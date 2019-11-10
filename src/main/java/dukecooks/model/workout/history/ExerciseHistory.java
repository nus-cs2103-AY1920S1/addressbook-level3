package dukecooks.model.workout.history;

import static java.util.Objects.requireNonNull;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Represents a record of all the time the exercise has been ran;
 */

public class ExerciseHistory {

    private final Integer noTimesRan;
    private final Duration averageRunTime;
    private final ArrayList<ExerciseRun> previousRuns;

    public ExerciseHistory(ArrayList<ExerciseRun> previousRuns) {
        requireNonNull(previousRuns);
        noTimesRan = previousRuns.size();
        this.previousRuns = previousRuns;
        averageRunTime = getAverageRunTime();
    }

    /**
     * Creates a new ExerciseHistory that accounts for a new ExerciseRun
     */
    public ExerciseHistory addRun(ExerciseRun runToAdd) {
        requireNonNull(runToAdd);
        ArrayList<ExerciseRun> newPreviousRuns = new ArrayList<>();
        newPreviousRuns.addAll(previousRuns);
        newPreviousRuns.add(runToAdd);
        return new ExerciseHistory(newPreviousRuns);
    }

    /**
     * Calculates average run timr based off of the previous runs list.
     */
    
    private Duration getAverageRunTime() {
        if (previousRuns.isEmpty()) {
            return Duration.ZERO;
        }
        Duration totalDuration = Duration.ZERO;
        for (ExerciseRun run : previousRuns) {
            totalDuration = totalDuration.plus(run.getTotalTimeTaken());
        }
        return totalDuration.dividedBy((long) previousRuns.size());
    }

    public Integer getNoTimesRan() {
        return noTimesRan;
    }

    public ArrayList<ExerciseRun> getPreviousRuns() {
        return previousRuns;
    }

    public ExerciseHistory clone() {
        return new ExerciseHistory(previousRuns);
    }

    /**
     * Returns the average run time in a standard format.
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

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ExerciseHistory)) {
            return false;
        }

        ExerciseHistory otherHistory = (ExerciseHistory) other;
        return otherHistory.getNoTimesRan().equals(getNoTimesRan())
                && otherHistory.getAverageRunTime().equals(getAverageRunTime())
                && otherHistory.getPreviousRuns().equals(getPreviousRuns());
    }

    @Override
    public int hashCode() {
        return Objects.hash(noTimesRan, previousRuns, averageRunTime);
    }
}
