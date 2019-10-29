package dukecooks.model.workout.exercise;

import dukecooks.model.workout.exercise.details.Distance;
import dukecooks.model.workout.exercise.details.ExerciseWeight;
import dukecooks.model.workout.exercise.details.Repetitions;
import dukecooks.model.workout.exercise.details.Timing;

import java.util.Objects;

/**
 * Represents one attempt of an exercise set, containing all the relevant details.
 */

public class ExerciseSetAttempt {

    private final ExerciseWeight weight;
    private final Distance distance;
    private final Repetitions reps;
    private final Timing time;
    private final Timing restTime;

    public ExerciseSetAttempt(ExerciseWeight weight, Distance distance, Repetitions reps, Timing time, Timing restTime){
        this.weight = weight;
        this.distance = distance;
        this.reps = reps;
        this.time = time;
        this.restTime = restTime;
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

    @Override
    public boolean equals(Object other){
        if (other == this) {
            return true;
        }

        if (!(other instanceof ExerciseSetAttempt)) {
            return false;
        }

        ExerciseSetAttempt otherAttemt = (ExerciseSetAttempt) other;
        return otherAttemt.getDistance().equals(getDistance())
                && otherAttemt.getWeight().equals(getWeight())
                && otherAttemt.getReps().equals(getReps())
                && otherAttemt.getTime().equals(getTime())
                && otherAttemt.getRestTime().equals(getRestTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(distance, weight, reps, time, restTime);
    }
}
