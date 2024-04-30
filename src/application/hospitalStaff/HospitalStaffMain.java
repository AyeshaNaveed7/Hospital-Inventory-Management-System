package application.hospitalStaff;

import javafx.application.Application;
import application.hospitalStaff.StaffRequestInventoryTableView;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;

public class HospitalStaffMain extends Application {

    private BorderPane mainLayout;
    private Connection connection;
    private boolean isInventoryTableDisplayed = false; // Flag to track if Inventory Table is displayed

    public HospitalStaffMain(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void start(Stage primaryStage) {
        mainLayout = new BorderPane();

        HBox topArea = setupTopArea();
        VBox leftSide = setupLeftSide();

        mainLayout.setTop(topArea);
        mainLayout.setLeft(leftSide);

        Scene scene = new Scene(mainLayout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true); // Maximizing instead of fullscreen

        primaryStage.show();
    }

    private VBox setupLeftSide() {
        VBox leftSide = new VBox(10);
        leftSide.setPadding(new Insets(20, 10, 10, 10)); // Add extra top padding

        Button availableInventoryButton = new Button("Available Inventory");
        availableInventoryButton.setOnAction(event -> {
            // Display the available inventory table
            isInventoryTableDisplayed = true;
            AvailableMSTableView availableInventoryTableView = new AvailableMSTableView(connection);
            try {
				availableInventoryTableView.refreshDeptInventoryTableView();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // Refresh table
            setRightTableView(availableInventoryTableView.getTableView());
        });

        VBox.setMargin(availableInventoryButton, new Insets(0, 0, 20, 0)); // Add extra bottom margin

        Button requestInventoryButton = new Button("Request Inventory");
        requestInventoryButton.setOnAction(event -> {
            // Create an instance of StaffRequestInventoryTableView
            StaffRequestInventoryTableView staffRequestTableView = new StaffRequestInventoryTableView(connection);

            // Set it as the center content of the mainLayout
            setRightTableView(staffRequestTableView.getTableView());

            // Refresh the table view if needed (you can add a refresh method in your StaffRequestInventoryTableView class)
            staffRequestTableView.refreshTableView();
        });


        VBox.setMargin(requestInventoryButton, new Insets(0, 0, 20, 0)); // Add extra bottom margin

        leftSide.getChildren().addAll(availableInventoryButton, requestInventoryButton);
        return leftSide;
    }

    private HBox setupTopArea() {
        HBox topArea = new HBox(10);
        topArea.setAlignment(Pos.CENTER);
        Label titleLabel = new Label("Hospital Staff Dashboard");
        ImageView userIcon = loadUserIcon();
        Label usernameLabel = new Label("Username");
        topArea.getChildren().addAll(titleLabel, userIcon, usernameLabel);
        return topArea;
    }

    private ImageView loadUserIcon() {
        InputStream imageStream = getClass().getResourceAsStream("/profile.png");
        if (imageStream != null) {
            return new ImageView(new Image(imageStream));
        } else {
            System.err.println("Image not found: /profile.png");
            return new ImageView(); // Placeholder if the image is not found
        }
    }

    private void setRightTableView(TableView<?> newTableView) {
        VBox rightLayout = new VBox(10);
        rightLayout.setAlignment(Pos.CENTER);
        rightLayout.getChildren().add(newTableView);

        mainLayout.setCenter(rightLayout);
    }

   
}
