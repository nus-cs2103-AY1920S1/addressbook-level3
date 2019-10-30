package seedu.address.commons.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import seedu.address.ics.IcsException;
import seedu.address.logic.parser.DateTimeParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.DateTime;

/**
 * Helper functions for anything Ics related.
 */
public class IcsUtil {

    private static final DateTimeParser DATE_TIME_PARSER =
        new DateTimeParser(DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'")
            .withZone(ZoneId.of("GMT")));

    /**
     * Generates a unique UID that complies with section RFC5545 of the iCalendar specification.
     * This is achieved by using the Instant of the function call.
     * @return A UID String.
     */
    public static String generateUid() {
        Instant currentInstant = Instant.now();
        return currentInstant + "@Horo";
    }

    /**
     * Converts the timestamp from the format given in the ICS file to a DateTime object.
     * @param icsTimeStamp A timestamp in the default ICS file specification format.
     * @return A DateTime object representing the timestamp.
     * @throws IcsException Thrown when the timestamp provided is invalid.
     */
    public static DateTime parseTimeStamp(String icsTimeStamp) throws IcsException {
        try {
            return DATE_TIME_PARSER.parse(icsTimeStamp);
        } catch (ParseException e) {
            throw new IcsException("The timestamp provided is invalid!");
        }
    }
}
