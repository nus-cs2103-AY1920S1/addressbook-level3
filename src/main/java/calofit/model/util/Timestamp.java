package calofit.model.util;

import java.time.LocalDateTime;

/**
 * Represents a timestamp of when a meal was eaten.
 */
public class Timestamp implements Comparable<Timestamp> {
    private LocalDateTime dateTime;
    private int tiebreaker;

    /**
     * Constructs a {@link Timestamp} around a {@link LocalDateTime}.
     * @param dateTime Date to wrap
     */
    public Timestamp(LocalDateTime dateTime) {
        this.dateTime = dateTime;
        this.tiebreaker = 0;
    }

    @Override
    public int compareTo(Timestamp timestamp) {
        int dtCompare = dateTime.compareTo(timestamp.dateTime);
        if (dtCompare != 0) {
            return dtCompare;
        }
        return Integer.compare(tiebreaker, timestamp.tiebreaker);
    }
}
