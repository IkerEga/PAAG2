package opendata.service;

import opendata.model.InflazioUrtea;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class InflazioZerbitzua {

    private static final String INFLAZIO_CSV = "/opendata/datuak/inflazioa.csv";

    private final List<InflazioUrtea> urteroInflazioa;

    public InflazioZerbitzua() {
        this.urteroInflazioa = kargatuDatuak();
    }

    private List<InflazioUrtea> kargatuDatuak() {
        List<InflazioUrtea> zerrenda = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        Objects.requireNonNull(getClass().getResourceAsStream(INFLAZIO_CSV),
                                "Ez da aurkitu resource-a: " + INFLAZIO_CSV),
                        StandardCharsets.UTF_8))) {

            String lerroa = reader.readLine(); // goiburua
            while ((lerroa = reader.readLine()) != null) {
                lerroa = lerroa.trim();
                if (lerroa.isEmpty()) continue;

                String[] zatiak = lerroa.split(";");
                int urtea = Integer.parseInt(zatiak[0].trim());
                double indizea = Double.parseDouble(zatiak[1].trim().replace(",", "."));

                zerrenda.add(new InflazioUrtea(urtea, indizea));
            }

        } catch (Exception e) {
            throw new RuntimeException("Errorea inflazio CSV-a kargatzean: " + e.getMessage(), e);
        }

        zerrenda.sort(Comparator.comparingInt(InflazioUrtea::getUrtea));
        return zerrenda;
    }

    public double lortuIndizea(int urtea) {
        return urteroInflazioa.stream()
                .filter(i -> i.getUrtea() == urtea)
                .map(InflazioUrtea::getIndizea)
                .findFirst()
                .orElse(urteroInflazioa.get(urteroInflazioa.size() - 1).getIndizea());
    }

    public int lortuAzkenUrtea() {
        return urteroInflazioa.get(urteroInflazioa.size() - 1).getUrtea();
    }

    public int lortuHasierakoUrtea() {
        return urteroInflazioa.get(0).getUrtea();
    }

    public double kalkulatuGaurkoBalioa(double zenbatekoa, int jatorriUrtea) {
        int azkenUrtea = lortuAzkenUrtea();
        double indizeJatorria = lortuIndizea(jatorriUrtea);
        double indizeGaur = lortuIndizea(azkenUrtea);

        if (indizeJatorria == 0) return zenbatekoa;
        return zenbatekoa * (indizeGaur / indizeJatorria);
    }

    public List<InflazioUrtea> lortuInflazioSeriea(int hasieraUrtea) {
        double jatorrizkoIndizea = lortuIndizea(hasieraUrtea);
        return urteroInflazioa.stream()
                .filter(i -> i.getUrtea() >= hasieraUrtea)
                .map(i -> new InflazioUrtea(i.getUrtea(), i.getIndizea() / jatorrizkoIndizea))
                .collect(Collectors.toList());
    }
}
