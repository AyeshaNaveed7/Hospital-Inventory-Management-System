package database.regulatory;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class RegulatoryViewer {

    private Connection connection;

    public RegulatoryViewer(Connection connection) {
        this.connection = connection;
    }

    // Method to select all records from MSRegulatory
    public ResultSet selectAllFromMSRegulatory() {
        String query = "SELECT * FROM MSRegulatory";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            return statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions as needed
            return null;
        }
    }

    // Method to select all records from ERegulatory
    public ResultSet selectAllFromERegulatory() {
        String query = "SELECT * FROM ERegulatory";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            return statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions as needed
            return null;
        }
    }
    //method to insert periodic maintenance date and compliance status
    public static void insertPeriodicDateAndCompliance(Connection connection, int regulatoryId, LocalDate periodicMaintenanceDate, String complianceStatus) throws SQLException {
        String sql = "INSERT INTO ERegulatory (regulatoryId, periodicMaintenanceDate, complianceStatus) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, regulatoryId);
            pstmt.setDate(2, java.sql.Date.valueOf(periodicMaintenanceDate));
            pstmt.setString(3, complianceStatus);

            pstmt.executeUpdate();
        }
    }
}
