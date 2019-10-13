package seedu.ezwatchlist.api;

import java.util.Optional;

public class MovieInformation {
    public Optional<Integer> runningTime;

    public void addRunningTime(int runningTime) {
        this.runningTime = Optional.ofNullable(runningTime);
    }
}
