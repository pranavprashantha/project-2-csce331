package controllers;

import app.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.event.Event;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class MainController {
    @FXML
    private Button chargeButton;

    @FXML
    private Text taxText;

    @FXML
    public ListView<String> orderListView; //listview fx:id is orderListView
    private final ObservableList<String> currentOrder = FXCollections.observableArrayList();
    private final List<Double> currentOrderPrices = new ArrayList<>();



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
        //switchScene(event, "/views/drinkModify.fxml");
        System.out.println("clicked");
        Node clickedNode = (Node) event.getSource();

        String slotId = clickedNode.getId();
        if (slotId == null && clickedNode instanceof Parent parent) {
            for (Node child : parent.getChildrenUnmodifiable()) {
                if (child.getId() != null) {
                    slotId = child.getId();
                    break;
                }
            }
        }

        //String slotId = clickedNode.getId(); //slot fx:ids are slot1,slot2,...
        if (slotId == null){
            return;
        } 
        int id = Integer.parseInt(slotId.replace("slot", "")) - 1; //break up string to int to get id=0 for slot1, id=1 for slot2,... 
        System.out.println("Adding drink with id: " + id);
        addToOrder(id);
        //int id = Integer.parseInt(slotId.replace("slot", "")) - 1; //break up string to int to get id=0 for slot1, id=1 for slot2,... 
        //addToOrder(id);
        
        }
        

    //add a drink to the order.
    public void addToOrder(int id) throws IOException{
        System.out.println(id);
        //string getDrinkName(id) to display on ListView on GUI
        //int getDrinkPrice(id) to display on ListView on GUI
        String drinkName = App.db.getDrinkName(id);
        double drinkPrice = App.db.getDrinkPrice(id);
        currentOrder.add(String.format("%s - $%.2f", drinkName, drinkPrice));
        currentOrderPrices.add(drinkPrice);

        if (orderListView != null) {
            orderListView.setItems(currentOrder); // update listview display
        }
        System.out.println(currentOrder);
    }

    //using the current order stored, calculate parameters to call completeOrder which sends to database
    public void makeOrder() throws IOException{
        
        //make sure an order is made
        if(currentOrder.isEmpty()){
            return; 
        }

        //get date, time, and weeks since 12/22/2024 (start of our business)
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        LocalDate startDate = LocalDate.of(2024, 12, 22); //start date
        long daysBetween = ChronoUnit.DAYS.between(startDate, date); //calculate weeks since start date
        int week = (int) (daysBetween / 7);

        //get price, and string of ordered drinks
        double totalPrice = currentOrderPrices.stream().mapToDouble(Double::doubleValue).sum();
        String drinks = String.join(", ", currentOrder);

        //update total and tax on GUI
        double tax = totalPrice * 0.0825;
        chargeButton.setText("Charge $" + String.format("%.2f", totalPrice));
        taxText.setText("Tax: $" + String.format("%.2f", tax));

        //send current order to database and clear it
        App.db.completeOrder(date, week, time, totalPrice, drinks);
        System.out.println("Date: " + date + ", Week: " + week + ", Time: " + time + 
                   ", Total Price: " + totalPrice + ", Drinks: " + drinks);
        currentOrder.clear();
        currentOrderPrices.clear();

        if (orderListView != null){
            orderListView.setItems(currentOrder); // update listview display
        }

    }

    

    private void switchScene(Event event, String fxmlPath) throws IOException {
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
