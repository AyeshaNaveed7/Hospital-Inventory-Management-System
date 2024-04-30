package application.regulatoryManager;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.regulatory.MSRegulatory; // Updated model class

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import database.regulatory.RegulatoryViewer;

public class RMedicalSuppliesTableView {

   private TableView<MSRegulatory> tableView; // Updated to MSRegulatory
   private ObservableList<MSRegulatory> rMedicalSuppliesList; // Updated to MSRegulatory
   private RegulatoryViewer regulatoryViewer;

   public RMedicalSuppliesTableView(Connection connection) {
       this.regulatoryViewer = new RegulatoryViewer(connection);
       this.rMedicalSuppliesList = FXCollections.observableArrayList();
       initializeTableView();
   }

   private void initializeTableView() {
       tableView = new TableView<>();

       TableColumn<MSRegulatory, Integer> msRegulatoryIdColumn = new TableColumn<>("MS Regulatory ID");
       msRegulatoryIdColumn.setCellValueFactory(new PropertyValueFactory<>("msRegulatoryId"));

       TableColumn<MSRegulatory, Integer> itemIdColumn = new TableColumn<>("Item ID");
       itemIdColumn.setCellValueFactory(new PropertyValueFactory<>("itemId"));

       TableColumn<MSRegulatory, String> nameColumn = new TableColumn<>("Name");
       nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

       TableColumn<MSRegulatory, LocalDate> manufacturingDateColumn = new TableColumn<>("Manufacturing Date");
       manufacturingDateColumn.setCellValueFactory(new PropertyValueFactory<>("manufacturingDate"));

       TableColumn<MSRegulatory, LocalDate> expiryDateColumn = new TableColumn<>("Expiry Date");
       expiryDateColumn.setCellValueFactory(new PropertyValueFactory<>("expiryDate"));

       TableColumn<MSRegulatory, Integer> temperatureColumn = new TableColumn<>("Temperature");
       temperatureColumn.setCellValueFactory(new PropertyValueFactory<>("temperature"));

       TableColumn<MSRegulatory, String> complianceStatusColumn = new TableColumn<>("Compliance Status");
       complianceStatusColumn.setCellValueFactory(new PropertyValueFactory<>("complianceStatus"));

       tableView.getColumns().addAll(
           msRegulatoryIdColumn, itemIdColumn, nameColumn, manufacturingDateColumn,
           expiryDateColumn, temperatureColumn, complianceStatusColumn
       );

       tableView.setItems(rMedicalSuppliesList);

       tableView.getStyleClass().add("regulatory-table-view");
   }

   public TableView<MSRegulatory> getTableView() {
       return tableView;
   }

   public void refreshRMedicalSuppliesTableView() {
       rMedicalSuppliesList.clear();
       ResultSet resultSet = regulatoryViewer.selectAllFromMSRegulatory();

       if (resultSet != null) {
           try {
               while (resultSet.next()) {
                   MSRegulatory rMedicalSupply = new MSRegulatory(
                       resultSet.getInt("RegulatoryID"),
                       resultSet.getInt("ItemID"),
                       resultSet.getString("Name"),
                       resultSet.getDate("ManufacturingDate").toLocalDate(),
                       resultSet.getDate("ExpiryDate").toLocalDate(),
                       resultSet.getInt("Temperature"),
                       resultSet.getString("ComplianceStatus")
                   );
                   rMedicalSuppliesList.add(rMedicalSupply);
               }
           } catch (SQLException e) {
               e.printStackTrace();
           }
       }
   }
}
