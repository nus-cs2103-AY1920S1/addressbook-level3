package dukecooks.storage.workout;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import dukecooks.commons.exceptions.IllegalValueException;
import dukecooks.model.workout.history.ExerciseRun;
import dukecooks.model.workout.history.WorkoutRun;
import dukecooks.storage.workout.exercise.JsonAdaptedExerciseRun;

/**
 * Jackson friendly version of WorkoutRun.
 */
public class JsonAdaptedWorkoutRun {

    private LocalDateTime timeStarted;
    private final LocalDateTime timeEnded;
    private final Integer totalExercisesCompleted;
    private final List<JsonAdaptedExerciseRun> exercisesRan;
    private final Duration totalTimeTaken;

    /**
     * Constructs a {@code JsonAdaptedWorkoutRun} with the given parameters.
     */
    @JsonCreator
    public JsonAdaptedWorkoutRun(@JsonProperty("timeStarted") LocalDateTime timeStarted,
                                 @JsonProperty("timeEnded") LocalDateTime timeEnded,
                                 @JsonProperty("totalExercisesCompleted") Integer totalExercisesCompleted,
                                 @JsonProperty("exercisesRan") List<JsonAdaptedExerciseRun> exercisesRan,
                                 @JsonProperty("totalTime") Duration totalTimeTaken) {
        this.timeStarted = timeStarted;
        this.timeEnded = timeEnded;
        this.totalExercisesCompleted = totalExercisesCompleted;
        this.exercisesRan = exercisesRan;
        this.totalTimeTaken = totalTimeTaken;
    }

    /**
     * Converts a given {@code JsonAdaptedWorkoutRun} into this class for Jackson use.
     */
    public JsonAdaptedWorkoutRun (WorkoutRun source) {
        this.timeStarted = source.getTimeStarted();
        this.timeEnded = source.getTimeEnded();
        this.totalExercisesCompleted = source.getTotalExercisesCompleted();
        this.totalTimeTaken = source.getTotalTimeTaken();
        this.exercisesRan = source.getExercisesRan().stream().map(run -> new JsonAdaptedExerciseRun(run))
                .collect(Collectors.toList());
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code WorkoutRun} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted WorkoutRun.
     */
    public WorkoutRun toModelType() throws IllegalValueException {
        ArrayList<ExerciseRun> modelExercisesRan = new ArrayList<>();
        for (JsonAdaptedExerciseRun jsonRun : exercisesRan) {
            modelExercisesRan.add(jsonRun.toModelType());
        }
        return new WorkoutRun(timeStarted, timeEnded, totalExercisesCompleted, modelExercisesRan);
    }

}
