package opendata.service;

import opendata.model.InflazioUrtea;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class InflazioZerbitzua {

    private List<InflazioUrtea> urteroInflazioa;

    public InflazioZerbitzua() {
        urteroInflazioa = kargatuDatuak();
    }

    private List<InflazioUrtea> kargatuDatuak() {
        
        List<InflazioUrtea> zerrenda = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        getClass().getResourceAsStream("/opendata/datuak/inflazioa.csv"),
                        StandardCharsets.UTF_8))) {

            String lerroa = reader.readLine(); // saltatu goiburua
            while ((lerroa = reader.readLine()) != null) {
                String[] zatitu = lerroa.split(";");
                int urtea = Integer.parseInt(zatitu[0]);
                double indizea = Double.parseDouble(zatitu[1]);
                zerrenda.add(new InflazioUrtea(urtea, indizea));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return zerrenda;
    }

    public List<Integer> lortuUrteGuztiak() {
        return urteroInflazioa.stream()
                .map(InflazioUrtea::getUrtea)
                .collect(Collectors.toList());
    }

    public double kalkulatuBalioEgokitua(double jatorrizkoBalioa, int hasieraUrtea) {
        double indizeHasiera = lortuIndizea(hasieraUrtea);
        double indizeGaur = lortuAzkenIndizea();
        return jatorrizkoBalioa * (indizeGaur / indizeHasiera);
    }

    private double lortuIndizea(int urtea) {
        return urteroInflazioa.stream()
                .filter(i -> i.getUrtea() == urtea)
                .map(InflazioUrtea::getIndizea)
                .findFirst()
                .orElse(100.0); // fallback
    }

    private double lortuAzkenIndizea() {
        return urteroInflazioa.get(urteroInflazioa.size() - 1).getIndizea();
    }

    public List<InflazioUrtea> lortuInflazioSeriea(int hasieraUrtea) {
        double jatorrizkoIndizea = lortuIndizea(hasieraUrtea);
        return urteroInflazioa.stream()
                .filter(i -> i.getUrtea() >= hasieraUrtea)
                .map(i -> new InflazioUrtea(
                        i.getUrtea(),
                        i.getIndizea() / jatorrizkoIndizea
                ))
                .collect(Collectors.toList());
    }
}
