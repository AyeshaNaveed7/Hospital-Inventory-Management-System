package application;

import database.connection.DatabaseConnector;
import database.inventory.InventoryDAO;
import database.user.UserDAO;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.paint.Stop;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

import application.hospitalStaff.HospitalStaffMain;
import application.inventoryManager.InventoryMMain;
import application.regulatoryManager.RegulatoryMMain;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Get a database connection
            Connection connection = DatabaseConnector.getConnection();

            // Create instance of userDAO
            UserDAO userDAO = new UserDAO(connection);
            
         // Login Box setup
            VBox loginBox = new VBox(10);
            loginBox.setAlignment(Pos.CENTER);
            loginBox.setPadding(new Insets(15, 15, 15, 15));
            loginBox.setStyle("-fx-background-color: white; -fx-border-radius: 15; -fx-background-radius: 15;");
            // Labels and Fields
            Label loginLabel = new Label("LOGIN");
            loginLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: black;");
            Label instructionLabel = new Label("Please enter your details");
            instructionLabel.setStyle("-fx-text-fill: darkgrey;");
            TextField nameField = new TextField();
            nameField.setPromptText("Enter your name");
            nameField.setStyle("-fx-border-color: darkgrey;");
            PasswordField passwordField = new PasswordField();
            passwordField.setPromptText("Enter your password");
            passwordField.setStyle("-fx-border-color: darkgrey;");
            Button loginButton = new Button("Login");
            loginButton.setStyle("-fx-background-color: purple; -fx-text-fill: white;");

            // Adding components to the VBox
            loginBox.getChildren().addAll(loginLabel, instructionLabel, nameField, passwordField, loginButton);

            // Root Pane with Gradient Background
            BorderPane root = new BorderPane();
            root.setCenter(loginBox);
            LinearGradient gradient = new LinearGradient(
                0, 0, 1, 0, true, 
                CycleMethod.NO_CYCLE, 
                new Stop(0, Color.GRAY), 
                new Stop(1, Color.GREEN)
            );
            root.setBackground(new Background(new BackgroundFill(gradient, CornerRadii.EMPTY, Insets.EMPTY)));

            // Scene and Stage Setup
            Scene scene = new Scene(root, 400, 300);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Login Screen");
            primaryStage.setMaximized(false); // Maximizing instead of fullscreen

         // Event handler for the login button
            loginButton.setOnAction(event -> {
                String username = nameField.getText();
                String password = passwordField.getText();

                // Authenticate user
                String authenticationResult = userDAO.authenticateUser(username, password);

                // Show alert based on authentication result
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Authentication Result");

                if ("SUCCESS".equals(authenticationResult)) {
                    // Get the user ID using the provided username
                    int userId = userDAO.getUserIdByUsername(username);

                    // Get the user type using the obtained user ID
                    String userType = userDAO.getUserTypeById(userId);
                    

                    alert.setContentText("Login successful! User Type: " + userType);

                    // Create a final variable for connection
                    final Connection finalConnection = connection;

                    // Open respective screens based on user type
                    switch (userType) {
                        case "Hospital Staff":
                            openHospitalManagerScreen(finalConnection);
                        	primaryStage.hide();
                            break;
                        case "Inventory Manager":
                            openInventoryManagerScreen( finalConnection);
                            primaryStage.hide();
                            break;
                        case "Supplier":
                            // openSupplierScreen(userId);
                        	primaryStage.hide();
                            break;
                        case "Finance Manager":
                            // openFinanceManagerScreen(userId);
                        	primaryStage.hide();
                            break;
                        case "Hospital Manager":
                            // openHospitalManagerScreen(userId);
                        	primaryStage.hide();
                            break;
                        case "Regulatory Manager":
                             openRegulatoryManagerScreen(finalConnection);
                        	primaryStage.hide();
                            break;
                        default:
                            alert.setContentText("Unsupported user type. Please contact the administrator.");
                    }

                 // Create a label with the "Welcome" message
                    Label welcomeLabel = new Label("Welcome, " + username);
                    welcomeLabel.setStyle("-fx-font-weight: bold;");

                    // Set up the alert with the label and a "Change Password" button
                    alert.getDialogPane().setContent(welcomeLabel);
                    ButtonType changePasswordButton = new ButtonType("Change Password", ButtonBar.ButtonData.OK_DONE);
                    alert.getButtonTypes().add(changePasswordButton);

                    // Set up event handler for the "Change Password" button
                    Button changePassword = (Button) alert.getDialogPane().lookupButton(changePasswordButton);
                    changePassword.setOnAction(e -> {
                        // Hide the main login page
                        primaryStage.hide();

                        // Show the ChangePasswordScreen with the obtained user ID
                        ChangePasswordScreen changePasswordScreen = new ChangePasswordScreen(connection, userId);
                        changePasswordScreen.start(new Stage());
                    });

                    // Create a Timer to close the alert after 5 seconds (5000 milliseconds)
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            alert.close();
                        }
                    }, 5000);

                    // Show the alert
                    alert.showAndWait();
                } else {
                    alert.setContentText("Incorrect password. Please try again.");
                }

               
            });

            primaryStage.show();
            
         // Methods to open respective screens
//            private void openHospitalStaffScreen(int userId) {
//                HospitalStaffMain hospitalStaffMain = new HospitalStaffMain(connection, userId);
//                hospitalStaffMain.start(new Stage());
//            }

           

//            private void openSupplierScreen(int userId) {
//                SupplierMain supplierMain = new SupplierMain(connection, userId);
//                supplierMain.start(new Stage());
//            }
//
//            private void openFinanceManagerScreen(int userId) {
//                FinanceManagerMain financeManagerMain = new FinanceManagerMain(connection, userId);
//                financeManagerMain.start(new Stage());
//            }
//
           
//
          

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void openInventoryManagerScreen( Connection connection) {
        InventoryMMain inventoryManagerMain = new InventoryMMain(connection);
        inventoryManagerMain.start(new Stage());
    }
    private void openRegulatoryManagerScreen(Connection connection) {
        RegulatoryMMain regulatoryManagerMain = new RegulatoryMMain(connection);
        regulatoryManagerMain.start(new Stage());
    }
    private void openHospitalManagerScreen(Connection connection) {
        HospitalStaffMain hospitalManagerMain = new HospitalStaffMain(connection);
        hospitalManagerMain.start(new Stage());
    }

	public static void main(String[] args) {
        launch(args);
    }
}
