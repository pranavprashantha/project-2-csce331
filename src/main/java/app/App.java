package app;
import database.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import java.util.*;

public class App extends Application {

    public static Database db;

    //javaFX app
    @Override
    public void start(Stage stage) throws Exception {

        //Database Connection
        App.db = new Database("jdbc:postgresql://csce-315-db.engr.tamu.edu/gang_x3_db", "gang_x3", "gang_x3");   
        
        //set scene and show stage
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/main.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("JavaFX Application");
        stage.setScene(scene); 
        stage.show();
    }

    public static void main(String[] args) {
    launch();
    }
}