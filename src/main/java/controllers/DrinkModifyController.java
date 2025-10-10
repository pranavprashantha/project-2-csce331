package controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class DrinkModifyController {
public void goToCheckout(ActionEvent event) throws IOException {
        switchScene(event, "/views/main.fxml");
    }

    public void goToOrderHistory(ActionEvent event) throws IOException {
        switchScene(event, "/views/trendview.fxml");
    }

    public void goToInventory(ActionEvent event) throws IOException {
        switchScene(event, "/views/inventory.fxml");
    }

    private void switchScene(ActionEvent event, String fxmlPath) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
