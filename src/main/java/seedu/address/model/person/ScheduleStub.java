package seedu.address.model.person;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import seedu.address.model.display.detailwindow.PersonTimeslot;
import seedu.address.model.person.exceptions.DuplicateEventException;
import seedu.address.model.person.exceptions.EventClashException;
import seedu.address.model.person.schedule.Event;
import seedu.address.model.person.schedule.Schedule;
import seedu.address.model.person.schedule.Timeslot;
import seedu.address.model.person.schedule.Venue;

/**
 * A class for testing Schedule View.
 */
public class ScheduleStub {

    private Person p = new Person(new PersonDescriptor());

    private Schedule schedule = new Schedule(new PersonId(12345));
    private Venue venue = new Venue("Central Library");

    private LocalDateTime startTime1 = LocalDateTime.of(2019, 10, 28, 9, 0);
    private LocalDateTime endTime1 = LocalDateTime.of(2019, 10, 28, 11, 0);

    private LocalDateTime startTime2 = LocalDateTime.of(2019, 10, 28, 12, 0);
    private LocalDateTime endTime2 = LocalDateTime.of(2019, 10, 28, 14, 0);

    private LocalDateTime startTime3 = LocalDateTime.of(2019, 10, 28, 16, 0);
    private LocalDateTime endTime3 = LocalDateTime.of(2019, 10, 28, 18, 0);

    private LocalDateTime startTime4 = LocalDateTime.of(2019, 10, 28, 9, 0);
    private LocalDateTime endTime4 = LocalDateTime.of(2019, 10, 28, 10, 0);

    private LocalDateTime startTime5 = LocalDateTime.of(2019, 10, 28, 13, 0);
    private LocalDateTime endTime5 = LocalDateTime.of(2019, 10, 28, 16, 0);

    private Timeslot timeslot1 = new Timeslot(startTime1, endTime1, venue);
    private Timeslot timeslot2 = new Timeslot(startTime2, endTime2, venue);
    private Timeslot timeslot3 = new Timeslot(startTime3, endTime3, venue);
    private Timeslot timeslot4 = new Timeslot(startTime4, endTime4, venue);
    private Timeslot timeslot5 = new Timeslot(startTime5, endTime5, venue);

    public ScheduleStub() {
    }

    public Schedule getSchedule() {
        p.setName(new Name("ME"));
        Event event = new Event("Test", new ArrayList<>(List.of(timeslot1, timeslot2)));
        Schedule result = new Schedule(new PersonId(-1));
        try {
            schedule.addEvent(event);
        } catch (EventClashException | DuplicateEventException e) {
            return null;
        }
        return schedule;
    }

    /**
     * Stub to create a list of day timeslots to show the events that is happening on this day.
     * @return List of DayTimeslot.
     */
    public List<PersonTimeslot> eventStubs() {
        PersonTimeslot event1 = new PersonTimeslot("Test run1!",
                startTime1.toLocalTime(), endTime1.toLocalTime(), venue);
        PersonTimeslot event2 = new PersonTimeslot("Test run2!",
                startTime2.toLocalTime(), endTime2.toLocalTime(), venue);
        PersonTimeslot event3 = new PersonTimeslot("Test run3!",
                startTime3.toLocalTime(), endTime3.toLocalTime(), venue);
        PersonTimeslot event4 = new PersonTimeslot("Test run4!",
                startTime4.toLocalTime(), endTime4.toLocalTime(), venue);
        PersonTimeslot event5 = new PersonTimeslot("Test run5!",
                startTime5.toLocalTime(), endTime5.toLocalTime(), venue);

        return List.of(event1, event2, event3, event4, event5);
    }
}
