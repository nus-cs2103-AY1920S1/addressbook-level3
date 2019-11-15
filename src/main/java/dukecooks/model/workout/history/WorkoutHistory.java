package dukecooks.model.workout.history;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Represents a record of all the time the exercise has been ran;
 */

public class WorkoutHistory {

    private final Integer noTimesRan;
    private final Duration averageRunTime;
    private final ArrayList<WorkoutRun> previousRuns;

    public WorkoutHistory(ArrayList<WorkoutRun> previousRuns) {
        noTimesRan = previousRuns.size();
        this.previousRuns = previousRuns;
        averageRunTime = calcAverageRunTime();
    }

    /**
     * Creates a new ExerciseHistory that accounts for a new ExerciseRun
     */
    public WorkoutHistory addRun(WorkoutRun runToAdd) {
        ArrayList<WorkoutRun> newPreviousRuns = new ArrayList<>();
        newPreviousRuns.addAll(previousRuns);
        newPreviousRuns.add(runToAdd);
        return new WorkoutHistory(newPreviousRuns);
    }

    /**
     * Returns the average run time amongst all the previous runs.
     */
    private Duration calcAverageRunTime() {
        if (previousRuns.isEmpty()) {
            return Duration.ZERO;
        }
        Duration totalDuration = Duration.ZERO;
        for (WorkoutRun run : previousRuns) {
            totalDuration = totalDuration.plus(run.getTotalTimeTaken());
        }
        return totalDuration.dividedBy((long) previousRuns.size());
    }

    public Duration getAverageRunTime() {
        return averageRunTime;
    }

    public Integer getNoTimesRan() {
        return noTimesRan;
    }

    public ArrayList<WorkoutRun> getPreviousRuns() {
        return previousRuns;
    }

    /**
     * Returns averageRunTime in a format that is more understandable.
     */
    public String getAverageRunTimeString() {
        long seconds = averageRunTime.getSeconds();
        long absSeconds = Math.abs(seconds);
        String positive = String.format(
                "%d:%02d:%02d",
                absSeconds / 3600, (
                        absSeconds % 3600) / 60,
                absSeconds % 60);
        return seconds < 0 ? "-" + positive : positive;
    }

    /**
     * Returns most newly added run.
     */
    public WorkoutRun getMostRecentRun() {
        return previousRuns.get(previousRuns.size() - 1);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof WorkoutHistory)) {
            return false;
        }

        WorkoutHistory otherHistory = (WorkoutHistory) other;
        return otherHistory.getNoTimesRan().equals(getNoTimesRan())
                && otherHistory.getPreviousRuns().equals(getPreviousRuns());
    }

    @Override
    public int hashCode() {
        return Objects.hash(noTimesRan, previousRuns);
    }
}
