package seedu.address.testutil.scheduleutil;

import java.time.LocalDateTime;

import seedu.address.model.person.schedule.Timeslot;
import seedu.address.model.person.schedule.Venue;

/**
 * Typical Timeslots.
 */
public class TypicalTimeslots {

    public static final LocalDateTime STARTTIME1 = LocalDateTime.parse("2007-12-03T10:15:30");
    public static final LocalDateTime ENDTIME1 = LocalDateTime.parse("2007-12-03T10:16:30");
    public static final Venue VENUE1 = new Venue("venue1");

    public static final Timeslot TIMESLOT1 = new Timeslot(
            STARTTIME1, ENDTIME1, VENUE1
    );

    public static final LocalDateTime STARTTIME2 = LocalDateTime.parse("2007-12-03T10:17:30");
    public static final LocalDateTime ENDTIME2 = LocalDateTime.parse("2007-12-03T10:18:30");
    public static final Venue VENUE2 = new Venue("venue2");

    public static final Timeslot TIMESLOT2 = new Timeslot(
            STARTTIME2, ENDTIME2, VENUE2
    );

    public static final LocalDateTime STARTTIME3 = LocalDateTime.parse("2007-12-03T10:19:30");
    public static final LocalDateTime ENDTIME3 = LocalDateTime.parse("2007-12-03T10:20:30");
    public static final Venue VENUE3 = new Venue("venue3");

    public static final Timeslot TIMESLOT3 = new Timeslot(
            STARTTIME3, ENDTIME3, VENUE3
    );
}
