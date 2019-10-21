package seedu.address.model.globalstatistics;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class WeeklyPlayed {

    private Map<DayOfWeek, Integer> numPlayed;
    private LocalDate startOfWeek;

    public WeeklyPlayed() {
        initNumPlayed();
        startOfWeek = getCurrentStartOfWeek();
    }

    public WeeklyPlayed(Map<DayOfWeek, Integer> numPlayed, LocalDate startOfWeek) {
        this();
        if (startOfWeek.equals(this.startOfWeek)) {
            // week has not changed since last time app opened
            this.numPlayed.putAll(numPlayed);
        }
    }

    void incrementPlay() {
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
