package opendata.service;

import opendata.model.IndizeUrtea;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class IndizeZerbitzua {

    private final List<IndizeUrtea> datuak;

    public IndizeZerbitzua(String resourcePath) {
        this.datuak = kargatu(resourcePath);
    }

    private List<IndizeUrtea> kargatu(String resourcePath) {
        List<IndizeUrtea> zerrenda = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        Objects.requireNonNull(getClass().getResourceAsStream(resourcePath),
                                "Ez da aurkitu resource-a: " + resourcePath),
                        StandardCharsets.UTF_8))) {

            String lerroa = reader.readLine(); // goiburua saltatu
            while ((lerroa = reader.readLine()) != null) {
                String[] zatitu = lerroa.split(";");
                int urtea = Integer.parseInt(zatitu[0].trim());
                double balioa = Double.parseDouble(zatitu[1].trim().replace(",", "."));
                zerrenda.add(new IndizeUrtea(urtea, balioa));
            }

        } catch (Exception e) {
            throw new RuntimeException("Errorea indizearen CSV-a kargatzean (" + resourcePath + "): " + e.getMessage(),
                    e);
        }

        zerrenda.sort(Comparator.comparingInt(IndizeUrtea::getUrtea));
        return zerrenda;
    }

    public List<IndizeUrtea> lortuDatuak() {
        return datuak;
    }

    public int lortuHasierakoUrtea() {
        return datuak.get(0).getUrtea();
    }

    public int lortuAzkenUrtea() {
        return datuak.get(datuak.size() - 1).getUrtea();
    }

    public double lortuUrtekoEtekina(int urtea) {
        // etekina: balioa(urtea)/balioa(urtea-1) - 1
        Double aurrekoa = null;
        Double oraingoa = null;

        for (IndizeUrtea d : datuak) {
            if (d.getUrtea() == urtea - 1)
                aurrekoa = d.getBalioa();
            if (d.getUrtea() == urtea)
                oraingoa = d.getBalioa();
        }

        if (aurrekoa == null || oraingoa == null || aurrekoa == 0)
            return 0.0;
        return (oraingoa / aurrekoa) - 1.0;
    }

    public int lortuAzkenUrtea() {
        return urteroInflazioa.get(urteroInflazioa.size() - 1).getUrtea();
    }

    public double lortuIndizeaUrtean(int urtea) {
        return lortuIndizea(urtea);
    }

}
