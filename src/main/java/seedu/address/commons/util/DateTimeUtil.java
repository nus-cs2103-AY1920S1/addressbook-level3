package seedu.address.commons.util;

import java.time.LocalDateTime;

public class DateTimeUtil {

    /**
     * A util method to check if the task is due soon.
     * @param weeks the reminder period specified by the user
     * @param dateTime deadline of the task
     * @return true if task is due within user's reminder period
     */
    public static boolean checkIfDueSoon(long weeks, LocalDateTime dateTime) {
        LocalDateTime dueDate = dateTime.minusWeeks(weeks);
        return LocalDateTime.now().isAfter(dueDate);
    }

}
