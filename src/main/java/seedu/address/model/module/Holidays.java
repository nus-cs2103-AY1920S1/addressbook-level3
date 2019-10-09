package seedu.address.model.module;

import java.util.List;

/**
 * Contains holiday dates strings.
 */
public class Holidays {
    private List<String> holidayDates;

    public Holidays(List<String> holidays) {
        holidayDates = holidays;
    }

    public List<String> getHolidayDates() {
        return holidayDates;
    }

    public String toString() {
        return holidayDates.toString();
    }
}

