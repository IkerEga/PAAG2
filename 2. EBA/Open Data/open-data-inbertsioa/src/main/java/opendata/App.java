package opendata;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.io.IOException;

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(kargatuFXML("menu_nagusia"), 1200, 600);
        stage.setTitle("Inflazioa eta Inbertsioa · Open Data");
        stage.getIcons().add(
                new Image(App.class.getResourceAsStream("/opendata/images/Inbertsioa-Inflazioa.png")));
        stage.setScene(scene);
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
