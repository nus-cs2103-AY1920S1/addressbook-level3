package seedu.deliverymans.model.deliveryman.deliverymanstatistics;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * This is a singleton Analyzer object.
 * It analyzes the delivery record of deliverymen and computes relevant data.
 */
public class Analyzer {
    private static Analyzer theAnalyzer = null;

    public Analyzer() {}

    /**
     * Prevents instantiation of Analyzer from outside. Only one copy is allowed.
     */
    public static Analyzer getInstance() {
        if (theAnalyzer == null) {
            theAnalyzer = new Analyzer();
        }
        return theAnalyzer;
    }

    /**
     * Analyzes the time in database, the delivery rate, order completion rate of a deliveryman record.
     */
    public void analyze(DeliveryRecord record) {}

    /**
     * Computes the time difference from a given start time to the current time, in seconds.
     */
    public long computeFromStartTimeToCurrentTime(LocalDateTime start) {
        LocalDateTime current = LocalDateTime.now();
        return computeDifferenceInTimesInSeconds(start, current);
    }

    /**
     * Computes the time difference between two LocalDateTime objects, in seconds.
     */
    public long computeDifferenceInTimesInSeconds(LocalDateTime start, LocalDateTime end) {
        long diff = start.until(end, ChronoUnit.SECONDS);
        return diff;
    }
}
