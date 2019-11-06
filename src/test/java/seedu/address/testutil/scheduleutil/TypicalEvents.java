package seedu.address.testutil.scheduleutil;

import static seedu.address.testutil.scheduleutil.TypicalTimeslots.TIME_SLOT1;
import static seedu.address.testutil.scheduleutil.TypicalTimeslots.TIME_SLOT2;
import static seedu.address.testutil.scheduleutil.TypicalTimeslots.TIME_SLOT3;

import java.util.ArrayList;

import seedu.address.model.person.schedule.Event;
import seedu.address.model.person.schedule.Timeslot;

/**
 * Typical Events.
 */
public class TypicalEvents {

    public static final String EMPTY_EVENT = "emptyevent";

    public static final String EVENT_NAME1 = "eventname1";
    public static final String EVENT_NAME2 = "eventname2";

    /**
     * Generate a Typical Event.
     *
     * Event 1 -> TimeSlot 1.
     */
    public static Event generateTypicalEvent1() {
        ArrayList<Timeslot> arr = new ArrayList<>();
        arr.add(TIME_SLOT1);
        Event event = new Event(EVENT_NAME1, arr);
        return event;
    }

    /**
     * Generate a Typical Event.
     *
     * Event 2 -> TimeSlot 2 and TimeSlot 3.
     */
    public static Event generateTypicalEvent2() {
        ArrayList<Timeslot> arr = new ArrayList<>();
        arr.add(TIME_SLOT2);
        arr.add(TIME_SLOT3);
        Event event = new Event(EVENT_NAME2, arr);
        return event;
    }

    /**
     * Generates and empty event.
     *
     * Event 3 -> No TimeSlots.
     */
    public static Event generateEmptyEvent() {
        Event event = new Event((EMPTY_EVENT));
        return event;
    }

}
