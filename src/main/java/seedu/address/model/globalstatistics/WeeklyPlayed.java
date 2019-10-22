package seedu.address.model.globalstatistics;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents how many times a user has played in the current week.
 */
public class WeeklyPlayed {

    private Map<DayOfWeek, Integer> numPlayed;
    private LocalDate startOfWeek;

    public WeeklyPlayed() {
        initNumPlayed();
        startOfWeek = getCurrentStartOfWeek();
    }

    /**
     * Constructor.
     * @param numPlayed A map from the day of week to how many times the user has played.
     * @param startOfWeek The start of week {@code numPlayed} was recorded.
     */
    public WeeklyPlayed(Map<DayOfWeek, Integer> numPlayed, LocalDate startOfWeek) {
        this();
        if (startOfWeek.equals(this.startOfWeek)) {
            // week has not changed since last time app opened
            this.numPlayed.putAll(numPlayed);
        }
    }

    /**
     * Increment the number of play for today.
     */
    public void incrementPlay() {
        LocalDate curStartOfWeek = getCurrentStartOfWeek();
        if (!curStartOfWeek.equals(startOfWeek)) {
            // move to new week
            initNumPlayed();
            startOfWeek = curStartOfWeek;
        }
        DayOfWeek today = LocalDate.now().getDayOfWeek();
        numPlayed.replace(today, numPlayed.get(today) + 1);
    }

    public Map<DayOfWeek, Integer> getNumPlayed() {
        return numPlayed;
    }

    public LocalDate getStartOfWeek() {
        return startOfWeek;
    }

    private void initNumPlayed() {
        numPlayed = new HashMap<>();
        Arrays.stream(DayOfWeek.values()).forEach(x -> numPlayed.put(x, 0)); // init map
    }

    private LocalDate getCurrentStartOfWeek() {
        return LocalDate.now().with(DayOfWeek.SUNDAY);
    }
}
