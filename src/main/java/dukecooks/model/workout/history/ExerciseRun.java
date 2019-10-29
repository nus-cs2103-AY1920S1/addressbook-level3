package dukecooks.model.workout.history;

import dukecooks.model.workout.exercise.details.Sets;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Represents one run of the exercise in a workout.
 */
public interface ExerciseRun {

    final LocalDateTime timeStarted;
    final LocalDateTime timeEnded;
    final Sets setsAttempted;
    final Sets setsCompleted;
    final Duration totalTimeTaken;
    
}
