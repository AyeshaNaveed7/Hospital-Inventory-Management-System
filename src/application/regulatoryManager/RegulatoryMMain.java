package application.regulatoryManager;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.Connection;

public class RegulatoryMMain extends Application {

    private BorderPane mainLayout;
    private Connection connection;
    

    public RegulatoryMMain(Connection connection) {
        this.connection = connection;
       
    }

    @Override
    public void start(Stage primaryStage) {
        mainLayout = new BorderPane();

        VBox leftSide = setupLeftSide();

        mainLayout.setLeft(leftSide);

        Scene scene = new Scene(mainLayout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);

        primaryStage.show();
    }

    private VBox setupLeftSide() {
        VBox leftSide = new VBox(10);
        leftSide.setPadding(new Insets(20, 10, 10, 10));

        Button medicalSuppliesButton = new Button("Medical Supplies");
        medicalSuppliesButton.setOnAction(event -> {
            RMedicalSuppliesTableView medicalSuppliesTableView = new RMedicalSuppliesTableView(connection);
            medicalSuppliesTableView.refreshRMedicalSuppliesTableView();
            setRightTableView(medicalSuppliesTableView.getTableView());
        });

        Button equipmentButton = new Button("Equipment");
        equipmentButton.setOnAction(event -> {
            REquipmentsTableView equipmentsTableView = new REquipmentsTableView(connection);
            equipmentsTableView.refreshREquipmentsTableView();
            setRightTableView(equipmentsTableView.getTableView());
        });

        VBox.setMargin(medicalSuppliesButton, new Insets(0, 0, 10, 20));
        VBox.setMargin(equipmentButton, new Insets(0, 0, 10, 20));

        leftSide.getChildren().addAll(medicalSuppliesButton, equipmentButton);
        return leftSide;
    }

    private void setRightTableView(TableView<?> newTableView) {
        VBox rightLayout = new VBox(10);
        rightLayout.setAlignment(Pos.CENTER);
        rightLayout.getChildren().add(newTableView);

        mainLayout.setCenter(rightLayout);
    }

    
}

