package seedu.address.model.exercise;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.exercise.TypicalExercises.ABS_ROLLOUT;
import static seedu.address.testutil.exercise.TypicalExercises.getTypicalWorkoutPlanner;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.model.exercise.components.Exercise;
import seedu.address.model.exercise.exceptions.DuplicateExerciseException;
import seedu.address.testutil.exercise.ExerciseBuilder;

public class WorkoutPlannerTest {

    private final WorkoutPlanner workoutPlanner = new WorkoutPlanner();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), workoutPlanner.getExerciseList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> workoutPlanner.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyDukeCooks_replacesData() {
        WorkoutPlanner newData = getTypicalWorkoutPlanner();
        workoutPlanner.resetData(newData);
        assertEquals(newData, workoutPlanner);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Exercise editedAbsRollout = new ExerciseBuilder(ABS_ROLLOUT)
                .withDetails(null, null, null, null, 99, 99)
                .build();
        List<Exercise> newExercises = Arrays.asList(ABS_ROLLOUT, editedAbsRollout);
        WorkoutPlannerStub newData = new WorkoutPlannerStub(newExercises);

        assertThrows(DuplicateExerciseException.class, () -> workoutPlanner.resetData(newData));
    }

    @Test
    public void hasPerson_nullExercise_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> workoutPlanner.hasExercise(null));
    }

    @Test
    public void hasExercise_exerciseNotInDukeCooks_returnsFalse() {
        assertFalse(workoutPlanner.hasExercise(ABS_ROLLOUT));
    }

    @Test
    public void hasExercise_exerciseInDukeCooks_returnsTrue() {
        workoutPlanner.addExercise(ABS_ROLLOUT);
        assertTrue(workoutPlanner.hasExercise(ABS_ROLLOUT));
    }

    @Test
    public void hasExercise_exerciseWithSameIdentityFieldsInDukeCooks_returnsTrue() {
        workoutPlanner.addExercise(ABS_ROLLOUT);
        Exercise editedAbsRollout = new ExerciseBuilder(ABS_ROLLOUT)
                .withDetails(null, null, null, null, 99, 99)
                .build();
        assertTrue(workoutPlanner.hasExercise(editedAbsRollout));
    }

    @Test
    public void getExerciseList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> workoutPlanner.getExerciseList().remove(0));
    }

    /**
     * A stub ReadOnlyDukeCooks whose persons list can violate interface constraints.
     */
    private static class WorkoutPlannerStub implements ReadOnlyWorkoutPlanner {
        private final ObservableList<Exercise> exercises = FXCollections.observableArrayList();

        WorkoutPlannerStub(Collection<Exercise> exercises) {
            this.exercises.setAll(exercises);
        }

        @Override
        public ObservableList<Exercise> getExerciseList() {
            return exercises;
        }
    }

}
