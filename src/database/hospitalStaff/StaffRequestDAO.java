package database.hospitalStaff;



import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.hospitalStaff.StaffRequest;

public class StaffRequestDAO {
    private Connection connection;

    public StaffRequestDAO(Connection connection) {
        this.connection = connection;
        // Check and create the table if it doesn't exist
       
    }

    // Method to create the Staff Request table if it doesn't exist
    private void createStaffRequestTableIfNotExists() {
        try {
            if (!checkTableExists("StaffRequest")) {
                createStaffRequestTable();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions as needed
        }
    }

 // Method to create the Staff Request table with constraints
    private void createStaffRequestTable() throws SQLException {
        String query = "CREATE TABLE StaffRequest (" +
                "requestId INT PRIMARY KEY," +
                "inventoryItemId INT REFERENCES InventoryItem(ItemId)," +
                "deptId INT REFERENCES Department(Dept_id)," +
                "requestedQuantity INT CHECK (requestedQuantity >= 0)," +
                "requestDate DATE DEFAULT CURRENT_TIMESTAMP," +
                "status VARCHAR(20) CHECK (status IN ('Pending', 'Approved', 'Rejected')))";

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

    // Insert data into the Staff Request table
    public void insertStaffRequest(StaffRequest staffRequest) throws SQLException {
        String query = "INSERT INTO StaffRequest (requestId, inventoryItemId, deptId, requestedQuantity, status) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, staffRequest.getRequestId());
            statement.setInt(2, staffRequest.getInventoryItemId());
            statement.setInt(3, staffRequest.getDeptId());
            statement.setInt(4, staffRequest.getRequestedQuantity());
            statement.setString(5, staffRequest.getStatus());
            statement.executeUpdate();
        }
    }

 // Method to insert data into StaffRequest table using the stored procedure
    public void insertStaffRequestWithProcedure(int inventoryid, int deptID, int requestquantity, String status) throws SQLException {
        String procedureCall = "{call InsertStaffRequest(?, ?, ?, ?)}";

        try (CallableStatement callableStatement = connection.prepareCall(procedureCall)) {
            callableStatement.setInt(1, inventoryid);
            callableStatement.setInt(2, deptID);
            callableStatement.setInt(3, requestquantity);
            callableStatement.setString(4, status);

            // Execute the stored procedure to insert the staff request
            callableStatement.executeUpdate();
        }
    }
    // Method to select all staff requests from the StaffRequest table
    public ResultSet selectStaffRequests() throws SQLException {
        String query = "SELECT * FROM StaffRequest";
        PreparedStatement statement = connection.prepareStatement(query);
        return statement.executeQuery();
    }
    
    public void updateStaffRequestStatus(StaffRequest staffRequest) throws SQLException {
        String query = "UPDATE StaffRequest SET status = ? WHERE requestId = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, staffRequest.getStatus());
            statement.setInt(2, staffRequest.getRequestId());
            statement.executeUpdate();
        }
    }

}

