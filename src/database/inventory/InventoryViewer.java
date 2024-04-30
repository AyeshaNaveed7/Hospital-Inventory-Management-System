package database.inventory;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InventoryViewer {

    private Connection connection;

    public InventoryViewer(Connection connection) {
        this.connection = connection;
    }

    // Method to select all records from the InventoryItem table and return the result set
    public ResultSet selectAllFromInventory() {
        String query = "SELECT * FROM InventoryItem";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            return statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions as needed
            return null;
        }
    }
    
 // Method to select data from MedicalSupplies and InventoryItem using a join
    public ResultSet selectMedicalSuppliesWithInventory() {
        String query = "SELECT MS.*, II.* " +
                       "FROM MedicalSupplies MS " +
                       "JOIN InventoryItem II ON MS.itemId = II.itemId";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            return statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions as needed
            return null;
        }
    }
    
 // Method to select data from Equipment and InventoryItem using a join
    public ResultSet selectEquipmentWithInventory() {
        String query = "SELECT E.*, II.* " +
                       "FROM Equipment E " +
                       "JOIN InventoryItem II ON E.itemId = II.itemId";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            return statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions as needed
            return null;
        }
    }
}
