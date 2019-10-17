package seedu.ezwatchlist.api;

import java.util.Optional;

import seedu.ezwatchlist.api.model.Information;



public class MovieInformation extends Information {
    public Optional<Integer> runningTime;

    public void addRunningTime(int runningTime) {
        this.runningTime = Optional.ofNullable(runningTime);
    }
}
