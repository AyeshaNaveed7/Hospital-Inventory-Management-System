package application.regulatoryManager;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;

import database.regulatory.RegulatoryViewer;

public class EquipmentAdditionalDetailForm extends Application {

    private int rId;
    private Connection connection;

    public EquipmentAdditionalDetailForm(int regulatoryId, Connection connection) {
        this.rId = regulatoryId;
        this.connection = connection;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Maintenance Input Form");

        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));

        Label periodicMaintenanceLabel = new Label("Periodic Maintenance Date:");
        grid.add(periodicMaintenanceLabel, 0, 0);

        DatePicker periodicMaintenancePicker = new DatePicker();
        grid.add(periodicMaintenancePicker, 1, 0);

        Label complianceStatusLabel = new Label("Compliance Status:");
        grid.add(complianceStatusLabel, 0, 1);

        ComboBox<String> complianceStatusComboBox = new ComboBox<>();
        complianceStatusComboBox.getItems().addAll("Compliant", "Non Compliant");
        grid.add(complianceStatusComboBox, 1, 1);

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            LocalDate maintenanceDate = periodicMaintenancePicker.getValue();
            String complianceStatus = complianceStatusComboBox.getValue();

            try {
                // Assuming regulatoryId is either derived or provided
                int regulatoryId = rId; // Replace with actual logic to obtain the regulatoryId
                RegulatoryViewer.insertPeriodicDateAndCompliance(connection, regulatoryId, maintenanceDate, complianceStatus);
                System.out.println("Data submitted successfully.");
            } catch (SQLException ex) {
                ex.printStackTrace();
                System.out.println("Failed to submit data.");
            }
        });
        grid.add(submitButton, 1, 2);

        Scene scene = new Scene(grid, 400, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

   
}
