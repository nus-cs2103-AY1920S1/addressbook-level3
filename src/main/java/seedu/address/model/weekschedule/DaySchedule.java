package seedu.address.model.weekschedule;

import java.time.LocalDate;
import java.util.ArrayList;

public class DaySchedule {

    LocalDate day;
    ArrayList<DayTimeslot> timeslots;

    public DaySchedule(LocalDate day) {
        this.day = day;
        this.timeslots = new ArrayList<>();
    }

    public void addTimeslot(DayTimeslot timeslot) {
        timeslots.add(timeslot);
    }


    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    public ArrayList<DayTimeslot> getTimeslots() {
        return timeslots;
    }

    public void setTimeslots(ArrayList<DayTimeslot> timeslots) {
        this.timeslots = timeslots;
    }
}
