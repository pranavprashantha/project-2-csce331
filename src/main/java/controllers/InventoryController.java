package controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class InventoryController {


    private static final String URL  = "jdbc:postgresql://csce-315-db.engr.tamu.edu/gang_x3_db";
    private static final String USER = "gang_x3";
    private static final String PASS = "gang_x3";


    private static final boolean AUTO_RESET_ON_START = true;


    @FXML private VBox inventoryList;


    @FXML
    public void initialize() {
        if (AUTO_RESET_ON_START) {
            resetInventoryToBaseline();  
        } else {
            refreshFromDb();
        }
    }


    private void refreshFromDb() {
        inventoryList.getChildren().clear();
        for (Row r : fetchInventory()) {
            inventoryList.getChildren().add(buildRow(r));
        }
    }


    private HBox buildRow(Row r) {
        HBox box = new HBox(12);
        box.setAlignment(Pos.CENTER_LEFT);
        box.setPadding(new Insets(6, 0, 6, 0));


        Label name = new Label(r.name);
        name.setMinWidth(360);
        name.setStyle("-fx-font-size: 14px;");


        Button statusBtn = new Button();
        statusBtn.setMinWidth(200);
        applyStatusText(statusBtn, r.stock, r.recommended);


        statusBtn.setOnAction(e -> {
            Integer newStock = setStockToRecommended(r.name);
            if (newStock == null) {
                showError("Failed to restock " + r.name);
                return;
            }
            r.stock = newStock;
            applyStatusText(statusBtn, r.stock, r.recommended);
        });


        box.getChildren().addAll(name, statusBtn);
        return box;
    }


    private void applyStatusText(Button btn, int stock, int rec) {
        if (stock < rec && stock <= Math.max(5, (int)Math.floor(rec * 0.5))) {
            btn.setText("Restock Required (" + stock + ")");
            btn.setTextFill(Color.web("#ff0d00"));
        } else if (stock < rec) {
            btn.setText("Low Stock (" + stock + ")");
            btn.setTextFill(Color.web("#ffa41a"));
        } else {
            btn.setText("In Stock (" + stock + ")");
            btn.setTextFill(Color.web("#209e2a"));
        }
    }


    private List<Row> fetchInventory() {
        String sql = "SELECT ingredient_id, name, unit, unit_price, stock, recommended_stock " +
                     "FROM inventory ORDER BY ingredient_id";
        List<Row> out = new ArrayList<>();
        try (Connection c = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Row r = new Row();
                r.id = rs.getInt("ingredient_id");
                r.name = rs.getString("name");
                r.unit = rs.getString("unit");
                r.unitPrice = rs.getBigDecimal("unit_price").doubleValue();
                r.stock = rs.getInt("stock");
                r.recommended = rs.getInt("recommended_stock");
                out.add(r);
            }
        } catch (SQLException e) {
            showError("DB error (fetchInventory): " + e.getMessage());
        }
        return out;
    }


    private Integer setStockToRecommended(String name) {
        String sql = "UPDATE inventory SET stock = recommended_stock WHERE name = ? RETURNING stock";
        try (Connection c = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("stock");
            }
        } catch (SQLException e) {
            showError("DB error (setStockToRecommended): " + e.getMessage());
        }
        return null;
    }


    @FXML
    public void resetInventoryToBaseline() {
        String[][] baseline = {
            {"Milk Tea","500"}, {"Jasmine Green Tea","400"}, {"Chai Tea","350"},
            {"Vanilla","200"}, {"Coconut Flavored Black Tea","300"}, {"Matcha Green Tea","250"},
            {"Red Beans","180"}, {"Oolong Tea","280"}, {"Taro Flavored Beverage","260"},
            {"Thai Tea","420"}, {"Coffee","500"}, {"Salted Caramel","220"},
            {"Honeydew Flavored Beverage","240"}, {"Vanilla Ice Cream","150"},
            {"Black Coffee","480"}, {"Mocha Flavor","210"}, {"Sweetener","800"},
            {"Creamer","700"}, {"Half and Half","300"}, {"Boba","600"}
        };
        String sql = "UPDATE inventory SET stock = ? WHERE name = ?";


        try (Connection c = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = c.prepareStatement(sql)) {


            for (String[] row : baseline) {
                ps.setInt(1, Integer.parseInt(row[1]));
                ps.setString(2, row[0]);
                ps.addBatch();
            }
            ps.executeBatch();


        } catch (SQLException e) {
            showError("Reset failed: " + e.getMessage());
            return;
        }
        refreshFromDb();
    }


    @FXML private void goToCheckout(ActionEvent e)     { switchScene(e, "/views/main.fxml"); }
    @FXML private void goToOrderHistory(ActionEvent e) { switchScene(e, "/views/Trendview.fxml"); }
    @FXML private void goToInventory(ActionEvent e)    { switchScene(e, "/views/inventory.fxml"); }


    private void switchScene(ActionEvent e, String fxmlPath) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception ex) {
            showError("Navigation failed: " + ex.getMessage());
        }
    }


    private void showError(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR, msg);
        a.setHeaderText(null);
        a.setTitle("Error");
        a.showAndWait();
    }


    private static class Row {
        int id; String name; String unit; double unitPrice; int stock; int recommended;
    }
}

