package database.user;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class DepartmentDAO {
    private Connection connection;

    public DepartmentDAO(Connection connection) {
        this.connection = connection;
    }

    public void createDepartmentTable() {
        try (Statement statement = connection.createStatement()) {
            // SQL statement to create the Department table
            String sql = "CREATE TABLE Department ("
                    + "dept_id INT PRIMARY KEY,"
                    + "name VARCHAR(255) NOT NULL,"
                    + "contact_info INT NOT NULL"
                    + ")";

            // Execute the SQL statement
            statement.executeUpdate(sql);

            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Additional methods for DepartmentDAO if needed
}
