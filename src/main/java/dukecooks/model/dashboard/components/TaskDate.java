package dukecooks.model.dashboard.components;

import static dukecooks.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

/**
 * Represents a TaskDate in DukeCooks.
 * Guarantees: details are present and not null, field values validated and immutable.
 */
public class TaskDate {

    public static final String MESSAGE_CONSTRAINTS =
            "Date entered is invalid, should only contain numbers, "
                    + "expressed in day/month/year";
    public final String day;
    public final String month;
    public final String year;
    public final String taskDate;

    /**
     * Constructs a {@TaskDate}
     * @param date A valid date
     */
    public TaskDate(String date) {
        requireNonNull(date);
        checkArgument(isValidTaskDate(date), MESSAGE_CONSTRAINTS);
        String[] d = date.split("/");
        day = d[0];
        month = d[1];
        year = d[2];
        taskDate = day + "/" + month + "/" + year;
    }

    /**
     * Returns true if given string is a valid date
     */
    public static boolean isValidTaskDate(String test) {
        String[] t = test.split("/");
        int d = Integer.parseInt(t[0]);
        int m = Integer.parseInt(t[1]);
        int y = Integer.parseInt(t[2]);
        boolean validDay = d > 0 && d < 31;
        boolean validMonth = m > 0 && m < 13;
        boolean validYear = y > 2018;
        if (validDay && validMonth && validYear) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return taskDate;
    }


}
