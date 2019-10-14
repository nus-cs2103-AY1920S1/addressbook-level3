package seedu.address.model.budget;

public class Percentage {
    private final int proportion;

    public Percentage(int proportion) {
        this.proportion = proportion;
    }

    public int getProportion() {
        return proportion;
    }

    public static Percentage calculate(double num, double den) {
        return new Percentage ((int) (num / den * 100));
    }

    public boolean reach(Percentage threshold) {
        return getProportion() >= threshold.getProportion();
    }

    @Override
    public String toString() {
        return proportion + "%";
    }
}
