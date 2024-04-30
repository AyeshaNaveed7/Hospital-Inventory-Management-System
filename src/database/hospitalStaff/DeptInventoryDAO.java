package database.hospitalStaff;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.hospitalStaff.DeptInventory;

public class DeptInventoryDAO {
    private Connection connection;

    public DeptInventoryDAO(Connection connection) {
        this.connection = connection;
        // Check and create the table if it doesn't exist
       
    }

    // Method to create the Department Inventory table if it doesn't exist
    private void createDeptInventoryTableIfNotExists() {
        try {
            if (!checkTableExists("DepartmentInventory")) {
                createDeptInventoryTable();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions as needed
        }
    }

 // Method to create the Department Inventory table with constraints
    private void createDeptInventoryTable() throws SQLException {
        String query = "CREATE TABLE DepartmentInventory (" +
                "depItemId INT PRIMARY KEY," +
                "inventoryItemId INT," +
                "name VARCHAR(255) REFERENCES InventoryItem(Name)," +
                "availableQuantity INT," +
                "limitQuantity INT," +
                "type VARCHAR(50) REFERENCES InventoryItem(Type)," +
                "lastUpdated DATE DEFAULT CURRENT_TIMESTAMP)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.executeUpdate();
        }
    }


    // Method to check if a table exists
    private boolean checkTableExists(String tableName) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String checkTableExistsSQL = "SELECT count(*) FROM user_tables WHERE table_name = '" + tableName + "'";
            java.sql.ResultSet resultSet = statement.executeQuery(checkTableExistsSQL);
            resultSet.next();
            int tableCount = resultSet.getInt(1);
            return tableCount > 0;
        }
    }

    // Insert data into the Department Inventory table
    public void insertDeptInventory(DeptInventory deptInventory) throws SQLException {
        String query = "INSERT INTO DepartmentInventory (depItemId, inventoryItemId, name, availableQuantity, limitQuantity, type, lastUpdated) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, deptInventory.getDepItemId());
            statement.setInt(2, deptInventory.getInventoryItemId());
            statement.setString(3, deptInventory.getName());
            statement.setInt(4, deptInventory.getAvailableQuantity());
            statement.setInt(5, deptInventory.getLimitQuantity());
            statement.setString(6, deptInventory.getType());
            statement.setObject(7, deptInventory.getLastUpdated());
            statement.executeUpdate();
        }
    }
    
 // Method to select data from the Department Inventory table and return a ResultSet
    public ResultSet selectDeptInventory() throws SQLException {
        String query = "SELECT depItemId, inventoryItemId, name, availableQuantity, limitQuantity, type, lastUpdated FROM DepartmentInventory";
        PreparedStatement statement = connection.prepareStatement(query);
        return statement.executeQuery();
    }



}
