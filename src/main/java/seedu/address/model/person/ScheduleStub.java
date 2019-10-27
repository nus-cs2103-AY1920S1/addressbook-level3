package seedu.address.model.person;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import seedu.address.model.display.schedulewindow.WeekSchedule;
import seedu.address.model.mapping.Role;
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

    public WeekSchedule getSchedule() {
        p.setName(new Name("AlexwithaverylongnameSolongthateventhespellingofthisentiresentenceisonlyafraction"
                + "ofhistruename"));
        Event monday1pmTo3pm = new Event("Test", new ArrayList<>(List.of(timeslot1, timeslot2, timeslot3,
                timeslot4, timeslot5)));
        schedule.addEvent(monday1pmTo3pm);
        p.setSchedule(schedule);
        return new WeekSchedule("TestSchedule", LocalDateTime.now(), p, Role.emptyRole());
    }

    public List<Event> eventStubs() {
        Event event1 = new Event("Test run!");
        Event event2 = new Event("Test run 2");
        Event event3 = new Event("Test run 3");
        Event event4 = new Event("E4");
        Event event5 = new Event("E5");
        event1.addTimeslot(timeslot1);
        event2.addTimeslot(timeslot2);
        event3.addTimeslot(timeslot3);
        event4.addTimeslot(timeslot4);
        event5.addTimeslot(timeslot5);
        return List.of(event1, event2, event3, event4, event5);
    }
}
