package application.inventoryManager;

import javafx.application.Application;
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

public class InventoryMMain extends Application {

    private BorderPane mainLayout;
    private Connection connection;
    private boolean isInventoryTableDisplayed = false; // Flag to track if Inventory Table is displayed

    public InventoryMMain(Connection connection) {
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

        Button inventoryButton = new Button("Inventory");
        inventoryButton.setOnAction(event -> {
            isInventoryTableDisplayed = true;
            InventoryTableView inventoryTableView = new InventoryTableView(connection);
            inventoryTableView.refreshTableView(); // Refresh table
            setRightTableView(inventoryTableView.getTableView());
        });

        VBox.setMargin(inventoryButton, new Insets(0, 0, 20, 0)); // Add extra bottom margin

        Button medicalSuppliesButton = new Button("Medical Supplies");
        medicalSuppliesButton.setOnAction(event -> {
            isInventoryTableDisplayed = false;
            MedicalSuppliesTableView medicalSuppliesTableView = new MedicalSuppliesTableView(connection);
            medicalSuppliesTableView.refreshMedicalSuppliesTableView(); // Refresh table
            setRightTableView(medicalSuppliesTableView.getTableView());
        });
Button newbutton=new Button("SEARCH");

        Button equipmentButton = new Button("Equipment");
        equipmentButton.setOnAction(event -> {
            isInventoryTableDisplayed = false;
            EquipmentTableView equipmentTableView = new EquipmentTableView(connection);
            equipmentTableView.refreshEquipmentTableView(); // Refresh table
            setRightTableView(equipmentTableView.getTableView());
        });

        Button requestedInventoryButton = new Button("Requested Inventory");
        requestedInventoryButton.setOnAction(event -> {
            isInventoryTableDisplayed = false;
            StaffRequestTableView staffRequestTableView = new StaffRequestTableView(connection);
            try {
                staffRequestTableView.refreshStaffRequestTableView(); // Refresh table
            } catch (SQLException e) {
                e.printStackTrace();
            }
            setRightTableView(staffRequestTableView.getTableView());
        });
        

        VBox.setMargin(medicalSuppliesButton, new Insets(0, 0, 10, 20)); // Add left indentation
        VBox.setMargin(equipmentButton, new Insets(0, 0, 10, 20)); // Add left indentation
        VBox.setMargin(requestedInventoryButton, new Insets(0, 0, 10, 20)); // Add left indentation
        VBox.setMargin(newbutton, new Insets(0, 0, 10, 20)); // Add left indentation

        leftSide.getChildren().addAll(inventoryButton, medicalSuppliesButton, equipmentButton, requestedInventoryButton, newbutton);
        return leftSide;
    }

    private HBox setupTopArea() {
        HBox topArea = new HBox(10);
        topArea.setAlignment(Pos.CENTER);
        Label titleLabel = new Label("Inventory Manager Screen");
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

        if (isInventoryTableDisplayed) {
            Button addNewItemButton = new Button("Add New Inventory Item");
            addNewItemButton.setOnAction(event -> openInventoryItemForm());
            rightLayout.getChildren().add(addNewItemButton);
        }

        mainLayout.setCenter(rightLayout);
    }

    private void openInventoryItemForm() {
        try {
            InventoryItemForm inventoryItemForm = new InventoryItemForm(connection);
            Stage stage = new Stage();
            inventoryItemForm.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

  
}
