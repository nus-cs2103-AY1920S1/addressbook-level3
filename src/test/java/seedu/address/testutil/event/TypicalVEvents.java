package seedu.address.testutil.event;

import static seedu.address.commons.util.EventUtil.DAILY_RECUR_RULE_STRING;
import static seedu.address.commons.util.EventUtil.NONE_RECUR_RULE_STRING;
import static seedu.address.commons.util.EventUtil.WEEKLY_RECUR_RULE_STRING;

import java.time.LocalDateTime;

import jfxtras.icalendarfx.components.VEvent;

/**
 * A utility class containing  {@code VEvent} objects to be used in tests. Note that these VEvents correspond to those
 * in typicalEvents. I.e. VEVENT1 is the VEvent form of EVENT1 in typicalEvents.
 */
public class TypicalVEvents {

    public static final VEvent VEVENT1 = new VEvent().withSummary("First Event")
            .withDateTimeStart(LocalDateTime.parse("2019-10-10T03:00"))
            .withDateTimeEnd(LocalDateTime.parse("2019-10-10T04:00"))
            .withRecurrenceRule(NONE_RECUR_RULE_STRING)
            .withCategories("group01")
            .withUniqueIdentifier("typicalevent1test");

    public static final VEvent VEVENT2 = new VEvent().withSummary("Second Event")
            .withDateTimeStart(LocalDateTime.parse("2019-10-11T03:00"))
            .withDateTimeEnd(LocalDateTime.parse("2019-10-11T04:00"))
            .withRecurrenceRule(DAILY_RECUR_RULE_STRING)
            .withCategories("group02")
            .withUniqueIdentifier("typicalevent2test");

    public static final VEvent VEVENT3 = new VEvent().withSummary("Third Event")
            .withDateTimeStart(LocalDateTime.parse("2019-10-12T03:00"))
            .withDateTimeEnd(LocalDateTime.parse("2019-10-12T04:00"))
            .withRecurrenceRule(WEEKLY_RECUR_RULE_STRING)
            .withCategories("group03")
            .withUniqueIdentifier("typicalevent3test");

    public static final VEvent VEVENT4 = new VEvent().withSummary("Fourth Event")
            .withDateTimeStart(LocalDateTime.parse("2019-10-13T03:00"))
            .withDateTimeEnd(LocalDateTime.parse("2019-10-13T04:00"))
            .withRecurrenceRule(NONE_RECUR_RULE_STRING)
            .withCategories("group04")
            .withUniqueIdentifier("typicalevent4test");

    public static final VEvent NOT_IN_TYPICAL_VEVENT = new VEvent().withSummary("Not Typical Event")
            .withDateTimeStart(LocalDateTime.parse("2019-10-14T03:00"))
            .withDateTimeEnd(LocalDateTime.parse("2019-10-14T04:00"))
            .withRecurrenceRule(NONE_RECUR_RULE_STRING)
            .withCategories("group05")
            .withUniqueIdentifier("typicalevent5test");
}
