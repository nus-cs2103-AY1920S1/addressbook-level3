package seedu.address.model.performance;

import seedu.address.model.date.AthletickDate;

/**
 * Under each event, each member will have a PerformanceEntry that contains their performance timing and the date
 * that they took
 */
public class Record {
    private AthletickDate date;
    private String timing;

    /**
     * Creates a Record for a member under an event.
     * @param date this entry was taken.
     * @param timing of this entry.
     */
    public Record(AthletickDate date, String timing) {
        this.date = date;
        this.timing = timing;
    }

    /**
     * Retrieves the timing of this PerformanceEntry.
     * @return Timing of this PerformanceEntry.
     */
    public String getTiming() {
        return timing;
    }

    public AthletickDate getDate() {
        return date;
    }

}
