package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.event.Event;              
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class MainController {

    public ListView<String> orderListView; //listview fx:id is orderListView
    private final ObservableList<String> currentOrder = FXCollections.observableArrayList();
    private final List<Integer> currentOrderPrices = new ArrayList<>();



    public void goToCheckout(ActionEvent event) throws IOException {
        switchScene(event, "/views/main.fxml");
    }

    public void goToOrderHistory(ActionEvent event) throws IOException {
        switchScene(event, "/views/trendview.fxml");
    }

    public void goToInventory(ActionEvent event) throws IOException {
        switchScene(event, "/views/inventory.fxml");
    }
    public void goToModify(MouseEvent event) throws IOException {
        Rectangle clickedRectangle = (Rectangle) event.getSource();
        String slotId = clickedRectangle.getId(); //slot fx:ids are slot1,slot2,...
        int id = Integer.parseInt(slotId.replace("slot", "")) - 1; //break up string to int to get id=0 for slot1, id=1 for slot2,... 
        addToOrder(id);
        switchScene(event, "/views/drinkModify.fxml");
        }
        

    //add a drink to the order.
    public void addToOrder(int id) throws IOException{
        //string getDrinkName(id) to display on ListView on GUI
        //int getDrinkPrice(id) to display on ListView on GUI
        String drinkName = getDrinkName(id);
        int drinkPrice = getDrinkPrice(id);
        currentOrder.add(drinkName + " - $" + drinkPrice);
        currentOrderPrices.add(drinkPrice);

        if (orderListView != null) {
            orderListView.setItems(currentOrder); // update listview display
        }
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
        double totalPrice = currentOrderPrices.stream().mapToInt(Integer::intValue).sum();
        String drinks = String.join(", ", currentOrder);

        //send current order to database and clear it
        completeOrder(date, week, time, totalPrice, drinks);
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
}
