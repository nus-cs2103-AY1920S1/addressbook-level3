package seedu.address.logic.parser;

import seedu.address.logic.parser.exceptions.ParseException;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_FAST_REMINDER_FORMAT;

public class FastReminderDateTimeParser implements DateTimeParser {

    private static final String DAY_INDICATOR = "DAY";
    private static final String HOUR_INDICATOR = "HOUR";
    private static final String MIN_INDICATOR = "MIN";

    private static final Pattern BASIC_INPUT_FORMAT =
            Pattern.compile("(?<quantity>[1-9]\\d*)(\\.)(?<unit>MIN|HOUR|DAY)(\\.LATER)$");

    public LocalDateTime parseDateTime(String stringDateTime) throws ParseException {
        //stringDateTime should be of format "10.min.later" or "3.hour.later" or "2.day.later"
        String processedString = stringDateTime.toUpperCase();

        final Matcher matcher = BASIC_INPUT_FORMAT.matcher(processedString);
        if (!matcher.matches()) {
            throw new ParseException(MESSAGE_INVALID_FAST_REMINDER_FORMAT);
        }

        final String quantity = matcher.group("quantity");
        final String unit = matcher.group("unit");
        final long longQuantity = Long.valueOf(quantity).longValue();

        LocalDateTime current = LocalDateTime.now();
        LocalDateTime processedDateTime = LocalDateTime.now(); // just to initialize

        try {
            switch (unit) {
                case DAY_INDICATOR:
                    processedDateTime = current.plusDays(longQuantity);
                    break;
                case HOUR_INDICATOR:
                    processedDateTime = current.plusHours(longQuantity);
                    break;
                case MIN_INDICATOR:
                    processedDateTime = current.plusMinutes(longQuantity);
                    break;
            }
        } catch (DateTimeException e) {
            throw new ParseException(e.getMessage());
        }

        return processedDateTime;
    }
}