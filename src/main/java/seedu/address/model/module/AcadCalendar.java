package seedu.address.model.module;

import java.util.Map;

/**
 * Contains start dates of each semester of each academic year.
 */
public class AcadCalendar {
    private Map<String, String> startDates;

    public AcadCalendar(Map<String, String> startDates) {
        this.startDates = startDates;
    }

    public String getStartDateString(AcadYear acadYear, SemesterNo semesterNo) {
        String acadYearSemNoString = acadYear.toString() + " Sem " + semesterNo.toString();
        return startDates.get(acadYearSemNoString);
    }

    public Map<String, String> getStartDates() {
        return startDates;
    }

    public String toString() {
        return startDates.toString();
    }
}
