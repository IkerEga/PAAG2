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

        if (grafikoa != null) {
            grafikoa.setAnimated(false);
        }
        if (xAldea != null) {
            xAldea.setForceZeroInRange(false);
            xAldea.setLabel("Urtea");
        }
        if (yAldea != null) {
            yAldea.setLabel("€ (gaurko balioan)");
        }
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

            if (indizea == null) {
                lblEmaitza.setText("Aukeratu indize bat.");
                return;
            }
            if (ekarpena <= 0) {
                lblEmaitza.setText("Ekarpena 0 baino handiagoa izan behar da.");
                return;
            }

            String csv = aukeratuIndizearenCsv(indizea);

            InflazioZerbitzua inflazioZerbitzua = new InflazioZerbitzua();
            IndizeZerbitzua indizeZerbitzua = new IndizeZerbitzua(csv);
            InbertsioZerbitzua inbertsioZerbitzua = new InbertsioZerbitzua(inflazioZerbitzua, indizeZerbitzua);

            // Hasierako urtea balidatu indizearen datuekin
            if (hasieraUrtea < indizeZerbitzua.lortuHasierakoUrtea()) {
                lblEmaitza.setText("Errorea: indizearen datuak " + indizeZerbitzua.lortuHasierakoUrtea()
                        + " urtetik hasten dira.");
                return;
            }

            // Azken urtea: indize + inflazioa -> minimoa, serieek denak berdin amaitzeko
            int azkenUrtea = Math.min(indizeZerbitzua.lortuAzkenUrtea(), inflazioZerbitzua.lortuAzkenUrtea());
            if (hasieraUrtea >= azkenUrtea) {
                lblEmaitza.setText("Errorea: hasierako urtea handiegia da (ez dago tarterik marrazteko).");
                return;
            }

            // 1) Inbertituta (gaurko €) -> zure zerbitzuak kalkulatzen du
            XYChart.Series<Number, Number> inbertitutaGaur = inbertsioZerbitzua.sortuSerieGaurkoEurotan(hasieraUrtea,
                    ekarpena);
            inbertitutaGaur.setName(indizea + " (Inbertituta - Gaurko €)");

            // 2) Aurrezten bakarrik (gaurko €) -> linea “zuzen” konparazioa
            XYChart.Series<Number, Number> aurrezkiGaur = new XYChart.Series<>();
            aurrezkiGaur.setName("Aurrezten (Gaurko €)");

            double metatuaNominal = 0.0;
            double indizeGaur = inflazioZerbitzua.lortuIndizea(azkenUrtea);

            for (int y = hasieraUrtea; y <= azkenUrtea; y++) {
                metatuaNominal += ekarpena * 12.0;

                double indizeY = inflazioZerbitzua.lortuIndizea(y);
                double metatuaGaur = (indizeY == 0) ? metatuaNominal : metatuaNominal * (indizeGaur / indizeY);

                aurrezkiGaur.getData().add(new XYChart.Data<>(y, metatuaGaur));
            }

            // Pintar
            grafikoa.getData().clear();
            grafikoa.getData().addAll(aurrezkiGaur, inbertitutaGaur);

            // Texto final (comparación)
            double azkenAurrezki = aurrezkiGaur.getData().get(aurrezkiGaur.getData().size() - 1).getYValue()
                    .doubleValue();
            double azkenInbertituta = inbertitutaGaur.getData().get(inbertitutaGaur.getData().size() - 1).getYValue()
                    .doubleValue();
            double aldea = azkenInbertituta - azkenAurrezki;

            lblEmaitza.setText(
                    "Azken urtea: " + azkenUrtea +
                            " \n| Aurrezten: " + fmt(azkenAurrezki) + " € (gaurko)" +
                            " \n| Inbertituta: " + fmt(azkenInbertituta) + " € (gaurko)" +
                            " \n| Aldea: " + fmt(aldea) + " €");

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

    private String fmt(double v) {
        return String.format(java.util.Locale.US, "%.2f", v);
    }
}
