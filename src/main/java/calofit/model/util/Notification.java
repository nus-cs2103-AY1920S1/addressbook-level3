package calofit.model.util;

import java.time.LocalDateTime;

public class Notification {
    private Timestamp currDateTime;

    /*Notification(LocalDateTime currDateTime) {
        this.currDateTime = currDateTime;
    }*/

    public boolean checkBreakfast(Timestamp currDateTime) {
        System.out.println(currDateTime.getDateTime());
        return false;
    }

    public boolean checkLunch() {
        return false;
    }

    public boolean checkDinner() {
        return false;
    }

}
