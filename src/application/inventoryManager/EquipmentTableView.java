package application.inventoryManager;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.mainInventory.Equipment;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import database.inventory.InventoryViewer;

public class EquipmentTableView {

   private TableView<Equipment> tableView;
   private ObservableList<Equipment> equipmentList;
   private InventoryViewer inventoryViewer;

   public EquipmentTableView(Connection connection) {
       this.inventoryViewer = new InventoryViewer(connection);
       this.equipmentList = FXCollections.observableArrayList(); // Initialize the ObservableList
       initializeTableView();
   }

   private void initializeTableView() {
       tableView = new TableView<>();

       TableColumn<Equipment, Integer> itemIdColumn = new TableColumn<>("Item ID");
       itemIdColumn.setCellValueFactory(new PropertyValueFactory<>("itemId"));

       TableColumn<Equipment, String> nameColumn = new TableColumn<>("Name");
       nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

       // Add columns for Equipment specific properties
       TableColumn<Equipment, String> conditionColumn = new TableColumn<>("Condition");
       conditionColumn.setCellValueFactory(new PropertyValueFactory<>("condition"));

       TableColumn<Equipment, LocalDate> warrantyDateColumn = new TableColumn<>("Warranty Date");
       warrantyDateColumn.setCellValueFactory(new PropertyValueFactory<>("warrantyDate"));

       // Add columns for InventoryItem properties
       TableColumn<Equipment, Integer> availableQuantityColumn = new TableColumn<>("Available Quantity");
       availableQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("availableQuantity"));

       TableColumn<Equipment, Integer> limitQuantityColumn = new TableColumn<>("Limit Quantity");
       limitQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("limitQuantity"));

       TableColumn<Equipment, String> typeColumn = new TableColumn<>("Type");
       typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

       TableColumn<Equipment, Integer> supplierIdColumn = new TableColumn<>("Supplier ID");
       supplierIdColumn.setCellValueFactory(new PropertyValueFactory<>("supplierId"));

       TableColumn<Equipment, LocalDate> lastUpdatedColumn = new TableColumn<>("Last Updated");
       lastUpdatedColumn.setCellValueFactory(new PropertyValueFactory<>("lastUpdated"));

       TableColumn<Equipment, Integer> costPerQuantityColumn = new TableColumn<>("Cost per Quantity");
       costPerQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("costPerQuantity"));

       // Add columns to the table view
       tableView.getColumns().addAll(
           itemIdColumn, nameColumn, conditionColumn,
           warrantyDateColumn, availableQuantityColumn, limitQuantityColumn,
           typeColumn, supplierIdColumn, lastUpdatedColumn, costPerQuantityColumn
       );

       // Set the items from the observable list to the table view
       tableView.setItems(equipmentList);

       // Add styling
       tableView.getStyleClass().add("custom-table-view");
   }

   public TableView<Equipment> getTableView() {
       return tableView;
   }

   // Method to refresh the table view with equipment data
   public void refreshEquipmentTableView() {
       equipmentList.clear(); // Clear the current items
       ResultSet resultSet = inventoryViewer.selectEquipmentWithInventory();

       if (resultSet != null) {
           try {
               while (resultSet.next()) {
                  // Create Equipment objects and add them to the equipmentList
                  Equipment equipment = new Equipment(
                          resultSet.getInt("itemId"),
                          resultSet.getString("name"),
                          resultSet.getInt("availableQuantity"),
                          resultSet.getInt("limitQuantity"),
                          resultSet.getString("type"),
                          resultSet.getInt("supplierId"),
                          resultSet.getDate("lastUpdated").toLocalDate(),
                          resultSet.getInt("costPerQuantity"),
                          resultSet.getString("condition"),
                          resultSet.getDate("warrantyDate").toLocalDate()
                  );
                  equipmentList.add(equipment);
               }
           } catch (SQLException e) {
               e.printStackTrace();
           }
       }
   }
}
