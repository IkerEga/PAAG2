package opendata.model;

public class InflazioUrtea {
    private int urtea;
    private double indizea;

    public InflazioUrtea(int urtea, double indizea) {
        this.urtea = urtea;
        this.indizea = indizea;
    }

    public int getUrtea() {
        return urtea;
    }

    public double getIndizea() {
        return indizea;
    }
}
