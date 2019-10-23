package seedu.address.model.flashcard;

import java.awt.*;

public enum ScheduleIncrement {
    FIRST,
    SECOND,
    THIRD,
    FOURTH,
    FIFTH,
    SIXTH,
    FINAL;

    private int numberOfDays;

    static {
        FIRST.numberOfDays = 1;
        SECOND.numberOfDays = 1;
        THIRD.numberOfDays = 2;
        FOURTH.numberOfDays = 3;
        FIFTH.numberOfDays = 5;
        SIXTH.numberOfDays = 5;
        FINAL.numberOfDays = 7;
    }

    public int getNumberOfDays() {
        return numberOfDays;
    }
}
