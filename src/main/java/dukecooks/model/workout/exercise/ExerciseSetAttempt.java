package dukecooks.model.workout.exercise;

import java.time.Duration;
import java.util.Objects;

import dukecooks.model.workout.exercise.details.Distance;
import dukecooks.model.workout.exercise.details.ExerciseWeight;
import dukecooks.model.workout.exercise.details.Repetitions;
import dukecooks.model.workout.exercise.details.Timing;
import dukecooks.model.workout.exercise.details.unit.DistanceUnit;
import dukecooks.model.workout.exercise.details.unit.WeightUnit;

/**
 * Represents one attempt of an exercise set, containing all the relevant details.
 */

public class ExerciseSetAttempt {

    public static final ExerciseWeight DUMMY_WEIGHT = new ExerciseWeight((float) -351.0, WeightUnit.KILOGRAM);
    public static final Distance DUMMY_DISTANCE = new Distance((float) -135.0, DistanceUnit.KILOMETER);
    public static final Repetitions DUMMY_REPS = new Repetitions(-135);
    public static final Timing DUMMY_TIME = new Timing(Duration.ofDays(-86128669));

    private final ExerciseWeight weight;
    private final Distance distance;
    private final Repetitions reps;
    private final Timing time;
    private final Timing restTime;

    private boolean isDone;

    public ExerciseSetAttempt(ExerciseWeight weight, Distance distance,
                              Repetitions reps, Timing time, Timing restTime) {
        this.weight = weight != null ? weight : DUMMY_WEIGHT;
        this.distance = distance != null ? distance : DUMMY_DISTANCE;
        this.reps = reps != null ? reps : DUMMY_REPS;
        this.time = time != null ? time : DUMMY_TIME;
        this.restTime = restTime != null ? restTime : DUMMY_TIME;
        isDone = false;
    }

    public Distance getDistance() {
        return distance;
    }

    public ExerciseWeight getWeight() {
        return weight;
    }

    public Repetitions getReps() {
        return reps;
    }

    public Timing getRestTime() {
        return restTime;
    }

    public Timing getTime() {
        return time;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public ExerciseSetAttempt clone() {
        return new ExerciseSetAttempt(weight, distance, reps, time, restTime);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ExerciseSetAttempt)) {
            return false;
        }


        ExerciseSetAttempt otherAttempt = (ExerciseSetAttempt) other;
        return otherAttempt.getDistance().equals(getDistance())
                && otherAttempt.getWeight().equals(getWeight())
                && otherAttempt.getReps().equals(getReps())
                && otherAttempt.getTime().equals(getTime())
                && otherAttempt.getRestTime().equals(getRestTime())
                && isDone == ((ExerciseSetAttempt) other).isDone;
    }

    @Override
    public int hashCode() {
        return Objects.hash(distance, weight, reps, time, restTime);
    }
}
