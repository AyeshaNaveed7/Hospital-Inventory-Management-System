package application.hospitalStaff;



import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.hospitalStaff.DeptInventory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import database.hospitalStaff.DeptInventoryDAO;

public class AvailableMSTableView{

    private TableView<DeptInventory> tableView;
    private ObservableList<DeptInventory> deptInventoryList;
    private DeptInventoryDAO deptInventoryDAO;

    public AvailableMSTableView(Connection connection) {
        this.deptInventoryDAO = new DeptInventoryDAO(connection);
        this.deptInventoryList = FXCollections.observableArrayList(); // Initialize the ObservableList
        initializeTableView();
    }

    private void initializeTableView() {
        tableView = new TableView<>();

        TableColumn<DeptInventory, Integer> depItemIdColumn = new TableColumn<>("DepItem ID");
        depItemIdColumn.setCellValueFactory(new PropertyValueFactory<>("depItemId"));

        TableColumn<DeptInventory, Integer> inventoryItemIdColumn = new TableColumn<>("Inventory Item ID");
        inventoryItemIdColumn.setCellValueFactory(new PropertyValueFactory<>("inventoryItemId"));

        TableColumn<DeptInventory, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<DeptInventory, Integer> availableQuantityColumn = new TableColumn<>("Available Quantity");
        availableQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("availableQuantity"));

        TableColumn<DeptInventory, Integer> limitQuantityColumn = new TableColumn<>("Limit Quantity");
        limitQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("limitQuantity"));

        TableColumn<DeptInventory, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<DeptInventory, LocalDate> lastUpdatedColumn = new TableColumn<>("Last Updated");
        lastUpdatedColumn.setCellValueFactory(new PropertyValueFactory<>("lastUpdated"));

        // Add columns to the table view
        tableView.getColumns().addAll(
                depItemIdColumn, inventoryItemIdColumn, nameColumn,
                availableQuantityColumn, limitQuantityColumn, typeColumn,
                lastUpdatedColumn
        );

        // Set the items from the observable list to the table view
        tableView.setItems(deptInventoryList);

        // Add styling
        tableView.getStyleClass().add("custom-table-view");
    }

    public TableView<DeptInventory> getTableView() {
        return tableView;
    }

    // Method to refresh the table view with department inventory data
    public void refreshDeptInventoryTableView() throws SQLException {
        deptInventoryList.clear(); // Clear the current items
        ResultSet resultSet = deptInventoryDAO.selectDeptInventory();

        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                    // Create DeptInventory objects and add them to the deptInventoryList
                    DeptInventory deptInventory = new DeptInventory(
                            resultSet.getInt("depItemId"),
                            resultSet.getInt("inventoryItemId"),
                            resultSet.getString("name"),
                            resultSet.getInt("availableQuantity"),
                            resultSet.getInt("limitQuantity"),
                            resultSet.getString("type"),
                            resultSet.getDate("lastUpdated").toLocalDate()
                    );
                    deptInventoryList.add(deptInventory);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

