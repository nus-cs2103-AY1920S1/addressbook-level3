package calofit.model.util;

import java.time.LocalDateTime;

public class Notification {

    LocalDateTime currDateTime;

    public Notification () {
        currDateTime = LocalDateTime.now();
    }

    public boolean eatenBreakfast() {
        if (currDateTime.getHour() > 10) {
            return true;
        } else {
            return false;
        }
    }

    public boolean eatenLunch(Timestamp dishDateTime) {
        if(currDateTime.getDayOfYear() != dishDateTime.getDateTime().getDayOfYear()) {
            return false;
        }

        if (currDateTime.getHour() < 14 || currDateTime.getHour() > 19) {
            return true;
        }

        if (dishDateTime.getDateTime().getHour() > 11) {
            return true;
        } else {
            return false;
        }
    }

    public boolean eatenDinner(Timestamp dishDateTime) {
        if(currDateTime.getDayOfYear() != dishDateTime.getDateTime().getDayOfYear()) {
            return false;
        }

        if (currDateTime.getHour() <= 20) {
            return true;
        }

        if (dishDateTime.getDateTime().getHour() > 16) {
            return true;
        } else {
            return false;
        }
    }

}
