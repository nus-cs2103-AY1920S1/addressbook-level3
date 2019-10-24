package seedu.ezwatchlist.api;

import java.util.Optional;

import seedu.ezwatchlist.api.model.Information;

/**
 * Movie information class to store information about movies.
 */
public class MovieInformation extends Information {
    private Optional<Integer> runningTime;

    public void setRunningTime(int runningTime) {
        this.runningTime = Optional.ofNullable(runningTime);
    }

    public Optional<Integer> getRunningTime() {
        return runningTime;
    }
}
