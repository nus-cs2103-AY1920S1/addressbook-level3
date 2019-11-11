package seedu.address.ui.uicomponent.schedule;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import seedu.address.model.display.timeslots.PersonTimeslot;
import seedu.address.model.display.timeslots.WeekSchedule;
import seedu.address.model.person.schedule.Venue;
import seedu.address.ui.util.ColorGenerator;

/**
 * A class for testing Schedule View.
 */
public class WeekScheduleBuilder {

    public static final List<String> EVENT_NAMES = List.of("EVENT1", "EVENT2", "EVENT3", "EVENT4",
            "EVENT5", "EVENT6", "EVENT7", "EVENT8", "EVENT9", "EVENT10", "EVENT11",
            "EVENT12", "EVENT13", "EVENT14", "EVENT15", "VENT16");
    private static final String COLOR = ColorGenerator.generateColor(0);
    private static final Venue VENUE = new Venue("Central library");
    private LocalDate now;
    private WeekSchedule weekSchedule;

    public WeekScheduleBuilder(LocalDate now) {
        this.now = now;
        initialiseContents();
    }

    public static WeekSchedule getValidSchedule() {
        HashMap<DayOfWeek, ArrayList<PersonTimeslot>> weekSch = new HashMap<>();
        weekSch.put(DayOfWeek.of(1), simplePersonTimeslotsStub());
        weekSch.put(DayOfWeek.of(2), backToBackPersonTimeslotsStub());
        weekSch.put(DayOfWeek.of(3), weirdGapsPersonTimeslotsStub());
        weekSch.put(DayOfWeek.of(4), anotherWeirdGapPersonTimeslotsStub());
        weekSch.put(DayOfWeek.of(5), fullDayPersonTimeslotsStub());
        weekSch.put(DayOfWeek.of(6), emptyPersonTimeslotsStub());
        weekSch.put(DayOfWeek.of(7), emptyPersonTimeslotsStub());
        return new WeekSchedule(weekSch);
    }

    public static WeekSchedule getInvalidSchedule() {
        HashMap<DayOfWeek, ArrayList<PersonTimeslot>> weekSch = new HashMap<>();
        weekSch.put(DayOfWeek.of(1), simplePersonTimeslotsStub());
        weekSch.put(DayOfWeek.of(2), backToBackPersonTimeslotsStub());
        weekSch.put(DayOfWeek.of(3), weirdGapsPersonTimeslotsStub());
        weekSch.put(DayOfWeek.of(4), anotherWeirdGapPersonTimeslotsStub());
        weekSch.put(DayOfWeek.of(5), fullDayPersonTimeslotsStub());
        weekSch.put(DayOfWeek.of(6), invalidPersonTimeslotsStub());
        weekSch.put(DayOfWeek.of(7), emptyPersonTimeslotsStub());
        return new WeekSchedule(weekSch);
    }

    /**
     * Creates an empty schedule.
     * @return
     */
    public static WeekSchedule getEmptySchedule() {
        HashMap<DayOfWeek, ArrayList<PersonTimeslot>> weekSch = new HashMap<>();
        for (int i = 1; i <= 7; i++) {
            ArrayList<PersonTimeslot> timeslots = new ArrayList<>();
            weekSch.put(DayOfWeek.of(i), timeslots);
        }
        return new WeekSchedule(weekSch);
    }

