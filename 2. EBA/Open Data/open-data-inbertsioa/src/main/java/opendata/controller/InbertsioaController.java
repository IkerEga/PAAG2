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
    public void initialize() {
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

            String csv = aukeratuIndizearenCsv(indizea);

            InflazioZerbitzua inflazioZerbitzua = new InflazioZerbitzua();
            IndizeZerbitzua indizeZerbitzua = new IndizeZerbitzua(csv);
            InbertsioZerbitzua inbertsioZerbitzua = new InbertsioZerbitzua(inflazioZerbitzua, indizeZerbitzua);

            if (hasieraUrtea < indizeZerbitzua.lortuHasierakoUrtea()) {
                lblEmaitza.setText("Errorea: indizearen datuak " + indizeZerbitzua.lortuHasierakoUrtea()
                        + " urtetik hasten dira.");
                return;
            }

            grafikoa.getData().clear();

            XYChart.Series<Number, Number> nominala = inbertsioZerbitzua.sortuSerieNominala(hasieraUrtea, ekarpena);
            nominala.setName(indizea + " (Nominala)");

            XYChart.Series<Number, Number> gaurkoa = inbertsioZerbitzua.sortuSerieGaurkoEurotan(hasieraUrtea, ekarpena);
            gaurkoa.setName(indizea + " (Gaurko €)");

            
            XYChart.Series<Number, Number> aurrezkiSerie = new XYChart.Series<>();
            aurrezkiSerie.setName("Aurrezten bakarrik (nominala)");

            int azkenUrtea = Integer.parseInt(txtUrtea.getText().trim());
            /* el mismo endYear que uses en la simulación */;
            double metatua = 0.0;

            for (int y = hasieraUrtea; y <= azkenUrtea; y++) {
                metatua += ekarpena * 12.0;
                aurrezkiSerie.getData().add(new XYChart.Data<>(y, metatua));
            }

            grafikoa.getData().add(aurrezkiSerie);

            grafikoa.getData().addAll(nominala, gaurkoa);

            // azken puntua erakutsi
            if (!nominala.getData().isEmpty()) {
                double azkenNominala = nominala.getData().get(nominala.getData().size() - 1).getYValue().doubleValue();
                double azkenGaurkoa = gaurkoa.getData().get(gaurkoa.getData().size() - 1).getYValue().doubleValue();
                lblEmaitza.setText(
                        "Azken balioa -> Nominala: " + String.format(java.util.Locale.US, "%.2f", azkenNominala)
                                + " € | Gaurko €: " + String.format(java.util.Locale.US, "%.2f", azkenGaurkoa) + " €");
            } else {
                lblEmaitza.setText("Ez dago daturik marrazteko.");
            }

        } catch (Exception e) {
            lblEmaitza.setText("Errorea: Ziurtatu datuak ondo sartu dituzula. (" + e.getMessage() + ")");
        }
    }

    private String aukeratuIndizearenCsv(String indizea) {
        if (indizea == null)
            return "/opendata/datuak/sp500.csv";

        switch (indizea) {
            case "S&P 500":
                return "/opendata/datuak/sp500.csv";
            case "Nasdaq 100":
                return "/opendata/datuak/nasdaq100.csv";
            case "Ibex 35":
                return "/opendata/datuak/ibex35.csv";
            default:
                return "/opendata/datuak/sp500.csv";
        }
    }

}
