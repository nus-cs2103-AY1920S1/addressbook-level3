package dukecooks.storage.workout.exercise;

import com.fasterxml.jackson.annotation.JsonCreator;
import dukecooks.commons.exceptions.IllegalValueException;
import dukecooks.model.workout.exercise.ExerciseSetAttempt;
import dukecooks.model.workout.exercise.details.Sets;
import dukecooks.model.workout.history.ExerciseRun;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JsonAdaptedExerciseRun {

    final private LocalDateTime timeStarted;
    final private LocalDateTime timeEnded;
    final private JsonAdaptedSets setsAttempted;
    final private JsonAdaptedSets setsCompleted;
    final private List<JsonAdaptedExerciseSetAttempt> exerciseSetAttempts;
    final private Duration totalTimeTaken;

    /**
     * Constructs a {@code JsonAdaptedExerciseRun} with the given parameters.
     */
    @JsonCreator
    public JsonAdaptedExerciseRun(LocalDateTime timeStarted, LocalDateTime timeEnded,
                                  JsonAdaptedSets setsAttempted, JsonAdaptedSets setsCompleted,
                                  List<JsonAdaptedExerciseSetAttempt> exerciseSetAttempts,
                                  Duration totalTimeTaken) {
        this.timeStarted = timeStarted;
        this.timeEnded = timeEnded;
        this.setsAttempted = setsAttempted;
        this.setsCompleted = setsCompleted;
        this.exerciseSetAttempts = exerciseSetAttempts;
        this.totalTimeTaken = totalTimeTaken;
    }

    /**
     * Converts a given {@code JsonAdaptedExerciseRun} into this class for Jackson use.
     */
    public JsonAdaptedExerciseRun (ExerciseRun source) {
        this.timeStarted = source.getTimeStarted();
        this.timeEnded = source.getTimeEnded();
        this.setsAttempted = new JsonAdaptedSets(source.getSetsAttempted());
        this.setsCompleted = new JsonAdaptedSets(source.getSetsCompleted());
        this.totalTimeTaken = source.getTotalTimeTaken();
        this.exerciseSetAttempts = source.getExerciseSetAttempts().stream().map(attempt ->
                new JsonAdaptedExerciseSetAttempt(attempt)).collect(Collectors.toList());
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code ExerciseRun} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted ExerciseRun.
     */
    public ExerciseRun toModelType() throws IllegalValueException {
        Sets modelSetsAttempted = (Sets) setsAttempted.toModelType();
        Sets modelSetsCompleted = (Sets) setsCompleted.toModelType();
        ArrayList<ExerciseSetAttempt> modelExerciseSetAttempts = new ArrayList<>();
        for (JsonAdaptedExerciseSetAttempt jsonAttempt : exerciseSetAttempts) {
            modelExerciseSetAttempts.add(jsonAttempt.toModelType());
        }
        return new ExerciseRun(timeStarted, timeEnded, modelSetsAttempted, modelSetsCompleted,
                modelExerciseSetAttempts);
    }
}
