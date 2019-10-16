package seedu.address.model.performance;

/**
 * Under each event, each member will have a PerformanceEntry that contains their performance timing and the date
 * that they took
 */
public class PerformanceEntry {
    private String date;
    private double time;

    /**
     * Creates a PerformanceEntry for a member under an event.
     * @param date Date this entry was taken.
     * @param time Timing of this entry.
     */
    public PerformanceEntry(String date, double time) {
        this.date = date;
        this.time = time;
    }

    /**
     * Retrieves the timing of this PerformanceEntry.
     * @return Timing of this PerformanceEntry.
     */
    public double getTime() {
        return time;
    }
}
