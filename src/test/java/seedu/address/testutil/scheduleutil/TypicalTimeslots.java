package seedu.address.testutil.scheduleutil;

import java.time.LocalDateTime;

import seedu.address.model.person.schedule.Timeslot;
import seedu.address.model.person.schedule.Venue;

/**
 * Typical Timeslots.
 */
public class TypicalTimeslots {

    public static final String STARTTIMETEXT1 = "03122007:1030-";
    public static final String ENDTIMETEXT1 = "03122007:1130-";

    public static final LocalDateTime STARTTIME1 = LocalDateTime.parse("2007-12-03T10:30:00");
    public static final LocalDateTime ENDTIME1 = LocalDateTime.parse("2007-12-03T11:30:00");
    public static final Venue VENUE1 = new Venue("venue1");

    public static final Timeslot TIMESLOT1 = new Timeslot(
            STARTTIME1, ENDTIME1, VENUE1
    );

    public static final LocalDateTime STARTTIME2 = LocalDateTime.parse("2007-12-03T12:30:00");
    public static final LocalDateTime ENDTIME2 = LocalDateTime.parse("2007-12-03T13:30:00");
    public static final Venue VENUE2 = new Venue("venue2");

    public static final Timeslot TIMESLOT2 = new Timeslot(
            STARTTIME2, ENDTIME2, VENUE2
    );

    public static final LocalDateTime STARTTIME3 = LocalDateTime.parse("2007-12-03T14:30:00");
    public static final LocalDateTime ENDTIME3 = LocalDateTime.parse("2007-12-03T15:30:00");
    public static final Venue VENUE3 = new Venue("venue3");

    public static final Timeslot TIMESLOT3 = new Timeslot(
            STARTTIME3, ENDTIME3, VENUE3
    );
}
