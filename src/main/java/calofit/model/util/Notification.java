package calofit.model.util;

import java.time.LocalDateTime;

/**
 * Notification class
 */
public class Notification {

    private LocalDateTime currDateTime;

    public Notification () {
        currDateTime = LocalDateTime.now();
    }

    public Notification (LocalDateTime currDateTime) {
        this.currDateTime = currDateTime;
    }
    /**
     * Check for breakfast.
     * @return boolean value
     */
    public boolean eatenBreakfast() {
        if (currDateTime.getHour() >= 10) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Check for lunch.
     * @param dishDateTime get the timing of dish
     * @return boolean value
     */
    public boolean eatenLunch(Timestamp dishDateTime) {
        if (currDateTime.getDayOfYear() != dishDateTime.getDateTime().getDayOfYear()) {
            return true;
        }

        if (dishDateTime.getDateTime().getHour() >= 11) {
            return true;
        } else {
            return currDateTime.getHour() <= 13 || currDateTime.getHour() >= 20;
        }
    }

    /**
     * Check for dinner.
     * @param dishDateTime get the timing of dish
     * @return boolean value
     */
    public boolean eatenDinner(Timestamp dishDateTime) {
        if (currDateTime.getDayOfYear() != dishDateTime.getDateTime().getDayOfYear()) {
            return true;
        }

        if (dishDateTime.getDateTime().getHour() >= 16) {
            return true;
        } else {
            return (currDateTime.getHour() <= 19);
        }
    }

}
