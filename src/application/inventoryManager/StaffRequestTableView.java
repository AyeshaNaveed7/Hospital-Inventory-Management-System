package application.inventoryManager;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.hospitalStaff.StaffRequest;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

import database.hospitalStaff.StaffRequestDAO;

public class StaffRequestTableView {

    private TableView<StaffRequest> tableView;
    private ObservableList<StaffRequest> staffRequestList;
    private StaffRequestDAO staffRequestDAO;

    public StaffRequestTableView(Connection connection) {
        this.staffRequestDAO = new StaffRequestDAO(connection);
        this.staffRequestList = FXCollections.observableArrayList(); // Initialize the ObservableList
        initializeTableView();
    }

    private void initializeTableView() {
        tableView = new TableView<>();

        TableColumn<StaffRequest, Integer> requestIdColumn = new TableColumn<>("Request ID");
        requestIdColumn.setCellValueFactory(new PropertyValueFactory<>("requestId"));

        // Add columns for StaffRequest specific properties
        TableColumn<StaffRequest, Integer> inventoryItemIdColumn = new TableColumn<>("Inventory Item ID");
        inventoryItemIdColumn.setCellValueFactory(new PropertyValueFactory<>("inventoryItemId"));

        TableColumn<StaffRequest, Integer> deptIdColumn = new TableColumn<>("Department ID");
        deptIdColumn.setCellValueFactory(new PropertyValueFactory<>("deptId"));

        TableColumn<StaffRequest, Integer> requestedQuantityColumn = new TableColumn<>("Requested Quantity");
        requestedQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("requestedQuantity"));

        TableColumn<StaffRequest, LocalDate> requestDateColumn = new TableColumn<>("Request Date");
        requestDateColumn.setCellValueFactory(new PropertyValueFactory<>("requestDate"));

        TableColumn<StaffRequest, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Add columns to the table view
        tableView.getColumns().addAll(
            requestIdColumn, inventoryItemIdColumn, deptIdColumn,
            requestedQuantityColumn, requestDateColumn, statusColumn
        );

        // Set the items from the observable list to the table view
        tableView.setItems(staffRequestList);

        // Add a row selection listener to open the status change dialog
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                showStatusChangeDialog(newSelection);
            }
        });

        // Add styling
        tableView.getStyleClass().add("custom-table-view");
    }

    public TableView<StaffRequest> getTableView() {
        return tableView;
    }

    // Method to refresh the table view with staff request data
    public void refreshStaffRequestTableView() throws SQLException {
        staffRequestList.clear(); // Clear the current items
        ResultSet resultSet = staffRequestDAO.selectStaffRequests();

        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                    // Create StaffRequest objects and add them to the staffRequestList
                    StaffRequest staffRequest = new StaffRequest(
                            resultSet.getInt("requestId"),
                            resultSet.getInt("inventoryItemId"),
                            resultSet.getInt("deptId"),
                            resultSet.getInt("requestedQuantity"),
                            resultSet.getDate("requestDate").toLocalDate(),
                            resultSet.getString("status")
                    );
                    staffRequestList.add(staffRequest);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void showStatusChangeDialog(StaffRequest staffRequest) {
    	   // Create a list of choices for the status (Accepted or Rejected)
    	   ObservableList<String> choices = FXCollections.observableArrayList("Approved", "Rejected");

    	   // Create a ChoiceDialog with the list of choices
    	   ChoiceDialog<String> dialog = new ChoiceDialog<>(staffRequest.getStatus(), choices);
    	   dialog.setTitle("Change Status");
    	   dialog.setHeaderText("Change the status of the job request:");
    	   dialog.setContentText("Select the new status:");

    	   // Show the dialog and wait for the user's choice
    	   Optional<String> result = dialog.showAndWait();

    	   // Check if the user made a choice
    	   result.ifPresent(newStatus -> {
    	       // Validate the new status
    	       if (!choices.contains(newStatus)) {
    	           System.out.println("Invalid status. Please enter 'Accepted' or 'Rejected'.");
    	           return;
    	       }

    	       // Update the status of the selected staffRequest in the database using the DAO
    	       try {
    	           staffRequest.setStatus(newStatus);
    	           staffRequestDAO.updateStaffRequestStatus(staffRequest); // Implement this method in your DAO
    	           refreshStaffRequestTableView(); // Refresh the table view
    	       } catch (SQLException e) {
    	           e.printStackTrace();
    	           // Handle the exception as needed
    	       }
    	   });
    	}

    
}
