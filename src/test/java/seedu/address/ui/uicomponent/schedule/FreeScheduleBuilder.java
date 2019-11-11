package seedu.address.ui.uicomponent.schedule;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import seedu.address.model.display.timeslots.FreeSchedule;
import seedu.address.model.display.timeslots.FreeTimeslot;

/**
 * Class to build free schedules for testing purposes.
 */
public class FreeScheduleBuilder {

    private LocalDate now;
    private FreeSchedule freeSchedule;
    private List<Integer> id = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15);
    public FreeScheduleBuilder(LocalDate now) {
        this.now = now;
        initialiseContent();
    }

    /**
     * Initialises the content for free schedule.
     */
    private void initialiseContent() {
        HashMap<DayOfWeek, ArrayList<FreeTimeslot>> freeSchedule = new HashMap<>();
        freeSchedule.put(now.plusDays(0).getDayOfWeek(), simpleFreeTimeslotsStub());
        freeSchedule.put(now.plusDays(1).getDayOfWeek(), backToBackFreeTimeslotsStub());
        freeSchedule.put(now.plusDays(2).getDayOfWeek(), weirdGapsFreeTimeslotsStub());
        freeSchedule.put(now.plusDays(3).getDayOfWeek(), anotherWeirdGapFreeTimeslotsStub());
        freeSchedule.put(now.plusDays(4).getDayOfWeek(), fullDayFreeTimeslotsStub());
        freeSchedule.put(now.plusDays(5).getDayOfWeek(), emptyFreeTimeslotsStub());
        freeSchedule.put(now.plusDays(6).getDayOfWeek(), emptyFreeTimeslotsStub());
        this.freeSchedule = new FreeSchedule(freeSchedule);
    }

    /**
     * Creates a simple list of FreeTimeslots.
     * @return Simple FreeTimeslots ArrayList.
     */
    private ArrayList<FreeTimeslot> simpleFreeTimeslotsStub() {
        ArrayList<FreeTimeslot> freeTimeslots = new ArrayList<>();
        freeTimeslots.add(new FreeTimeslot(id.get(0), new ArrayList<>(), null,
                LocalTime.of(8, 0), LocalTime.of(9, 0)));
        freeTimeslots.add(new FreeTimeslot(id.get(1), new ArrayList<>(), null,
                LocalTime.of(10, 0), LocalTime.of(12, 0)));
        freeTimeslots.add(new FreeTimeslot(id.get(2), new ArrayList<>(), null,
                LocalTime.of(14, 0), LocalTime.of(16, 0)));
        return freeTimeslots;
    }

    /**
     * Creates a back to back list of FreeTimeslots.
     * @return Back to back FreeTimeslots ArrayList.
     */
    private ArrayList<FreeTimeslot> backToBackFreeTimeslotsStub() {
        ArrayList<FreeTimeslot> freeTimeslots = new ArrayList<>();
        freeTimeslots.add(new FreeTimeslot(id.get(3), new ArrayList<>(), null,
                LocalTime.of(8, 0), LocalTime.of(10, 0)));
        freeTimeslots.add(new FreeTimeslot(id.get(4), new ArrayList<>(), null,
                LocalTime.of(10, 0), LocalTime.of(12, 0)));
        freeTimeslots.add(new FreeTimeslot(id.get(5), new ArrayList<>(), null,
                LocalTime.of(12, 0), LocalTime.of(16, 0)));
        return freeTimeslots;
    }

    /**
     * Creates a full day List of FreeTimeslot.
     * @return Full day FreeTimeslot ArrayList.
     */
    private ArrayList<FreeTimeslot> fullDayFreeTimeslotsStub() {
        ArrayList<FreeTimeslot> freeTimeslots = new ArrayList<>();
        freeTimeslots.add(new FreeTimeslot(id.get(6), new ArrayList<>(), null,
                LocalTime.of(8, 0), LocalTime.of(20, 0)));
        return freeTimeslots;
    }

    /**
     * Creates a uncommon List of FreeTimeslots.
     * @return Uncommon FreeTimeslots ArrayList.
     */
    private ArrayList<FreeTimeslot> weirdGapsFreeTimeslotsStub() {
        ArrayList<FreeTimeslot> freeTimeslots = new ArrayList<>();
        freeTimeslots.add(new FreeTimeslot(id.get(7), new ArrayList<>(), null,
                LocalTime.of(8, 20), LocalTime.of(9, 52)));
        freeTimeslots.add(new FreeTimeslot(id.get(8), new ArrayList<>(), null,
                LocalTime.of(9, 55), LocalTime.of(10, 17)));
        freeTimeslots.add(new FreeTimeslot(id.get(9), new ArrayList<>(), null,
                LocalTime.of(11, 7), LocalTime.of(11, 48)));
        return freeTimeslots;
    }

    /**
     * Creates another uncommon List of FreeTimeslots.
     * @return Uncommon list of FreeTimeslots.
     */
    private ArrayList<FreeTimeslot> anotherWeirdGapFreeTimeslotsStub() {
        ArrayList<FreeTimeslot> freeTimeslots = new ArrayList<>();
        freeTimeslots.add(new FreeTimeslot(id.get(10), new ArrayList<>(), null,
                LocalTime.of(8, 0), LocalTime.of(8, 12)));
        freeTimeslots.add(new FreeTimeslot(id.get(11), new ArrayList<>(), null,
                LocalTime.of(9, 33), LocalTime.of(9, 50)));
        freeTimeslots.add(new FreeTimeslot(id.get(12), new ArrayList<>(), null,
                LocalTime.of(14, 30), LocalTime.of(16, 23)));
        return freeTimeslots;
    }

    /**
     * Creates an empty list of FreeTimeslots.
     * @return Empty ArrayList of FreeTimeslots.
     */
    private ArrayList<FreeTimeslot> emptyFreeTimeslotsStub() {
        ArrayList<FreeTimeslot> freeTimeslots = new ArrayList<>();
        return freeTimeslots;
    }

    /**
     * Returns the FreeSchedule created.
     * @return FreeSchedule created.
     */
    public FreeSchedule getSampleFreeSchedule() {
        return this.freeSchedule;
    }
}
