package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DrinkModifyController {

    public static String selectedName;
    public static double selectedPrice;
    public static final ObservableList<String> cart = FXCollections.observableArrayList();

    @FXML
    private void handleAdd(ActionEvent event) {
        if (selectedName != null) {
            cart.add(String.format("%s - $%.2f", selectedName, selectedPrice));
        }
        goToCheckout(event);
    }

    public void goToCheckout(ActionEvent event) {
        switchScene(event, "/views/main.fxml");
    }

    public void goToOrderHistory(ActionEvent event) {
        switchScene(event, "/views/trendview.fxml");
    }

    public void goToInventory(ActionEvent event) {
        switchScene(event, "/views/inventory.fxml");
    }

    public void goToManagerView(ActionEvent event) {
        switchScene(event, "/views/managerView.fxml");
    }

    private void switchScene(ActionEvent event, String fxmlPath) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
