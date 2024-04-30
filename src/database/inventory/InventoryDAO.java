package database.inventory;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDate;

import model.mainInventory.Equipment;
import model.mainInventory.InventoryItem;
import model.mainInventory.MedicalSupplies;

public class InventoryDAO {
    private Connection connection;

    public InventoryDAO(Connection connection) {
        this.connection = connection;
       
    }

    // Method to create tables if they do not exist
    private void createTablesIfNotExist() {
        try {
            if (!checkTableExists("InventoryItem")) {
                createInventoryItemTable();
            }
            if (!checkTableExists("MedicalSupplies")) {
                createMedicalSuppliesTable();
            }
            if (!checkTableExists("Equipment")) {
                createEquipmentTable();
            }
            if (!checkTableExists("MSRegulatory")) {
                createMSRegulatoryTable();
            }
            if (!checkTableExists("ERegulatory")) {
                createERegulatoryTable();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions as needed
        }
    }

    // Method to check if a table exists
    private boolean checkTableExists(String tableName) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String checkTableExistsSQL = "SELECT count(*) FROM user_tables WHERE table_name = '" + tableName + "'";
            ResultSet resultSet = statement.executeQuery(checkTableExistsSQL);
            resultSet.next();
            int tableCount = resultSet.getInt(1);
            return tableCount > 0;
        }
    }

    // Method to create the InventoryItem table
    private void createInventoryItemTable() throws SQLException {
        String query = "CREATE TABLE InventoryItem (" +
                "itemId INT PRIMARY KEY," +
                "name VARCHAR(255)," +
                "availableQuantity INT," +
                "limitQuantity INT," +
                "type VARCHAR(50)," +
                "supplierId INT," +
                "lastUpdated DATE," +
                "costPerQuantity INT," +
                "FOREIGN KEY (supplierId) REFERENCES Supplier(user_id))";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.execute();
        }
    }

    // Method to create the MedicalSupplies table
    private void createMedicalSuppliesTable() throws SQLException {
        String query = "CREATE TABLE MedicalSupplies (" +
                "itemId INT PRIMARY KEY," +
                "manufacturingDate DATE," +
                "expiryDate DATE," +
                "temperature INT," +
                "dosageForm VARCHAR(50)," +
                "FOREIGN KEY (itemId) REFERENCES InventoryItem(itemId))";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.execute();
        }
    }

    // Method to create the Equipment table
    private void createEquipmentTable() throws SQLException {
        String query = "CREATE TABLE Equipment (" +
                "itemId INT PRIMARY KEY," +
                "condition VARCHAR(255)," +
                "warrantyDate DATE," +
                "FOREIGN KEY (itemId) REFERENCES InventoryItem(itemId))";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.execute();
        }
    }

    // Method to create MSRegulatory table
    private void createMSRegulatoryTable() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String sql = "CREATE TABLE MSRegulatory (" +
                    "regulatoryId INT PRIMARY KEY, " +
                    "itemId INT, " +
                    "manufacturingDate DATE, " +
                    "expiryDate DATE, " +
                    "temperature INT, " +
                    "complianceStatus VARCHAR(20), " +
                    "name VARCHAR(255), " +
                    "FOREIGN KEY (itemId) REFERENCES MedicalSupplies(itemId)" +
                    ")";
            statement.executeUpdate(sql);
        }
    }

    // Method to create ERegulatory table
    private void createERegulatoryTable() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String sql = "CREATE TABLE ERegulatory (" +
                    "regulatoryId INT PRIMARY KEY, " +
                    "itemId INT, " +
                    "name VARCHAR(255), " +
                    "warrantyDate DATE, " +
                    "periodicMaintenanceDate DATE, " +
                    "complianceStatus VARCHAR(20), " +
                    "FOREIGN KEY (itemId) REFERENCES Equipment(itemId)" +
                    ")";
            statement.executeUpdate(sql);
        }
    }
    
    
 // Method to insert data into InventoryItem table
    public void insertInventoryItem(InventoryItem item) throws SQLException {
        // Stored procedure call for inserting into InventoryItem
        String storedProcedureCall = "{call InsertInventoryItem(?, ?, ?, ?, ?, ?, ?, ?)}";

        try (CallableStatement statement = connection.prepareCall(storedProcedureCall)) {
            statement.setInt(1, item.getItemId());
            statement.setString(2, item.getName());
            statement.setInt(3, item.getAvailableQuantity());
            statement.setInt(4, item.getLimitQuantity());
            statement.setString(5, item.getType());
            statement.setInt(6, item.getSupplierId());
            statement.setObject(7, item.getLastUpdated(), Types.DATE);
            statement.setInt(8, item.getCostPerQuantity());

            // Execute the stored procedure
            statement.executeUpdate();
        }
    }
    
 // Method to insert data into MSRegulatory table
    public void insertMSRegulatory(int itemId, LocalDate manufacturingDate, LocalDate expiryDate, int temperature) throws SQLException {
        // Stored procedure call for inserting into MSRegulatory
        String msRegulatoryStoredProcedure = "{call InsertMSRegulatory(?, ?, ?, ?)}";

        try (CallableStatement msRegulatoryStatement = connection.prepareCall(msRegulatoryStoredProcedure)) {
            msRegulatoryStatement.setInt(1, itemId);
            msRegulatoryStatement.setObject(2, manufacturingDate, Types.DATE);
            msRegulatoryStatement.setObject(3, expiryDate, Types.DATE);
            msRegulatoryStatement.setInt(4, temperature);

            // Execute the stored procedure for MSRegulatory
            msRegulatoryStatement.executeUpdate();
        }
    }

    // Method to insert data into MedicalSupplies table (we need to call only this method in GUI as it will automatically insert data into regulatory table of medical supplies)
    public void insertMedicalSupplies(MedicalSupplies medicalSupplies) throws SQLException {
        // Stored procedure call for inserting into MedicalSupplies
        String medicalSuppliesStoredProcedure = "{call InsertMedicalSupplies(?, ?, ?, ?, ?)}";

        try (CallableStatement medicalSuppliesStatement = connection.prepareCall(medicalSuppliesStoredProcedure)) {
            medicalSuppliesStatement.setInt(1, medicalSupplies.getItemId());
            medicalSuppliesStatement.setObject(2, medicalSupplies.getManufacturingDate(), Types.DATE);
            medicalSuppliesStatement.setObject(3, medicalSupplies.getExpiryDate(), Types.DATE);
            medicalSuppliesStatement.setInt(4, medicalSupplies.getTemperature());
            medicalSuppliesStatement.setString(5, medicalSupplies.getDosageForm());

            // Execute the stored procedure for MedicalSupplies
            medicalSuppliesStatement.executeUpdate();

            // Call the method to insert data into MSRegulatory
            insertMSRegulatory(
                medicalSupplies.getItemId(),
                medicalSupplies.getManufacturingDate(),
                medicalSupplies.getExpiryDate(),
                medicalSupplies.getTemperature()
            );
        }
    }
    
 // Method to insert data into Equipment table
    public void insertEquipment(Equipment equipment) throws SQLException {
        // Stored procedure call for inserting into Equipment
        String equipmentStoredProcedure = "{call InsertEquipment(?, ?, ?)}";

        try (CallableStatement equipmentStatement = connection.prepareCall(equipmentStoredProcedure)) {
            equipmentStatement.setInt(1, equipment.getItemId());
            equipmentStatement.setString(2, equipment.getCondition());
            equipmentStatement.setObject(3, equipment.getWarrantyDate(), Types.DATE);

            // Execute the stored procedure for Equipment
            equipmentStatement.executeUpdate();
        }

        // Insert data into ERegulatory table using the same data
        insertERegulatory(equipment.getItemId(), equipment.getWarrantyDate());
    }


    // Method to insert data into ERegulatory table
    public void insertERegulatory(int itemId, LocalDate warrantyDate) throws SQLException {
        // Stored procedure call for inserting into ERegulatory
        String eRegulatoryStoredProcedure = "{call InsertERegulatory(?, ?)}";

        try (CallableStatement eRegulatoryStatement = connection.prepareCall(eRegulatoryStoredProcedure)) {
            eRegulatoryStatement.setInt(1, itemId);
            eRegulatoryStatement.setObject(2, warrantyDate, Types.DATE);

            // Execute the stored procedure for ERegulatory
            eRegulatoryStatement.executeUpdate();
        }
    }
    
}
