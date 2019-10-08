package calofit.model.util;

import java.time.LocalDateTime;

public class Timestamp implements Comparable<Timestamp> {
    private LocalDateTime dateTime;
    private int tiebreaker;

    public Timestamp(LocalDateTime dateTime) {
       this.dateTime = dateTime;
       this.tiebreaker = 0;
    }

    @Override
    public int compareTo(Timestamp timestamp) {
        int dtCompare = dateTime.compareTo(timestamp.dateTime);
        if(dtCompare != 0) {
            return dtCompare;
        }
        return Integer.compare(tiebreaker, timestamp.tiebreaker);
    }
}
