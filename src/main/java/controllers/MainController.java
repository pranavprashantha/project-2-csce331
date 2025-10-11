package controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MainController {
    public void goToCheckout(ActionEvent event) throws IOException {
        switchScene(event, "/views/main.fxml");
    }

    public void goToOrderHistory(ActionEvent event) throws IOException {
        switchScene(event, "/views/trendview.fxml");
    }

    public void goToInventory(ActionEvent event) throws IOException {
        switchScene(event, "/views/inventory.fxml");
    }
    public void goToManagerView(ActionEvent event) throws IOException {
        switchScene(event, "/views/managerView.fxml");
    }
    public void goToModify(MouseEvent event) throws IOException {
        switchScene(event, "/views/drinkModify.fxml");
    }
    private void switchScene(ActionEvent event, String fxmlPath) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    private void switchScene(MouseEvent event, String fxmlPath) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
