package seedu.ezwatchlist.api;

import seedu.ezwatchlist.api.model.Information;

import java.util.Optional;

public class MovieInformation extends Information {
    public Optional<Integer> runningTime;

    public void addRunningTime(int runningTime) {
        this.runningTime = Optional.ofNullable(runningTime);
    }
}
