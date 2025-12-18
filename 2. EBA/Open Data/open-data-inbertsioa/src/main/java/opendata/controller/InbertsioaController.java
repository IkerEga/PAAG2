package opendata.controller;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import opendata.App;

import java.io.IOException;

public class InbertsioaController {

    @FXML
    private ComboBox<String> cmbIndizea;

    @FXML
    private TextField txtEkarpena;

    @FXML
    private TextField txtUrtea;

    @FXML
    private Label lblEmaitza;

    @FXML
    private LineChart<Number, Number> grafikoa;

    @FXML
    private NumberAxis xAldea;

    @FXML
    private NumberAxis yAldea;

    @FXML
    private void joanMenura() throws IOException {
        App.setRoot("menu_nagusia");
    }

    @FXML
    private void kalkulatuInbertsioa() {
        try {
            String indizea = cmbIndizea.getValue();
            double ekarpena = Double.parseDouble(txtEkarpena.getText());
            int urtea = Integer.parseInt(txtUrtea.getText());

            lblEmaitza.setText("Datuak prestatzen...");

            // Hurrengo pausoa: kalkulua eta grafikoa

        } catch (Exception e) {
            lblEmaitza.setText("Errorea: Ziurtatu datuak ondo sartu dituzula.");
        }
    }
}
