package seedu.address.ui.uicomponent;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import seedu.address.model.display.detailwindow.PersonTimeslot;
import seedu.address.model.display.schedulewindow.WeekSchedule;
import seedu.address.model.person.schedule.Venue;

/**
 * A class for testing Schedule View.
 */
public class WeekScheduleStub {

    private LocalDate now;
    private WeekSchedule stub;
    private static final Venue VENUE = new Venue("Central library");
    public static final List<String> EVENT_NAMES = List.of("EVENT0", "EVENT1", "EVENT2", "EVENT3", "EVENT4", "EVENT5", "EVENT6",
            "EVENT7", "EVENT8", "EVENT9", "EVENT10", "EVENT11", "EVENT12", "EVENT13", "EVENT14", "EVENT15", "EVENT16");

    public WeekScheduleStub(LocalDate now) {
        this.now = now;
        initialiseContents();
    }

    public static WeekSchedule getEmptySchedule() {
        HashMap<DayOfWeek, ArrayList<PersonTimeslot>> weekSch = new HashMap<>();
        for (int i = 1; i <= 7; i++) {
            ArrayList<PersonTimeslot> timeslots = new ArrayList<>();
        }
        return new WeekSchedule(weekSch);
    }

    public void initialiseContents() {
        HashMap<DayOfWeek, ArrayList<PersonTimeslot>> weekSch = new HashMap<>();
        weekSch.put(now.plusDays(0).getDayOfWeek(), simplePersonTimeslotsStub());
        weekSch.put(now.plusDays(1).getDayOfWeek(), backToBackPersonTimeslotsStub());
        weekSch.put(now.plusDays(2).getDayOfWeek(), weirdGapsPersonTimeslotsStub());
        weekSch.put(now.plusDays(3).getDayOfWeek(), anotherWeirdGapPersonTimeslotsStub());
        weekSch.put(now.plusDays(4).getDayOfWeek(), fullDayPersonTimeslotsStub());
        weekSch.put(now.plusDays(5).getDayOfWeek(), emptyPersonTimeslotsStub());
        weekSch.put(now.plusDays(6).getDayOfWeek(), emptyPersonTimeslotsStub());
        for (int i = 1; i <= 7; i++) {
            ArrayList<PersonTimeslot> personTimeslots = new ArrayList<>();
            if (!weekSch.containsKey(DayOfWeek.of(i))) {
                weekSch.put(DayOfWeek.of(i), personTimeslots);
            }
        }
        this.stub = new WeekSchedule(weekSch);
    }

    public ArrayList<PersonTimeslot> simplePersonTimeslotsStub() {
        ArrayList<PersonTimeslot> timeslots = new ArrayList<>();
        timeslots.add(new PersonTimeslot(
                EVENT_NAMES.get(0), LocalTime.of(8, 0), LocalTime.of(9, 0), VENUE));
        timeslots.add(new PersonTimeslot(
                EVENT_NAMES.get(1), LocalTime.of(10, 0), LocalTime.of(12, 0), VENUE));
        timeslots.add(new PersonTimeslot(
                EVENT_NAMES.get(2), LocalTime.of(14, 0), LocalTime.of(16, 0), VENUE));
        return timeslots;
    }

    //Back to back lessons.
    public ArrayList<PersonTimeslot> backToBackPersonTimeslotsStub() {
        ArrayList<PersonTimeslot> timeslots = new ArrayList<>();
        timeslots.add(new PersonTimeslot(
                EVENT_NAMES.get(3), LocalTime.of(8, 0), LocalTime.of(9, 0), VENUE));
        timeslots.add(new PersonTimeslot(
                EVENT_NAMES.get(4), LocalTime.of(9, 0), LocalTime.of(10, 0), VENUE));
        timeslots.add(new PersonTimeslot(
                EVENT_NAMES.get(5), LocalTime.of(10, 0), LocalTime.of(12, 0), VENUE));
        return timeslots;
    }

    //Lessons with weird gaps.
    public ArrayList<PersonTimeslot> weirdGapsPersonTimeslotsStub() {
        ArrayList<PersonTimeslot> timeslots = new ArrayList<>();
        timeslots.add(new PersonTimeslot(
                EVENT_NAMES.get(6), LocalTime.of(8, 10), LocalTime.of(8, 25), VENUE));
        timeslots.add(new PersonTimeslot(
                EVENT_NAMES.get(7), LocalTime.of(10, 35), LocalTime.of(12, 45), VENUE));
        timeslots.add(new PersonTimeslot(
                EVENT_NAMES.get(8), LocalTime.of(14, 55), LocalTime.of(16, 0), VENUE));
        return timeslots;
    }

    // Another set of weird time slots.
    public ArrayList<PersonTimeslot> anotherWeirdGapPersonTimeslotsStub() {
        ArrayList<PersonTimeslot> timeslots = new ArrayList<>();
        timeslots.add(new PersonTimeslot(
                EVENT_NAMES.get(9), LocalTime.of(8, 0), LocalTime.of(8, 25), VENUE));
        timeslots.add(new PersonTimeslot(
                EVENT_NAMES.get(10), LocalTime.of(10, 35), LocalTime.of(12, 45), VENUE));
        timeslots.add(new PersonTimeslot(
                EVENT_NAMES.get(11), LocalTime.of(14, 55), LocalTime.of(16, 5), VENUE));
        return timeslots;
    }

    //One long day
    public ArrayList<PersonTimeslot> fullDayPersonTimeslotsStub() {
        ArrayList<PersonTimeslot> timeslots = new ArrayList<>();
        timeslots.add(new PersonTimeslot(
                EVENT_NAMES.get(12), LocalTime.of(8, 0), LocalTime.of(20, 0), VENUE));
        return timeslots;
    }

    public ArrayList<PersonTimeslot> emptyPersonTimeslotsStub() {
        ArrayList<PersonTimeslot> timeslots = new ArrayList<>();
        return timeslots;
    }

    public WeekSchedule getSampleSchedule() {
        return stub;
    }

}
