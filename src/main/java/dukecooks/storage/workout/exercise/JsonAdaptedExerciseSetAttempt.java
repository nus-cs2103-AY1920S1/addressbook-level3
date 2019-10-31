package dukecooks.storage.workout.exercise;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import dukecooks.commons.exceptions.IllegalValueException;
import dukecooks.model.workout.exercise.ExerciseSetAttempt;
import dukecooks.model.workout.exercise.details.Distance;
import dukecooks.model.workout.exercise.details.ExerciseWeight;
import dukecooks.model.workout.exercise.details.Repetitions;
import dukecooks.model.workout.exercise.details.Timing;

public class JsonAdaptedExerciseSetAttempt {

    private final JsonAdaptedWeight weight;
    private final JsonAdaptedDistance distance;
    private final JsonAdaptedRepetitions reps;
    private final JsonAdaptedTiming time;
    private final JsonAdaptedTiming restTime;

    /**
     * Constructs a {@code JsonExerciseSetAttemot} with the given parameters.
     */
    @JsonCreator
    public JsonAdaptedExerciseSetAttempt(@JsonProperty("weight") JsonAdaptedWeight weight,
                                         @JsonProperty("distance") JsonAdaptedDistance distance,
                                         @JsonProperty("reps") JsonAdaptedRepetitions reps,
                                         @JsonProperty("time") JsonAdaptedTiming time,
                                         @JsonProperty("restTime") JsonAdaptedTiming restTime) {
        this.weight = weight;
        this.distance = distance;
        this.reps = reps;
        this.time = time;
        this.restTime = restTime;
    }

    /**
     * Converts a given {@code ExerciseSetAttempt} into this class for Jackson use.
     */
    public JsonAdaptedExerciseSetAttempt (ExerciseSetAttempt source) {
        this.weight = new JsonAdaptedWeight(source.getWeight());
        this.distance = new JsonAdaptedDistance(source.getDistance());
        this.reps = new JsonAdaptedRepetitions(source.getReps());
        this.time = new JsonAdaptedTiming(source.getTime());
        this.restTime = new JsonAdaptedTiming(source.getRestTime());
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code ExerciseSetAttempt} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted ExerciseSetAttempt.
     */
    public ExerciseSetAttempt toModelType() throws IllegalValueException {
        ExerciseWeight modelWeight = (ExerciseWeight) weight.toModelType();
        Distance modelDistance = (Distance) distance.toModelType();
        Repetitions modelReps = (Repetitions) reps.toModelType();
        Timing modelTiming = (Timing) time.toModelType();
        Timing modelRestTime = (Timing) time.toModelType();
        return new ExerciseSetAttempt(modelWeight, modelDistance, modelReps, modelTiming, modelRestTime);
    }
}
