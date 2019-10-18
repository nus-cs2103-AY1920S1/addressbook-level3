package seedu.address.commons.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.TimeZone;

import seedu.address.ics.IcsException;
import seedu.address.model.events.DateTime;

/**
 * Helper functions for anything Ics related.
 */
public class IcsUtil {

    /**
     * Gets a new SimpleDateFormat with the pattern matching the default ICS file specification format.
     * @return a configured SimpleDateFormat format.
     */
    public static SimpleDateFormat getIcsSimpleDateFormat() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sdf;
    }

    /**
     * Generates a unique UID that complies with section RFC5545 of the iCalendar specification.
     * This is achieved by using the Instant of the function call.
     * @return A UID String.
     */
    public static String generateUid() {
        Instant currentInstant = Instant.now();
        return currentInstant + "@Rori";
    }

    /**
     * Converts the timestamp from the format given in the ICS file to a DateTime object.
     * @param icsTimeStamp A timestamp in the default ICS file specification format.
     * @return A DateTime object representing the timestamp.
     * @throws IcsException Thrown when the timestamp provided is invalid.
     */
    public static DateTime parseTimeStamp(String icsTimeStamp) throws IcsException {
        try {
            SimpleDateFormat sdf = getIcsSimpleDateFormat();
            return new DateTime(sdf.parse(icsTimeStamp).toInstant());
        } catch (ParseException e) {
            throw new IcsException("The timestamp provided is invalid!");
        }
    }

    /**
     * Converts the DateTime object to a time stamp in the default ICS file specification format.
     * @param dateTime A DateTime object.
     * @return The timestamp in the default ICS file specification format representing the DateTime object.
     */
    public static String toIcsTimeStamp(DateTime dateTime) {
        Instant instant = dateTime.toInstant();
        SimpleDateFormat simpleDateFormat = getIcsSimpleDateFormat();
        return simpleDateFormat.format(instant);
    }

}
