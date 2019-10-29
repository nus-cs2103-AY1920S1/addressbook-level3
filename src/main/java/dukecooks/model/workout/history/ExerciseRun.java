package dukecooks.model.workout.history;

import dukecooks.model.workout.exercise.ExerciseSetAttempt;
import dukecooks.model.workout.exercise.details.Sets;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Represents one run of the exercise in a workout.
 */
public class ExerciseRun {

    final private LocalDateTime timeStarted;
    final private LocalDateTime timeEnded;
    final private Sets setsAttempted;
    final private Sets setsCompleted;
    final private ArrayList<ExerciseSetAttempt> exerciseSetAttempts;
    final private Duration totalTimeTaken;


    public ExerciseRun(LocalDateTime timeStarted, LocalDateTime timeEnded, Sets setsAttempted,
                       Sets setsCompleted, ArrayList<ExerciseSetAttempt> exerciseSetAttempts) {
        this.timeStarted = timeStarted;
        this.timeEnded = timeEnded;
        this.setsAttempted = setsAttempted;
        this.setsCompleted = setsCompleted;
        this.exerciseSetAttempts = exerciseSetAttempts;
        this.totalTimeTaken = Duration.between(timeStarted, timeEnded);
    }

    public ArrayList<ExerciseSetAttempt> getExerciseSetAttempts() {
        return exerciseSetAttempts;
    }

    public Duration getTotalTimeTaken() {
        return totalTimeTaken;
    }

    public LocalDateTime getTimeEnded() {
        return timeEnded;
    }

    public LocalDateTime getTimeStarted() {
        return timeStarted;
    }

    public Sets getSetsAttempted() {
        return setsAttempted;
    }

    public Sets getSetsCompleted() {
        return setsCompleted;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ExerciseRun)) {
            return false;
        }

        ExerciseRun otherRun = (ExerciseRun) other;
        return otherRun.getExerciseSetAttempts().equals(getExerciseSetAttempts())
                && otherRun.getTimeStarted().equals(getTimeStarted())
                && otherRun.getTimeEnded().equals(getTimeEnded())
                && otherRun.getSetsAttempted().equals(getSetsAttempted())
                && otherRun.getSetsCompleted().equals(getSetsCompleted());
    }

    @Override
    public int hashCode() {
        return Objects.hash(timeStarted, timeStarted, setsAttempted, setsCompleted, exerciseSetAttempts);
    }
}

