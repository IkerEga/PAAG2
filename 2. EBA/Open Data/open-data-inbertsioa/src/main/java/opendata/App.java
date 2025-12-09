package opendata;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(kargatuFXML("menu_nagusia"), 640, 480);
        stage.setScene(scene);
        stage.setTitle("Open Data – Inflazioa eta Inbertsioa");
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(kargatuFXML(fxml));
    }

    private static Parent kargatuFXML(String fxml) throws IOException {
        FXMLLoader kargatzailea = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return kargatzailea.load();
    }

    public static void main(String[] args) {
        launch();
    }
}
