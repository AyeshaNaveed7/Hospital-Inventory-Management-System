package application.inventoryManager;



import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

import database.inventory.InventoryDAO;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.mainInventory.Equipment;
import model.mainInventory.InventoryItem;
import model.mainInventory.MedicalSupplies;

public class AdditionalDetailsForm extends Application {
    private Connection connection;
    private InventoryItem inventoryItem;
    private Stage stage;

    public AdditionalDetailsForm(Connection connection, InventoryItem inventoryItem) {
        this.connection = connection;
        this.inventoryItem = inventoryItem;
    }


    @Override
    public void start(Stage stage) {
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(20, 20, 20, 20));
        String itemType = inventoryItem.getType();

        // Check the type and display appropriate fields
        if ("Medical Supplies".equals(itemType)) {
            createMedicalSuppliesForm(grid);
        } else if ("Equipment".equals(itemType)) {
            createEquipmentForm(grid);
        }

        // Scene and Stage setup
        Scene scene = new Scene(grid);
        stage.setTitle("Additional Details Form");
        stage.setScene(scene);
        stage.show();
        this.stage=stage;
    }

    private void createMedicalSuppliesForm(GridPane grid) {
        // Manufacturing Date
        Label manufacturingDateLabel = new Label("Manufacturing Date:");
        DatePicker manufacturingDatePicker = new DatePicker();
        grid.add(manufacturingDateLabel, 0, 0);
        grid.add(manufacturingDatePicker, 1, 0);

        // Expiry Date
        Label expiryDateLabel = new Label("Expiry Date:");
        DatePicker expiryDatePicker = new DatePicker();
        grid.add(expiryDateLabel, 0, 1);
        grid.add(expiryDatePicker, 1, 1);

        // Temperature
        Label temperatureLabel = new Label("Temperature:");
        TextField temperatureField = new TextField();
        grid.add(temperatureLabel, 0, 2);
        grid.add(temperatureField, 1, 2);

        // Dosage Form
        Label dosageFormLabel = new Label("Dosage Form:");
        ComboBox<String> dosageFormComboBox = new ComboBox<>();
        dosageFormComboBox.getItems().addAll("Tablet", "Liquid", "Injection");
        grid.add(dosageFormLabel, 0, 3);
        grid.add(dosageFormComboBox, 1, 3);

       
     
     // Submit Button for Medical Supplies
        Button submitButton = new Button("Submit");
        grid.add(submitButton, 1, 4);
        submitButton.setOnAction(event -> {
            try {
                MedicalSupplies medicalSupplies = new MedicalSupplies(
                        inventoryItem,
                        manufacturingDatePicker.getValue(),
                        expiryDatePicker.getValue(),
                        Integer.parseInt(temperatureField.getText()),
                        dosageFormComboBox.getValue()
                );

                InventoryDAO inventoryDAO = new InventoryDAO(connection);
                inventoryDAO.insertMedicalSupplies(medicalSupplies);

                // Clear form fields after submission
                manufacturingDatePicker.setValue(null);
                expiryDatePicker.setValue(null);
                temperatureField.clear();
                dosageFormComboBox.getSelectionModel().clearSelection();

                // Show confirmation alert
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Medical Supplies Added Successfully", ButtonType.OK);
                alert.show();

                // Delay closing the stage and fading out the alert
                final Stage currentStage = (Stage) alert.getDialogPane().getScene().getWindow();
                Timeline timeline = new Timeline();
                KeyFrame key = new KeyFrame(Duration.seconds(2), new KeyValue(alert.getDialogPane().opacityProperty(), 0));
                timeline.getKeyFrames().add(key);
                timeline.setOnFinished(e -> {
                    currentStage.hide(); // Hide the stage
                    stage.hide(); // Hide the main stage
                });
                timeline.play();
            } catch (NumberFormatException | SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage(), ButtonType.OK).showAndWait();
            }
        });


    }

    private void createEquipmentForm(GridPane grid) {
        // Condition
        Label conditionLabel = new Label("Condition:");
        TextField conditionField = new TextField();
        grid.add(conditionLabel, 0, 0);
        grid.add(conditionField, 1, 0);

        // Warranty Date
        Label warrantyDateLabel = new Label("Warranty Date:");
        DatePicker warrantyDatePicker = new DatePicker();
        grid.add(warrantyDateLabel, 0, 1);
        grid.add(warrantyDatePicker, 1, 1);

        // Submit Button for Equipment
        Button submitButton = new Button("Submit");
        grid.add(submitButton, 1, 2);
        submitButton.setOnAction(event -> {
            try {
                Equipment equipment = new Equipment(
                        inventoryItem,
                        conditionField.getText(),
                        warrantyDatePicker.getValue()
                );

                InventoryDAO inventoryDAO = new InventoryDAO(connection);
                inventoryDAO.insertEquipment(equipment);

                // Clear form fields after submission
                conditionField.clear();
                warrantyDatePicker.setValue(null);

                // Show confirmation alert
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Equipment Added Successfully", ButtonType.OK);
                alert.show();

                // Delay closing the stage and fading out the alert
                Timeline timeline = new Timeline();
                KeyFrame key = new KeyFrame(Duration.seconds(2), new KeyValue(alert.getDialogPane().opacityProperty(), 0));
                timeline.getKeyFrames().add(key);
                timeline.setOnFinished(e -> {
                    stage.hide(); // Hide the stage
                });
                timeline.play();
            } catch (NumberFormatException | SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage(), ButtonType.OK).showAndWait();
            }
        });

    }
}

