package seedu.address.testutil.scheduleutil;

import static seedu.address.testutil.scheduleutil.TypicalTimeslots.TIMESLOT1;
import static seedu.address.testutil.scheduleutil.TypicalTimeslots.TIMESLOT2;
import static seedu.address.testutil.scheduleutil.TypicalTimeslots.TIMESLOT3;

import java.util.ArrayList;

import seedu.address.model.person.schedule.Event;
import seedu.address.model.person.schedule.Timeslot;

/**
 * Typical Events.
 */
public class TypicalEvents {

    public static final String EMPTYEVENT = "emptyevent";
    public static final String EVENTNAME1 = "eventname1";
    public static final String EVENTNAME2 = "eventname2";

    /**
     * Generate a Typical Event.
     *
     * @return Event 1
     */
    public static Event generateTypicalEvent1() {
        ArrayList<Timeslot> arr = new ArrayList<>();
        arr.add(TIMESLOT1);
        Event event = new Event(EVENTNAME1, arr);
        return event;
    }

    /**
     * Generate a Typical Event.
     *
     * @return Event 2
     */
    public static Event generateTypicalEvent2() {
        ArrayList<Timeslot> arr = new ArrayList<>();
        arr.add(TIMESLOT2);
        arr.add(TIMESLOT3);
        Event event = new Event(EVENTNAME2, arr);
        return event;
    }

    /**
     * Generates and empty event.
     *
     * @return Event
     */
    public static Event generateEmptyEvent() {
        Event event = new Event((EMPTYEVENT));
        return event;
    }

}
