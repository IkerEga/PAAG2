package opendata.controller;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import opendata.App;
import opendata.service.IndizeZerbitzua;
import opendata.service.InbertsioZerbitzua;
import opendata.service.InflazioZerbitzua;

import java.io.IOException;

public class InbertsioaController {

    @FXML private ComboBox<String> cmbIndizea;
    @FXML private TextField txtEkarpena;
    @FXML private TextField txtUrtea;
    @FXML private Label lblEmaitza;

    @FXML private LineChart<Number, Number> grafikoa;
    @FXML private NumberAxis xAldea;
    @FXML private NumberAxis yAldea;

    @FXML
    public void initialize() {
        // FXML-ean items badituzu ere, ez du minik egiten
        if (cmbIndizea.getItems().isEmpty()) {
            cmbIndizea.getItems().addAll("S&P 500", "Nasdaq 100", "Ibex 35");
        }
        cmbIndizea.getSelectionModel().selectFirst();

        grafikoa.setAnimated(false);
        xAldea.setForceZeroInRange(false);
    }

    @FXML
    private void joanMenura() throws IOException {
        App.setRoot("menu_nagusia");
    }

    @FXML
    private void kalkulatuInbertsioa() {
        try {
            String indizea = cmbIndizea.getValue();
            double ekarpena = Double.parseDouble(txtEkarpena.getText().trim().replace(",", "."));
            int hasieraUrtea = Integer.parseInt(txtUrtea.getText().trim());

            String csvPath = aukeratuIndizearenCsv(indizea);

            InflazioZerbitzua inflazioZerbitzua = new InflazioZerbitzua();
            IndizeZerbitzua indizeZerbitzua = new IndizeZerbitzua(csvPath);
            InbertsioZerbitzua inbertsioZerbitzua = new InbertsioZerbitzua(inflazioZerbitzua, indizeZerbitzua);

            if (hasieraUrtea < indizeZerbitzua.lortuHasierakoUrtea()) {
                lblEmaitza.setText("Errorea: indizearen datuak " + indizeZerbitzua.lortuHasierakoUrtea() + " urtetik hasten dira.");
                return;
            }

            grafikoa.getData().clear();

            XYChart.Series<Number, Number> nominala =
                    inbertsioZerbitzua.sortuSerieNominala(hasieraUrtea, ekarpena);

            XYChart.Series<Number, Number> egokitua =
                    inbertsioZerbitzua.sortuSerieInflazioEgokituaGaur(hasieraUrtea, ekarpena);

            nominala.setName(indizea + " (Nominala)");
            egokitua.setName(indizea + " (Gaurko €)");

            grafikoa.getData().addAll(nominala, egokitua);

            lblEmaitza.setText("Kalkulua eginda: " + indizea);

        } catch (Exception e) {
            lblEmaitza.setText("Errorea: datuak ondo sartu. (" + e.getMessage() + ")");
        }
    }

    private String aukeratuIndizearenCsv(String indizea) {
        return switch (indizea) {
            case "S&P 500" -> "/opendata/datuak/sp500.csv";
            case "Nasdaq 100" -> "/opendata/datuak/nasdaq100.csv";
            case "Ibex 35" -> "/opendata/datuak/ibex35.csv";
            default -> "/opendata/datuak/sp500.csv";
        };
    }
}
