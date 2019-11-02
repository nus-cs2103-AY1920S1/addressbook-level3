package dukecooks.model.dashboard.components;

import static dukecooks.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Represents a TaskDate in DukeCooks.
 * Guarantees: details are present and not null, field values validated and immutable.
 */
public class TaskDate {

    public static final String MESSAGE_CONSTRAINTS =
            "Date entered is invalid, should only contain numbers, "
                    + "expressed in day/month/year."
                    + "\n"
                    + "Or subsequently, the entered month does not have the that particular day.";
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

    public String getDate() {
        return taskDate;
    }
    /**
     * Returns true if given string is a valid date
     */
    public static boolean isValidTaskDate(String test) {
        //TODO: give reference to https://www.mkyong.com/java/how-to-check-if-date-is-valid-in-java/
        if (test == null) {
            return false;
        }
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        format.setLenient(false);
        try {
            format.parse(test);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return taskDate;
    }


}
