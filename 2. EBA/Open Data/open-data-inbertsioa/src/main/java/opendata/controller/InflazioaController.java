package opendata.controller;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import opendata.App;
import opendata.service.InflazioZerbitzua;
import opendata.model.InflazioUrtea;

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
    private void joanMenura() throws IOException {
        App.setRoot("menu_nagusia");
    }

    @FXML
    private void kalkulatuEmaitzak() {
        try {
            double zenbatekoa = Double.parseDouble(txtZenbatekoa.getText());
            int urtea = Integer.parseInt(txtUrtea.getText());

            double gaurkoBalioa = inflazioZerbitzua.kalkulatuBalioEgokitua(zenbatekoa, urtea);
            lblEmaitza
                    .setText(String.format("%,.2f € (%d urtean) = %,.2f € gaur egun", zenbatekoa, urtea, gaurkoBalioa));

            erakutsiGrafikoa(zenbatekoa, urtea);

        } catch (NumberFormatException e) {
            lblEmaitza.setText("Errorea: Sartu balio egokiak (zenbatekoa eta urtea)");
        }
    }

    private void erakutsiGrafikoa(double zenbatekoa, int hasieraUrtea) {
        grafikoa.getData().clear();
        List<InflazioUrtea> seriea = inflazioZerbitzua.lortuInflazioSeriea(hasieraUrtea);

        int azkenUrtea = seriea.get(seriea.size() - 1).getUrtea();

        xAldea.setAutoRanging(false);
        xAldea.setLowerBound(hasieraUrtea);
        xAldea.setUpperBound(azkenUrtea);
        xAldea.setTickUnit(5); // cada 5 años, puedes ajustar

        yAldea.setAutoRanging(true); // el eje Y puede ajustarse solo

        XYChart.Series<Number, Number> datuak = new XYChart.Series<>();
        datuak.setName("Erosahalmena (€)");

        for (InflazioUrtea urtea : seriea) {
            double egokitua = zenbatekoa * urtea.getIndizea();
            datuak.getData().add(new XYChart.Data<>(urtea.getUrtea(), egokitua));
        }

        grafikoa.getData().add(datuak);
    }
}
