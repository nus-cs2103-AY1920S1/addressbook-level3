package seedu.address.diaryfeature.logic.parser.exceptions.DiaryEntryExceptions;

/**
 * DateParse Exception arises if there are any errors with the date format
 */
public class DateParseException extends DiaryEntryParseException {
    private final String ERROR_MESSAGE =
            "In particular, something is wrong with your date and time. \n" +
                    "Note the following ranges: \n" +
                    "Day(D): 00 - 31 |  Month(M): 00 - 12 |  Year(Y): 1980 - 2030\n" +
                    "Hour(H): 00 - 23 | Minutes(M): 00 - 59\n" +
                    "Enter it like this:\n" +
                    "d/DD/MM/YYYY HHMM | EG: d/31/12/2019 2359 \n" +
                    "We take into account leap years and months with 30/31 days.";

    /**
     * @return String representation of the Error message
     */
    @Override
    public String toString() {
        return getUsage() + ERROR_MESSAGE;
    }


}
