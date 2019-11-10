package seedu.address.itinerary.model.event;

/**
 * Date of the event in the itinerary.
 */
public class Date {
    public static final String MESSAGE_CONSTRAINTS =
            "Date should only contain numerals and be written in the DDMMYYYY format, from year 1000 to 3999.\n"
            + "Valid formats: 13071997, 11092001, 10101010 ✓\n"
            + "Invalid formats: 13-07-1997, 11/09/2001, 12312011 ✗";

    /*
     * The first character of the description must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "^(3[01]|[12][0-9]|0[1-9])(1[0-2]|0[1-9])([1-3])[0-9]{3}$";
    public final String date;
    public final String oldDate;

    public Date(String date) {
        assert isValidDate(date);
        this.oldDate = date;
        this.date = formatDate(date);
    }

    public String getOriginalDate() {
        return oldDate;
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {

        if (test.matches(VALIDATION_REGEX)) {
            String day = test.substring(0, 2);
            String month = test.substring(2, 4);
            String year = test.substring(4);

            if (day.equals("29") && month.equals("02")) {
                int numYear = Integer.parseInt(year);

                if (numYear % 400 == 0) {
                    return true;
                } else if (numYear % 100 == 0) {
                    return false;
                } else if (numYear % 4 == 0) {
                    return true;
                } else {
                    return false;
                }

            } else if (month.equals("04") | month.equals("06") | month.equals("09") | month.equals("11")) {

                return Integer.parseInt(day) <= 30;

            } else {

                if (month.equals("02")) {
                    int numDay = Integer.parseInt(day);
                    if (numDay > 28) {
                        return false;
                    }
                }

                return true;

            }

        }

        return false;

    }

    /**
     * Format the date in the event to dd/MM/yyyy.
     * @param date attribute in the given event.
     * @return formatted date based on dd/MM/yyyy.
     */
    private String formatDate(String date) {
        String day = date.substring(0, 2);
        String month = date.substring(2, 4);
        String year = date.substring(4);
        return day + "/" + month + "/" + year;
    }

    @Override
    public String toString() {
        return date;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Date // instanceof handles nulls
                && date.equals(((Date) other).date)); // state check
    }
}
