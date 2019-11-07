package dukecooks.model.workout.history;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Represents one run of a Workout
 */

public class WorkoutRun {

    private final LocalDateTime timeStarted;
    private final LocalDateTime timeEnded;
    private final Integer totalExercisesCompleted;
    private final ArrayList<ExerciseRun> exercisesRan;
    private final Duration totalTimeTaken;

    public WorkoutRun(LocalDateTime timeStarted, LocalDateTime timeEnded,
                      int totalExercisesCompleted, ArrayList<ExerciseRun> exercisesRan) {
        this.timeStarted = timeStarted;
        this.timeEnded = timeEnded;
        this.totalExercisesCompleted = totalExercisesCompleted;
        this.exercisesRan = exercisesRan;
        totalTimeTaken = Duration.between(timeStarted, timeEnded);
    }

    public LocalDateTime getTimeStarted() {
        return timeStarted;
    }

    public LocalDateTime getTimeEnded() {
        return timeEnded;
    }

    public ArrayList<ExerciseRun> getExercisesRan() {
        return exercisesRan;
    }

    public Integer getTotalExercisesCompleted() {
        return totalExercisesCompleted;
    }

    public Duration getTotalTimeTaken() {
        return totalTimeTaken;
    }

    /**
     * Returns duration in a more readable format.
     */
    public String getTotalTimeTakenString() {
        long seconds = totalTimeTaken.getSeconds();
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

        if (!(other instanceof WorkoutRun)) {
            return false;
        }

        WorkoutRun otherRun = (WorkoutRun) other;
        return otherRun.getExercisesRan().equals(getExercisesRan())
                && otherRun.getTimeStarted().equals(getTimeStarted())
                && otherRun.getTimeEnded().equals(getTimeEnded())
                && otherRun.getTotalExercisesCompleted().equals(getTotalExercisesCompleted());
    }

    @Override
    public int hashCode() {
        return Objects.hash(exercisesRan, timeStarted, timeEnded, totalExercisesCompleted);
    }
}
