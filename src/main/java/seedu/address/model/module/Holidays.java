package seedu.address.model.module;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains holiday dates strings.
 */
public class Holidays {
    private List<LocalDate> holidayDates;

    public Holidays() {
        holidayDates = new ArrayList<>();
    }

    public Holidays(List<LocalDate> holidays) {
        holidayDates = holidays;
    }

    public List<LocalDate> getHolidayDates() {
        return holidayDates;
    }

    public String toString() {
        return holidayDates.toString();
    }
}

