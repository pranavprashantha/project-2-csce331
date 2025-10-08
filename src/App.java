
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import java.util.*;

public class App extends Application {

    

    //javaFX app
    @Override
    public void start(Stage stage) throws Exception {
        
        //set scene and show stage
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("JavaFX Application");
        stage.setScene(scene); 
        stage.show();
    }

    public static void main(String[] args) {
    launch();
    }
}