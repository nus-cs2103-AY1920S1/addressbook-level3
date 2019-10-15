package seedu.address.model.performance;

public class PerformanceEntry {
    private String date;
    private double time;

    public PerformanceEntry(String date, double time) {
        this.date = date;
        this.time = time;
    }

    public double getTime() {
        return time;
    }
}
