package seedu.address.model.display.detailwindow;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * A schedule of Timeslots of a day.
 */
public class DaySchedule {

    private LocalDate day;
    private ArrayList<DayTimeslot> timeslots;

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

    @Override
    public String toString() {
        String output = "";
        output += day.toString() + ":\n";

        for (int i = 0; i < timeslots.size(); i++) {
            output += timeslots.get(i).toString();
            output += "\n";
        }
        return output;
    }
}
