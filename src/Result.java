
public class Result implements Comparable {
    private String algorytm;
    private double czas;

    public Result(String algorytm, double czas)
    {
        this.algorytm = algorytm;
        this.czas = czas;
    }

    public String getAlgorytm() {
        return algorytm;
    }

    public void setAlgorytm(String algorytm) {
        this.algorytm = algorytm;
    }

    public double getCzas() {
        return czas;
    }

    public void setCzas(double czas) {
        this.czas = czas;
    }

    public String toString ()
    {
        return algorytm + " : " + czas;
    }

    @Override
    public int compareTo(Object o) {
        return this.getCzas() >= ((Result) o).getCzas() ? 1 : -1;
    }
}
