package application;



import database.user.UserDAO;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.Connection;
import java.sql.SQLException;

public class ChangePasswordScreen extends Application {

    private Stage primaryStage;
    private UserDAO userDAO;
    private int userid;

    public ChangePasswordScreen(Connection connection, int userid) {
        this.userDAO = new UserDAO(connection);
        this.userid = userid;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        // Create the change password screen
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.setTitle("Change Password");

        VBox changePasswordLayout = new VBox(10);
        Label oldPasswordLabel = new Label("Old Password:");
        PasswordField oldPasswordField = new PasswordField();
        Label newPasswordLabel = new Label("New Password:");
        PasswordField newPasswordField = new PasswordField();
        Button changeButton = new Button("Change");

        changePasswordLayout.getChildren().addAll(oldPasswordLabel, oldPasswordField, newPasswordLabel, newPasswordField, changeButton);
        changePasswordLayout.setAlignment(Pos.CENTER);

        Scene changePasswordScene = new Scene(changePasswordLayout, 300, 200);
        primaryStage.setScene(changePasswordScene);

        // Set up the focus events to fade out the change password screen on focus loss
        changePasswordScene.setOnMousePressed(event -> primaryStage.requestFocus());
        primaryStage.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                // Fade out on focus loss
                primaryStage.setOpacity(0.7);
            }
        });

        // Set up action for the change button
        changeButton.setOnAction(e -> handleChangePassword(oldPasswordField.getText(), newPasswordField.getText()));

        primaryStage.show();
    }

    private void handleChangePassword(String oldPassword, String newPassword) {
        // Call the changePassword method from UserDAO with the provided username
        boolean success = userDAO.changePassword(userid, oldPassword, newPassword);

        // Show a fade-out alert message
        showAlert(success ? "Password Successfully Changed!" : "Failed to Change Password!");

        // Close the change password screen
        primaryStage.close();
    }

    private void showAlert(String message) {
        Stage alertStage = new Stage();
        alertStage.initStyle(StageStyle.UNDECORATED);
        alertStage.initModality(Modality.APPLICATION_MODAL);

        VBox alertLayout = new VBox(10);
        Label alertLabel = new Label(message);
        alertLayout.getChildren().add(alertLabel);
        alertLayout.setAlignment(Pos.CENTER);

        Scene alertScene = new Scene(alertLayout, 200, 100);
        alertStage.setScene(alertScene);

        // Set up a timeline for fade-out effect
        javafx.animation.Timeline timeline = new javafx.animation.Timeline();
        javafx.animation.KeyFrame keyFrame = new javafx.animation.KeyFrame(
                javafx.util.Duration.seconds(2), // Set the fade-out duration
                new javafx.event.EventHandler<javafx.event.ActionEvent>() {
                    @Override
                    public void handle(javafx.event.ActionEvent event) {
                        alertStage.close();
                    }
                });
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();

        alertStage.show();
    }
}
