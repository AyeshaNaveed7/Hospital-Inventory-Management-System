package application.inventoryManager;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import database.inventory.InventoryViewer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.mainInventory.InventoryItem;

public class InventoryTableView {

   private TableView<InventoryItem> tableView;
   private ObservableList<InventoryItem> itemList;
   private InventoryViewer inventoryViewer;
   private Connection connection;

   public InventoryTableView(Connection connection) {
       this.connection = connection;
       this.itemList = FXCollections.observableArrayList(); // Initialize the ObservableList
       this.inventoryViewer = new InventoryViewer(connection); // Create an instance of InventoryViewer
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

       // Add columns to the table view
       tableView.getColumns().addAll(
           itemIdColumn, nameColumn, availableQuantityColumn,
           limitQuantityColumn, typeColumn, supplierIdColumn,
           lastUpdatedColumn, costPerQuantityColumn
       );

       // Set the items from the observable list to the table view
       tableView.setItems(itemList);

       // Add styling
       tableView.getStyleClass().add("custom-table-view");
   }

   public TableView<InventoryItem> getTableView() {
       return tableView;
   }

   // Method to refresh the table view with data from the database
   public void refreshTableView() {
       itemList.clear(); // Clear the current items
       ResultSet resultSet = inventoryViewer.selectAllFromInventory(); // Call the method from InventoryViewer

       try {
           if (resultSet != null) {
               while (resultSet.next()) {
                  // Create InventoryItem objects and add them to the itemList
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
           // Handle SQL exceptions
           e.printStackTrace();
       } finally {
           // Close the ResultSet and any other resources if needed
           if (resultSet != null) {
               try {
                  resultSet.close();
               } catch (SQLException e) {
                  e.printStackTrace();
               }
           }
       }
   }
}
