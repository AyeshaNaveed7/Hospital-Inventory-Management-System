package application.regulatoryManager;



import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.regulatory.ERegulatory; // Assuming this is your model class

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import database.regulatory.RegulatoryViewer;

public class REquipmentsTableView {

	 private TableView<ERegulatory> tableView;
	    private ObservableList<ERegulatory> equipmentRegulatoryList;
	    private RegulatoryViewer regulatoryViewer;
	    private Connection connection; // Added to store the connection
private int userid;
	    public REquipmentsTableView(Connection connection) {
	        this.connection = connection; // Store the connection
	        this.regulatoryViewer = new RegulatoryViewer(connection);
	        this.equipmentRegulatoryList = FXCollections.observableArrayList();
	        initializeTableView();
	        setupRowClickListener(); // Setup the click listener
	    }

    private void initializeTableView() {
        tableView = new TableView<>();

        TableColumn<ERegulatory, Integer> eRegulatoryIdColumn = new TableColumn<>("E Regulatory ID");
        eRegulatoryIdColumn.setCellValueFactory(new PropertyValueFactory<>("eRegulatoryId"));

        TableColumn<ERegulatory, Integer> itemIdColumn = new TableColumn<>("Item ID");
        itemIdColumn.setCellValueFactory(new PropertyValueFactory<>("itemId"));

        TableColumn<ERegulatory, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<ERegulatory, LocalDate> warrantyDateColumn = new TableColumn<>("Warranty Date");
        warrantyDateColumn.setCellValueFactory(new PropertyValueFactory<>("warrantyDate"));

        TableColumn<ERegulatory, LocalDate> periodicMaintenanceDateColumn = new TableColumn<>("Periodic Maintenance Date");
        periodicMaintenanceDateColumn.setCellValueFactory(new PropertyValueFactory<>("datePeriodicMaintenance"));

        TableColumn<ERegulatory, String> complianceStatusColumn = new TableColumn<>("Compliance Status");
        complianceStatusColumn.setCellValueFactory(new PropertyValueFactory<>("complianceStatus"));

        tableView.getColumns().addAll(
                eRegulatoryIdColumn, itemIdColumn, nameColumn, warrantyDateColumn,
                periodicMaintenanceDateColumn, complianceStatusColumn
        );

        tableView.setItems(equipmentRegulatoryList);

        tableView.getStyleClass().add("equipment-regulatory-table-view");
    }
    private void setupRowClickListener() {
        tableView.setRowFactory(tv -> {
            TableRow<ERegulatory> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 1) {
                    ERegulatory clickedRow = row.getItem();
                    // Assuming you have a way to get userId from clickedRow
                    int userId = getTheUserId(clickedRow); // Replace with actual logic to get userId

                    // Launch the EquipmentAdditionalDetailForm
                    EquipmentAdditionalDetailForm detailForm = new EquipmentAdditionalDetailForm(userId, connection);
                    try {
                        Stage stage = new Stage();
                        detailForm.start(stage); // Start the detail form
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });
    }
    public TableView<ERegulatory> getTableView() {
        return tableView;
    }

    public void refreshREquipmentsTableView() {
        equipmentRegulatoryList.clear();
        ResultSet resultSet = regulatoryViewer.selectAllFromERegulatory();

        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                    int regulatoryID = resultSet.getInt("RegulatoryID");
                    int itemID = resultSet.getInt("ItemID");
                    String name = resultSet.getString("Name");

                    LocalDate warrantyDate = null;
                    Date warrantyDateSql = resultSet.getDate("WarrantyDate");
                    if (warrantyDateSql != null) {
                        warrantyDate = warrantyDateSql.toLocalDate();
                    }

                    LocalDate periodicMaintenanceDate = null;
                    Date periodicMaintenanceDateSql = resultSet.getDate("PeriodicMaintenanceDate");
                    if (periodicMaintenanceDateSql != null) {
                        periodicMaintenanceDate = periodicMaintenanceDateSql.toLocalDate();
                    }

                    String complianceStatus = resultSet.getString("ComplianceStatus");

                    ERegulatory equipment = new ERegulatory(
                        regulatoryID, itemID, name, warrantyDate, periodicMaintenanceDate, complianceStatus
                    );
                    equipmentRegulatoryList.add(equipment);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    private int getTheUserId(ERegulatory regulatory) {
        return regulatory.getERegulatoryId();
    }

}
