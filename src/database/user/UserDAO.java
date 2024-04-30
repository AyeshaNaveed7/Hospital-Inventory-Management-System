package database.user;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

public class UserDAO {

    private Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {
            String sql = "CREATE TABLE HUsers ("
                    + "user_id INT PRIMARY KEY,"
                    + "name VARCHAR(255) NOT NULL,"
                    + "password VARCHAR(255) NOT NULL,"
                    + "user_type VARCHAR(30) NOT NULL,"
                    + "department_id INT,"
                    + "contact_info INT NOT NULL,"
                    + "CONSTRAINT fk_dep FOREIGN KEY (department_id) REFERENCES Department(dept_id)"
                    + ")";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            // Log the exception or handle it appropriately
            e.printStackTrace();
        }
    }

    public void createSupplierTable() {
        try (Statement statement = connection.createStatement()) {
            String sql = "CREATE TABLE Supplier ("
                    + "user_id INT PRIMARY KEY,"
                    + "type VARCHAR(255) NOT NULL,"
                    + "CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES HUsers(user_id)"
                    + ")";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            // Log the exception or handle it appropriately
            e.printStackTrace();
        }
    }
    
    
    // Method to authenticate user using stored procedure
    public String authenticateUser(String username, String password) {
        String authenticationResult = "FAILURE";

        try (CallableStatement callableStatement = connection.prepareCall("{call AuthenticateUser(?, ?, ?)}")) {
            // Set input parameters
            callableStatement.setString(1, username);
            callableStatement.setString(2, password);

            // Register output parameter
            callableStatement.registerOutParameter(3, Types.VARCHAR);

            // Execute the stored procedure
            callableStatement.execute();

            // Retrieve the result
            authenticationResult = callableStatement.getString(3);

        } catch (SQLException e) {
            // Log the exception or handle it appropriately
            e.printStackTrace();
        }

        return authenticationResult;
    }
    

   
 // Method to change user password using stored procedure
    public boolean changePassword(int userId, String oldPassword, String newPassword) {
        boolean success = false;

        try {
            

            // Prepare and execute the stored procedure
            try (CallableStatement callableStatement = connection.prepareCall("{call CHANGE_PASSWORD(?, ?, ?)}")) {
                // Set input parameters
                callableStatement.setInt(1, userId);
                callableStatement.setString(2, oldPassword);
                callableStatement.setString(3, newPassword);

                // Execute the stored procedure
                success = callableStatement.executeUpdate() > 0;
            }
        } catch (NumberFormatException | SQLException e) {
            // Handle the exception appropriately (e.g., log or show an error message)
            e.printStackTrace();
        }

        return success;
    }
    //method to get user id by user name
    public int getUserIdByUsername(String username) {
        int userId = -1; // Default value if not found

        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT user_id FROM HUsers WHERE name = ?")) {
            preparedStatement.setString(1, username);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    userId = resultSet.getInt("user_id");
                }
            }

        } catch (SQLException e) {
            // Log the exception or handle it appropriately
            e.printStackTrace();
        }

        return userId;
    }
    //method to get user type by user id
    public String getUserTypeById(int userId) {
        String userType = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT user_type FROM HUsers WHERE user_id = ?")) {
            preparedStatement.setInt(1, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    userType = resultSet.getString("user_type");
                }
            }
        } catch (SQLException e) {
            // Log the exception or handle it appropriately
            e.printStackTrace();
        }

        return userType;
    }



}
