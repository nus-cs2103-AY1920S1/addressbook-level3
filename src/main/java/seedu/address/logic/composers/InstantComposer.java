package seedu.address.logic.composers;

import static seedu.address.MainApp.TIME_ZONE;
import static seedu.address.model.DateTime.ICS_DATE_TIME_PATTERN;
import static seedu.address.model.DateTime.USER_DATE_TIME_PATTERN;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Composer that can compose an {@link Instant} into a String.
 */
public class InstantComposer {

    private static final DateTimeFormatter USER_COMPOSER = DateTimeFormatter.ofPattern(USER_DATE_TIME_PATTERN)
        .withZone(TIME_ZONE);

    private static final DateTimeFormatter ICS_COMPOSER = DateTimeFormatter.ofPattern(ICS_DATE_TIME_PATTERN)
        .withZone(ZoneOffset.UTC);

    public String toUserString(Instant dateTime) {
        return USER_COMPOSER.format(dateTime);
    }

    public String toIcsString(Instant dateTime) {
        return ICS_COMPOSER.format(dateTime);
    }
}
