package controllers;

import java.io.IOException;

import app.App;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class EmployeeController {
    @FXML public ListView<String> employeeListView;
    @FXML private TextField nameField;
    @FXML private TextField roleField;

    private final ObservableList<String> employees = FXCollections.observableArrayList();
    @FXML
    public void initialize() {
        employees.setAll(App.db.employeeList());
        if (employeeListView != null) {
            employeeListView.setItems(employees);
        }
    }
    @FXML
     public void addEmployee() {
        String name = nameField.getText().trim();
        String role = roleField.getText().trim();
        if (!name.isEmpty() && !role.isEmpty()) {
            App.db.addEmployee(name, role);
            refreshList();
            nameField.clear();
            roleField.clear();
        }
    }
    private void refreshList() {
        employees.setAll(App.db.employeeList());
        employeeListView.setItems(employees);
    }
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
    private void switchScene(ActionEvent event, String fxmlPath) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
