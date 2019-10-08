package seedu.address.model.module;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;

/**
 * Contains holiday dates strings.
 */
public class Holidays {
    private List<String> holidayDates;

    public Holidays(List<String> holidays) {
        holidayDates = holidays;
    }

    /**
     * Parse Holidays from a JSONArray
     * @param arr JSONArray
     * @return Holidays object
     */
    public static Holidays parseFromJson(JSONArray arr) {
        requireNonNull(arr);
        List<String> holidayDates = new ArrayList<>();

        for (int i = 0; i < arr.size(); i++) {
            holidayDates.add(arr.get(i).toString());
        }

        return new Holidays(holidayDates);
    }

    public List<String> getHolidayDates() {
        return holidayDates;
    }

    public String toString() {
        return holidayDates.toString();
    }
}

