package seedu.address.storage.globalstatistics;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.model.globalstatistics.WeeklyPlayed;

/**
 * Jackson-friendly version of {@link WeeklyPlayed}.
 */
public class JsonAdaptedWeeklyPlayed {
    private Map<Integer, Integer> days;
    private long startOfWeekEpochDays;

    public JsonAdaptedWeeklyPlayed(@JsonProperty("days") Map<Integer, Integer> days,
                                   @JsonProperty("startOfWeek") long startOfWeekEpochDays) {
        this.days = new HashMap<>();
        this.days.putAll(days);
        this.startOfWeekEpochDays = startOfWeekEpochDays;
    }

    /**
     * Converts a given {@code WeeklyPlayed} into this class for Jackson use.
     */
    public JsonAdaptedWeeklyPlayed(WeeklyPlayed weeklyPlayed) {
        this.days = new HashMap<>();
        for (Map.Entry<DayOfWeek, Integer> entry : weeklyPlayed.getNumPlayed().entrySet()) {
            this.days.put(entry.getKey().getValue(), entry.getValue());
        }
        this.startOfWeekEpochDays = weeklyPlayed.getStartOfWeek().toEpochDay();
    }

    /**
     * Converts this weekly played into the model's {@code WeeklyPlayed} object.
     */
    public WeeklyPlayed toModelType() {
        Map<DayOfWeek, Integer> map = new HashMap<>();
        for (Map.Entry<Integer, Integer> entry : days.entrySet()) {
            map.put(DayOfWeek.of(entry.getKey()), entry.getValue());
        }
        return new WeeklyPlayed(map, LocalDate.ofEpochDay(startOfWeekEpochDays));
    }
}
