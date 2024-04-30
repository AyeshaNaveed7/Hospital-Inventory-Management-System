package application.inventoryManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import database.inventory.InventoryDAO;
import model.mainInventory.InventoryItem;

public class InventoryItemForm extends Application {
    private Connection connection;

    public InventoryItemForm(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void start(Stage stage) {
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(20, 20, 20, 20));

        // Heading
        Label headingLabel = new Label("Add New Inventory Item");
        grid.add(headingLabel, 0, 0, 2, 1);

        // Item ID
        Label itemIdLabel = new Label("Item ID:");
        TextField itemIdField = new TextField();
        itemIdField.setPromptText("Item ID");
        grid.add(itemIdLabel, 0, 1);
        grid.add(itemIdField, 1, 1);

        // Name
        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField();
        nameField.setPromptText("Name");
        grid.add(nameLabel, 0, 2);
        grid.add(nameField, 1, 2);

        // Available Quantity
        Label availableQuantityLabel = new Label("Available Quantity:");
        TextField availableQuantityField = new TextField();
        availableQuantityField.setPromptText("Available Quantity");
        grid.add(availableQuantityLabel, 0, 3);
        grid.add(availableQuantityField, 1, 3);

        // Limit Quantity
        Label limitQuantityLabel = new Label("Limit Quantity:");
        TextField limitQuantityField = new TextField();
        limitQuantityField.setPromptText("Limit Quantity");
        grid.add(limitQuantityLabel, 0, 4);
        grid.add(limitQuantityField, 1, 4);

        // Type
        Label typeLabel = new Label("Type:");
        ComboBox<String> typeComboBox = new ComboBox<>();
        typeComboBox.getItems().addAll("Medical Supplies", "Equipment");
        grid.add(typeLabel, 0, 5);
        grid.add(typeComboBox, 1, 5);

        // Last Updated
        Label lastUpdatedLabel = new Label("Last Updated:");
        DatePicker lastUpdatedPicker = new DatePicker();
        grid.add(lastUpdatedLabel, 0, 6);
        grid.add(lastUpdatedPicker, 1, 6);

        // Cost Per Quantity
        Label costPerQuantityLabel = new Label("Cost Per Quantity:");
        TextField costPerQuantityField = new TextField();
        costPerQuantityField.setPromptText("Cost Per Quantity");
        grid.add(costPerQuantityLabel, 0, 7);
        grid.add(costPerQuantityField, 1, 7);

        // Submit Button
        Button submitButton = new Button("Submit");
        grid.add(submitButton, 1, 8);

        submitButton.setOnAction(event -> {
            try {
                InventoryDAO inventoryDAO = new InventoryDAO(connection);
                int itemId = Integer.parseInt(itemIdField.getText());
                String name = nameField.getText();
                int availableQuantity = Integer.parseInt(availableQuantityField.getText());
                int limitQuantity = Integer.parseInt(limitQuantityField.getText());
                String type = typeComboBox.getValue();
                int supplierId = "Medical Supplies".equals(type) ? 7 : 8;
                LocalDate lastUpdated = lastUpdatedPicker.getValue();
                int costPerQuantity = Integer.parseInt(costPerQuantityField.getText());

                InventoryItem newItem = new InventoryItem(itemId, name, availableQuantity, limitQuantity, type, supplierId, lastUpdated, costPerQuantity);
                inventoryDAO.insertInventoryItem(newItem);

                // Clear form fields after submission
                itemIdField.clear();
                nameField.clear();
                availableQuantityField.clear();
                limitQuantityField.clear();
                typeComboBox.getSelectionModel().clearSelection();
                lastUpdatedPicker.setValue(null);
                costPerQuantityField.clear();

                // Show confirmation
                new Alert(Alert.AlertType.INFORMATION, "Inventory Item Added Successfully", ButtonType.OK).showAndWait();

                // Open AdditionalDetailsForm
                AdditionalDetailsForm additionalDetailsForm = new AdditionalDetailsForm(connection, newItem);
                Stage additionalDetailsStage = new Stage();
                additionalDetailsForm.start(additionalDetailsStage);

                // Optionally, close the current form
                stage.close();

            } catch (NumberFormatException | SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage(), ButtonType.OK).showAndWait();
            }
        });


        // Scene and Stage setup
        Scene scene = new Scene(grid, 400, 550);
        stage.setTitle("Inventory Item Form");
        stage.setScene(scene);
        stage.show();
    }

    
}
