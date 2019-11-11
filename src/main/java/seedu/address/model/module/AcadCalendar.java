package seedu.address.model.module;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Contains start dates of each semester of each academic year.
 */
public class AcadCalendar {
    private Map<Map.Entry<AcadYear, SemesterNo>, LocalDate> startDates;

    public AcadCalendar() {
        this.startDates = new HashMap<>();
    }

    public AcadCalendar(Map<Map.Entry<AcadYear, SemesterNo>, LocalDate> startDates) {
        this.startDates = startDates;
    }

    public LocalDate getStartDate(AcadYear acadYear, SemesterNo semesterNo) {
        Map.Entry<AcadYear, SemesterNo> key = Map.entry(acadYear, semesterNo);
        return startDates.get(key);
    }

    public Map<Map.Entry<AcadYear, SemesterNo>, LocalDate> getStartDates() {
        return startDates;
    }

    @Override
    public String toString() {
        return startDates.toString();
    }
}
