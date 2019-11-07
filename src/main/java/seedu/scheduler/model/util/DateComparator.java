package seedu.scheduler.model.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

/**
 * Class to compare between 2 dates in string.
 */
public class DateComparator implements Comparator<String> {

    /**
     * Compares 2 dates in string.
     * @param dateStringA
     * @param dateStringB
     * @return Positive int signifies dateStringA > dateString B. Negative int signifies dateStringA < dateString B.
     * Zero is returned when both strings are equal or when there is an exception.
     */
    public int compare(String dateStringA, String dateStringB) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date dateA = formatter.parse(dateStringA);
            Date dateB = formatter.parse(dateStringB);
            return dateA.compareTo(dateB);
        } catch (ParseException e) {
            return 0;
        }

    }
}
