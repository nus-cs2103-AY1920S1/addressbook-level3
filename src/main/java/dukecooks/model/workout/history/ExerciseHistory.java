package dukecooks.model.workout.history;

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
        noTimesRan = previousRuns.size();
        this.previousRuns = previousRuns;
        averageRunTime = getAverageRunTime();
    }

    /**
     * Creates a new ExerciseHistory that accounts for a new ExerciseRun
     */
    public ExerciseHistory addRun(ExerciseRun runToAdd) {
        ArrayList<ExerciseRun> newPreviousRuns = new ArrayList<>();
        newPreviousRuns.addAll(previousRuns);
        newPreviousRuns.add(runToAdd);
        return new ExerciseHistory(newPreviousRuns);
    }

    private Duration getAverageRunTime() {
        if (previousRuns.isEmpty()) {
            return Duration.ZERO;
        }
        Duration totalDuration = Duration.ZERO;
        for (ExerciseRun run : previousRuns) {
            totalDuration.plus(run.getTotalTimeTaken());
        }
        return totalDuration.dividedBy((long) previousRuns.size());
    }

    public Integer getNoTimesRan() {
        return noTimesRan;
    }

    public ArrayList<ExerciseRun> getPreviousRuns() {
        return previousRuns;
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
