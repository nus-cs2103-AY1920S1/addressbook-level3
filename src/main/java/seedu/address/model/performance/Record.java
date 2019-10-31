package seedu.address.model.performance;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.model.date.AthletickDate;

/**
 * Under each event, each member will have a PerformanceEntry that contains their performance timing and the date
 * that they took
 */
public class Record {
    private AthletickDate date;
    private Timing timing;

    /**
     * Creates a Record for a member under an event.
     * @param date this entry was taken.
     * @param timing of this entry.
     */
    public Record(AthletickDate date, Timing timing) {
        requireNonNull(date);
        requireNonNull(timing);
        this.date = date;
        this.timing = timing;
    }

    /**
     * Retrieves the timing of this PerformanceEntry.
     * @return Timing of this PerformanceEntry.
     */
    public Timing getTiming() {
        return timing;
    }

    public AthletickDate getDate() {
        return date;
    }

    public static double getFastestTiming(List<Record> records) {
        double fastest = Double.MAX_VALUE;
        for (Record record : records) {
            double timing = record.getTiming().getValue();
            if (timing < fastest) {
                fastest = timing;
            }
        }
        assert(fastest < Double.MAX_VALUE);
        return fastest;
    }

    public static double getSlowestTiming(List<Record> records) {
        double slowest = Double.MIN_VALUE;
        for (Record record : records) {
            double timing = record.getTiming().getValue();
            if (timing > slowest) {
                slowest = timing;
            }
        }
        assert(slowest > Double.MIN_VALUE);
        return slowest;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Record)) {
            return false;
        }

        Record otherRecord = (Record) other;
        return otherRecord.getTiming().equals(timing) && otherRecord.getDate().equals(date);
    }
}
