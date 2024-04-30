package application.inventoryManager;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.mainInventory.MedicalSupplies;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import database.inventory.InventoryViewer;

public class MedicalSuppliesTableView {

   private TableView<MedicalSupplies> tableView;
   private ObservableList<MedicalSupplies> medicalSuppliesList;
   private InventoryViewer inventoryViewer;

   public MedicalSuppliesTableView(Connection connection) {
       this.inventoryViewer = new InventoryViewer(connection);
       this.medicalSuppliesList = FXCollections.observableArrayList(); // Initialize the ObservableList
       initializeTableView();
   }

   private void initializeTableView() {
       tableView = new TableView<>();

       TableColumn<MedicalSupplies, Integer> itemIdColumn = new TableColumn<>("Item ID");
       itemIdColumn.setCellValueFactory(new PropertyValueFactory<>("itemId"));

       TableColumn<MedicalSupplies, String> nameColumn = new TableColumn<>("Name");
       nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

       // Add columns for MedicalSupplies specific properties
       TableColumn<MedicalSupplies, LocalDate> manufacturingDateColumn = new TableColumn<>("Manufacturing Date");
       manufacturingDateColumn.setCellValueFactory(new PropertyValueFactory<>("manufacturingDate"));

       TableColumn<MedicalSupplies, LocalDate> expiryDateColumn = new TableColumn<>("Expiry Date");
       expiryDateColumn.setCellValueFactory(new PropertyValueFactory<>("expiryDate"));

       TableColumn<MedicalSupplies, Integer> temperatureColumn = new TableColumn<>("Temperature");
       temperatureColumn.setCellValueFactory(new PropertyValueFactory<>("temperature"));

       TableColumn<MedicalSupplies, String> dosageFormColumn = new TableColumn<>("Dosage Form");
       dosageFormColumn.setCellValueFactory(new PropertyValueFactory<>("dosageForm"));

       // Add columns for InventoryItem properties
       TableColumn<MedicalSupplies, Integer> availableQuantityColumn = new TableColumn<>("Available Quantity");
       availableQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("availableQuantity"));

       TableColumn<MedicalSupplies, Integer> limitQuantityColumn = new TableColumn<>("Limit Quantity");
       limitQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("limitQuantity"));

       TableColumn<MedicalSupplies, String> typeColumn = new TableColumn<>("Type");
       typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

       TableColumn<MedicalSupplies, Integer> supplierIdColumn = new TableColumn<>("Supplier ID");
       supplierIdColumn.setCellValueFactory(new PropertyValueFactory<>("supplierId"));

       TableColumn<MedicalSupplies, LocalDate> lastUpdatedColumn = new TableColumn<>("Last Updated");
       lastUpdatedColumn.setCellValueFactory(new PropertyValueFactory<>("lastUpdated"));

       TableColumn<MedicalSupplies, Integer> costPerQuantityColumn = new TableColumn<>("Cost per Quantity");
       costPerQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("costPerQuantity"));

       // Add columns to the table view
       tableView.getColumns().addAll(
           itemIdColumn, nameColumn, manufacturingDateColumn,
           expiryDateColumn, temperatureColumn, dosageFormColumn,
           availableQuantityColumn, limitQuantityColumn, typeColumn,
           supplierIdColumn, lastUpdatedColumn, costPerQuantityColumn
       );

       // Set the items from the observable list to the table view
       tableView.setItems(medicalSuppliesList);

       // Add styling
       tableView.getStyleClass().add("custom-table-view");
   }

   public TableView<MedicalSupplies> getTableView() {
       return tableView;
   }

   // Method to refresh the table view with medical supplies data
   public void refreshMedicalSuppliesTableView() {
       medicalSuppliesList.clear(); // Clear the current items
       ResultSet resultSet = inventoryViewer.selectMedicalSuppliesWithInventory();

       if (resultSet != null) {
           try {
               while (resultSet.next()) {
                  // Create MedicalSupplies objects and add them to the medicalSuppliesList
                  MedicalSupplies medicalSupplies = new MedicalSupplies(
                          resultSet.getInt("itemId"),
                          resultSet.getString("name"),
                          resultSet.getInt("availableQuantity"),
                          resultSet.getInt("limitQuantity"),
                          resultSet.getString("type"),
                          resultSet.getInt("supplierId"),
                          resultSet.getDate("lastUpdated").toLocalDate(),
                          resultSet.getInt("costPerQuantity"),
                          resultSet.getDate("manufacturingDate").toLocalDate(),
                          resultSet.getDate("expiryDate").toLocalDate(),
                          resultSet.getInt("temperature"),
                          resultSet.getString("dosageForm")
                  );
                  medicalSuppliesList.add(medicalSupplies);
               }
           } catch (SQLException e) {
               e.printStackTrace();
           }
       }
   }
}