    /**
     * Initialises the contents in the stub.
     */
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
        this.weekSchedule = new WeekSchedule(weekSch);
    }

    /**
     * Creates an invalid time slot.
     */
    public static ArrayList<PersonTimeslot> invalidPersonTimeslotsStub() {
        ArrayList<PersonTimeslot> timeslots = new ArrayList<>();
        timeslots.add(new PersonTimeslot(
                EVENT_NAMES.get(0), null, LocalTime.of(8, 0),
                LocalTime.of(9, 0), VENUE, COLOR, false, null));
        timeslots.add(new PersonTimeslot(
                EVENT_NAMES.get(1), null, LocalTime.of(8, 40),
                LocalTime.of(9, 30), VENUE, COLOR, false, null));
        timeslots.add(new PersonTimeslot(
                EVENT_NAMES.get(2), null, LocalTime.of(14, 0),
                LocalTime.of(16, 0), VENUE, COLOR, false, null));
        return timeslots;
    }

    /**
     * Creates a simple and ordinary list of PersonTimeslots.
     * @return ArrayList of PersonTimeslot.
     */
    public static ArrayList<PersonTimeslot> simplePersonTimeslotsStub() {
        ArrayList<PersonTimeslot> timeslots = new ArrayList<>();
        timeslots.add(new PersonTimeslot(
                EVENT_NAMES.get(0), null, LocalTime.of(8, 0),
                LocalTime.of(9, 0), VENUE, COLOR, false, null));
        timeslots.add(new PersonTimeslot(
                EVENT_NAMES.get(1), null, LocalTime.of(10, 0),
                LocalTime.of(12, 0), VENUE, COLOR, false, null));
        timeslots.add(new PersonTimeslot(
                EVENT_NAMES.get(2), null, LocalTime.of(14, 0),
                LocalTime.of(16, 0), VENUE, COLOR, false, null));
        return timeslots;
    }

    /**
     * Creates an ArrayList of PersonTimeslot which has back to back time slots.
     * @return ArrayList of PersonTimeslot.
     */
    public static ArrayList<PersonTimeslot> backToBackPersonTimeslotsStub() {
        ArrayList<PersonTimeslot> timeslots = new ArrayList<>();
        //Back to back lessons.
        timeslots.add(new PersonTimeslot(
                EVENT_NAMES.get(3), null, LocalTime.of(8, 0), LocalTime.of(9, 0),
                VENUE, COLOR, false, null));
        timeslots.add(new PersonTimeslot(
                EVENT_NAMES.get(4), null, LocalTime.of(9, 0), LocalTime.of(10, 0),
                VENUE, COLOR, false, null));
        timeslots.add(new PersonTimeslot(
                EVENT_NAMES.get(5), null, LocalTime.of(10, 0), LocalTime.of(12, 0),
                VENUE, COLOR, false, null));
        return timeslots;
    }

    /**
     * Creates an ArrayList of PersonTimeslot that has irregular time slots.
     * @return ArrayList of irregular PersonTimeslot.
     */
    public static ArrayList<PersonTimeslot> weirdGapsPersonTimeslotsStub() {
        ArrayList<PersonTimeslot> timeslots = new ArrayList<>();
        //Lessons with weird gaps.
        timeslots.add(new PersonTimeslot(
                EVENT_NAMES.get(6), null, LocalTime.of(8, 10), LocalTime.of(8, 35),
                VENUE, COLOR, false, null));
        timeslots.add(new PersonTimeslot(
                EVENT_NAMES.get(7), null, LocalTime.of(10, 35), LocalTime.of(12, 45),
                VENUE, COLOR, false, null));
        timeslots.add(new PersonTimeslot(
                EVENT_NAMES.get(8), null, LocalTime.of(14, 55), LocalTime.of(16, 0),
                VENUE, COLOR, false, null));
        return timeslots;
    }

    /**
     * Creates a list of PersonTimeslot with irregular times.
     * @return ArrayList of PersonTimeslot.
     */
    public static ArrayList<PersonTimeslot> anotherWeirdGapPersonTimeslotsStub() {
        //Another weird set of time slots.
        ArrayList<PersonTimeslot> timeslots = new ArrayList<>();
        timeslots.add(new PersonTimeslot(
                EVENT_NAMES.get(9), null, LocalTime.of(8, 0), LocalTime.of(8, 25),
                VENUE, COLOR, false, null));
        timeslots.add(new PersonTimeslot(
                EVENT_NAMES.get(10), null, LocalTime.of(10, 35), LocalTime.of(12, 45),
                VENUE, COLOR, false, null));
        timeslots.add(new PersonTimeslot(
                EVENT_NAMES.get(11), null, LocalTime.of(14, 55), LocalTime.of(16, 5),
                VENUE, COLOR, false, null));
        return timeslots;
    }


    /**
     * Generates a list that contains one PersonTimeslot that span the whole schedule view duration.
     * @return ArrayList of PersonTimeslot containing the person with the entire schedule.
     */
    public static ArrayList<PersonTimeslot> fullDayPersonTimeslotsStub() {
        ArrayList<PersonTimeslot> timeslots = new ArrayList<>();
        //One long day
        timeslots.add(new PersonTimeslot(
                EVENT_NAMES.get(12), null, LocalTime.of(8, 0), LocalTime.of(20, 0),
                VENUE, COLOR, false, null));
        return timeslots;
    }

    /**
     * Creates an empty list of PersonTimeslot.
     * @return an empty ArrayList of PersonTimeslot.
     */
    public static ArrayList<PersonTimeslot> emptyPersonTimeslotsStub() {
        ArrayList<PersonTimeslot> timeslots = new ArrayList<>();
        return timeslots;
    }

    public WeekSchedule getSampleSchedule() {
        return weekSchedule;
    }

}
