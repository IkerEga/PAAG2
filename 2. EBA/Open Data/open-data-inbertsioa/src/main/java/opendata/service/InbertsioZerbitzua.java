package opendata.service;

import javafx.scene.chart.XYChart;

public class InbertsioZerbitzua {

    private final InflazioZerbitzua inflazioa;
    private final IndizeZerbitzua indizea;

    public InbertsioZerbitzua(InflazioZerbitzua inflazioa, IndizeZerbitzua indizea) {
        this.inflazioa = inflazioa;
        this.indizea = indizea;
    }

    public XYChart.Series<Number, Number> sortuSerieNominala(int hasieraUrtea, double hilekoEkarpena) {
        XYChart.Series<Number, Number> serie = new XYChart.Series<>();
        double balioa = 0.0;

        int azkenUrtea = Math.min(indizea.lortuAzkenUrtea(), inflazioa.lortuAzkenUrtea());

        for (int urtea = hasieraUrtea; urtea <= azkenUrtea; urtea++) {
            balioa += hilekoEkarpena * 12.0;
            double r = indizea.lortuUrtekoEtekina(urtea);
            balioa *= (1.0 + r);
            serie.getData().add(new XYChart.Data<>(urtea, balioa));
        }
        return serie;
    }

    public XYChart.Series<Number, Number> sortuSerieGaurkoEurotan(int hasieraUrtea, double hilekoEkarpena) {
        XYChart.Series<Number, Number> serie = new XYChart.Series<>();
        double balioa = 0.0;

        int azkenUrtea = Math.min(indizea.lortuAzkenUrtea(), inflazioa.lortuAzkenUrtea());
        double indizeGaur = inflazioa.lortuIndizea(azkenUrtea);

        for (int urtea = hasieraUrtea; urtea <= azkenUrtea; urtea++) {
            balioa += hilekoEkarpena * 12.0;
            double r = indizea.lortuUrtekoEtekina(urtea);
            balioa *= (1.0 + r);

            double indizeUrtekoa = inflazioa.lortuIndizea(urtea);
            double egokitua = (indizeUrtekoa == 0) ? balioa : balioa * (indizeGaur / indizeUrtekoa);

            serie.getData().add(new XYChart.Data<>(urtea, egokitua));
        }
        return serie;
    }
}
