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

    private Duration calcAverageRunTime() {
        Duration totalDuration = Duration.ZERO;
        for (WorkoutRun run : previousRuns){
            totalDuration.plus(run.getTotalTimeTaken());
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
