package opendata.controller;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import opendata.App;
import opendata.model.InflazioUrtea;
import opendata.service.InflazioZerbitzua;

import java.io.IOException;
import java.util.List;

public class InflazioaController {

    @FXML
    private TextField txtZenbatekoa;
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

    private final InflazioZerbitzua inflazioZerbitzua = new InflazioZerbitzua();

    @FXML
    public void initialize() {
        if (grafikoa != null) {
            grafikoa.setAnimated(false);
            xAldea.setForceZeroInRange(false);
        }
    }

    @FXML
    private void joanMenura() throws IOException {
        App.setRoot("menu_nagusia");
    }

    @FXML
    private void kalkulatuEmaitzak() {
        try {
            double zenbatekoa = Double.parseDouble(txtZenbatekoa.getText().trim().replace(",", "."));
            int urtea = Integer.parseInt(txtUrtea.getText().trim());

            double gaurkoBalioa = inflazioZerbitzua.kalkulatuGaurkoBalioa(zenbatekoa, urtea);
            lblEmaitza.setText("Gaurko balioa (inflazioarekin): "
                    + String.format(java.util.Locale.US, "%.2f", gaurkoBalioa) + " €");

            // Seriea marraztu (indize erlatiboa)
            // Serieak marraztu: (1) Gaurko baliokidea (gora) + (2) Erosahalmena (behera)
            if (grafikoa != null) {
                grafikoa.getData().clear();

                XYChart.Series<Number, Number> serieGaurko = new XYChart.Series<>();
                serieGaurko.setName("Gaurko baliokidea (€)");

                XYChart.Series<Number, Number> serieErosahalmena = new XYChart.Series<>();
                serieErosahalmena.setName("Erosahalmena (hasierako €tan)");

                List<InflazioUrtea> puntuak = inflazioZerbitzua.lortuInflazioSeriea(urtea);

                for (InflazioUrtea p : puntuak) {
                    double ind = p.getIndizea();
                    if (ind <= 0)
                        continue;

                    // 1) Zenbat beharko zenuke urte horretan (edo gaur) erosahalmen bera izateko
                    double gaurkoBaliokidea = zenbatekoa * ind;

                    // 2) Zure zenbatekoa zenbat “balio” duen urte horretan (erosahalmena) ->
                    // beherantz joaten da
                    double erosahalmena = zenbatekoa / ind;

                    serieGaurko.getData().add(new XYChart.Data<>(p.getUrtea(), gaurkoBaliokidea));
                    serieErosahalmena.getData().add(new XYChart.Data<>(p.getUrtea(), erosahalmena));
                }
                
                if (!puntuak.isEmpty()) {
                    double azkenInd = puntuak.get(puntuak.size() - 1).getIndizea();
                    if (azkenInd > 0) {
                        double gaurkoErosahalmena = zenbatekoa / azkenInd;
                        double galeraPct = (1.0 - (1.0 / azkenInd)) * 100.0;

                        lblEmaitza.setText(
                                "Gaurko balioa (inflazioarekin): "
                                        + String.format(java.util.Locale.US, "%.2f", gaurkoBalioa) + " €\n" +
                                        "Erosahalmena gaur (hasierako €tan): "
                                        + String.format(java.util.Locale.US, "%.2f", gaurkoErosahalmena) + " €\n" +
                                        "Balio-galera: " + String.format(java.util.Locale.US, "%.2f", galeraPct)
                                        + " %");
                    }
                }

                grafikoa.getData().addAll(serieGaurko, serieErosahalmena);

                // (Aukeran) Y ardatzaren label-a argitu
                yAldea.setLabel("€ (balio erlatiboa)");
            }

        } catch (Exception e) {
            lblEmaitza.setText("Errorea: sarrerak ez dira zuzenak. (" + e.getMessage() + ")");
        }
    }
}
