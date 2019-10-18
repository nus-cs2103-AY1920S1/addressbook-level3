package seedu.address.itinerary.model.Event;

/**
 * Date of the event in the itinerary.
 */
public class Date {
    public static final String MESSAGE_CONSTRAINTS =
            "Date should only contain numerals and be written in the DDMMYYYY format.\n"
            + "Valid formats: 13071997, 11092001, 10101010 ✓\n"
            + "Invalid formats: 13-07-1997, 11/09/2001, 12312011 ✗";

    /*
     * The first character of the description must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "^(3[01]|[12][0-9]|0[1-9])(1[0-2]|0[1-9])[0-9]{4}$";
    public final String date;

    public Date(String date) {
        this.date = date;
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return date;
    }
}