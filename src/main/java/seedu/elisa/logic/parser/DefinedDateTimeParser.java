package seedu.elisa.logic.parser;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Locale;

import seedu.elisa.logic.parser.exceptions.InvalidDateException;
import seedu.elisa.logic.parser.exceptions.MidnightParseException;
import seedu.elisa.logic.parser.exceptions.ParseException;

/**
 * Parses string date time using a formatter dd/MM/yyyy HHmm
 */
public class DefinedDateTimeParser implements DateTimeParser {
    private static Locale sgLocale = new Locale("en", "SG");
    /**
     * Processes the string using the given format and returns a LocalDateTime
     * @param stringDateTime of the format "dd/MM/yyyy HHmm"
     * @return LocalDateTime representation of the string
     */
    public LocalDateTime parseDateTime(String stringDateTime) throws ParseException {
        try {
            String[] splitTime = stringDateTime.split(" ");

            // If time given is 2400, prompts the user for 0000 of the next day instead
            if (splitTime[1].equals("2400")) {
                throw new MidnightParseException("0000");
            }

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");
            LocalDate processedDate = LocalDate.parse(splitTime[0], dateFormatter);

            if (!isValid(splitTime[0])) {
                throw new InvalidDateException();
            }

            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");
            LocalTime processedTime = LocalTime.parse(splitTime[1], timeFormatter);

            LocalDateTime processedDateTime = LocalDateTime.of(processedDate, processedTime);
            return processedDateTime;
        } catch (MidnightParseException me) {
            throw me;
        } catch (InvalidDateException de) {
            throw de;
        } catch (Exception e) {
            throw new ParseException("Date Time format given is incorrect."
                    + " Should be \"25/09/2019 2300\"");
        }
    }

    /**
     * Checks that the given date string is a valid date.
     * @param dateStr of the input date
     * @return true if the date exists, false otherwise
     */
    private boolean isValid(String dateStr) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("d/M/uuuu", sgLocale)
                .withResolverStyle(ResolverStyle.STRICT);
        try {
            df.parse(dateStr);
        } catch (DateTimeException e) {
            return false;
        }
        return true;
    }
}
