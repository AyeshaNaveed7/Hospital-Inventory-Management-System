package application.hospitalStaff;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.hospitalStaff.StaffRequest;
import model.mainInventory.InventoryItem;
import database.hospitalStaff.StaffRequestDAO;
import database.inventory.InventoryViewer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class StaffRequestInventoryTableView {
    private TableView<InventoryItem> tableView;
    private ObservableList<InventoryItem> itemList;
    private InventoryViewer inventoryViewer;
    private Connection connection;
    private StaffRequestDAO staffRequestDAO;
    private int userId = 3; // Assuming you have a userId for the logged-in user

    public StaffRequestInventoryTableView(Connection connection) {
        this.connection = connection;
        
        this.itemList = FXCollections.observableArrayList();
        this.inventoryViewer = new InventoryViewer(connection);
        this.staffRequestDAO = new StaffRequestDAO(connection);
        initializeTableView();
    }

    private void initializeTableView() {
        tableView = new TableView<>();

        TableColumn<InventoryItem, Integer> itemIdColumn = new TableColumn<>("Item ID");
        itemIdColumn.setCellValueFactory(new PropertyValueFactory<>("itemId"));

        TableColumn<InventoryItem, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<InventoryItem, Integer> availableQuantityColumn = new TableColumn<>("Available Quantity");
        availableQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("availableQuantity"));

        TableColumn<InventoryItem, Integer> limitQuantityColumn = new TableColumn<>("Limit Quantity");
        limitQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("limitQuantity"));

        TableColumn<InventoryItem, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<InventoryItem, Integer> supplierIdColumn = new TableColumn<>("Supplier ID");
        supplierIdColumn.setCellValueFactory(new PropertyValueFactory<>("supplierId"));

        TableColumn<InventoryItem, LocalDate> lastUpdatedColumn = new TableColumn<>("Last Updated");
        lastUpdatedColumn.setCellValueFactory(new PropertyValueFactory<>("lastUpdated"));

        TableColumn<InventoryItem, Integer> costPerQuantityColumn = new TableColumn<>("Cost per Quantity");
        costPerQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("costPerQuantity"));

        tableView.getColumns().addAll(
                itemIdColumn, nameColumn, availableQuantityColumn,
                limitQuantityColumn, typeColumn, supplierIdColumn,
                lastUpdatedColumn, costPerQuantityColumn
        );

        tableView.setItems(itemList);
        tableView.getStyleClass().add("custom-table-view");
        setupRowClickListener(); // Setup row click listener
        refreshTableView();
    }

    public TableView<InventoryItem> getTableView() {
        return tableView;
    }

    public void refreshTableView() {
        itemList.clear();
        ResultSet resultSet = inventoryViewer.selectAllFromInventory();

        try {
            if (resultSet != null) {
                while (resultSet.next()) {
                    InventoryItem item = new InventoryItem(
                            resultSet.getInt("itemId"),
                            resultSet.getString("name"),
                            resultSet.getInt("availableQuantity"),
                            resultSet.getInt("limitQuantity"),
                            resultSet.getString("type"),
                            resultSet.getInt("supplierId"),
                            resultSet.getDate("lastUpdated").toLocalDate(),
                            resultSet.getInt("costPerQuantity")
                    );
                    itemList.add(item);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void setupRowClickListener() {
        tableView.setRowFactory(tv -> {
            TableRow<InventoryItem> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    InventoryItem selectedItem = row.getItem();
                    promptForRequestedQuantity(selectedItem);
                }
            });
            return row;
        });
    }

    private void promptForRequestedQuantity(InventoryItem selectedItem) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Request Inventory");
        dialog.setHeaderText("Requesting Item: " + selectedItem.getName());
        dialog.setContentText("Please enter the requested quantity:");

        // Convert the result to an int
        dialog.setResultConverter(result -> {
        	String userInput = dialog.getEditor().getText();
        	try {
        	    int requestedQuantity = Integer.parseInt(userInput);
        	    if (requestedQuantity > 0) {
        	        insertStaffRequest(selectedItem.getItemId(), requestedQuantity);
        	    } else {
        	        showAlert("Invalid Quantity", "Please enter a valid positive quantity.");
        	    }
        	} catch (NumberFormatException e) {
        	    showAlert("Invalid Input", "Please enter a valid number for the quantity.");
        	}

            return null;
        });

        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new javafx.scene.image.Image(getClass().getResourceAsStream("/profile.png")));

        dialog.showAndWait();
    }

    private void insertStaffRequest(int inventoryItemId, int requestedQuantity) {
      
        try {
            staffRequestDAO.insertStaffRequestWithProcedure(inventoryItemId, userId, requestedQuantity, "Pending");
            showAlert("Request Submitted", "Your request has been submitted successfully.");
        } catch (SQLException e) {
            showAlert("Request Failed", "Failed to submit your request. Please try again.");
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String content) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(content);
            alert.showAndWait();
        });
    }
}
