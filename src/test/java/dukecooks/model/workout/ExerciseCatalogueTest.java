package dukecooks.model.workout;

import static dukecooks.testutil.exercise.TypicalExercises.ABS_ROLLOUT;
import static dukecooks.testutil.exercise.TypicalExercises.getTypicalWorkoutPlanner;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import dukecooks.model.workout.exercise.ExerciseCatalogue;
import dukecooks.model.workout.exercise.ReadOnlyExerciseCatalogue;
import dukecooks.model.workout.exercise.components.Exercise;
import dukecooks.model.workout.exercise.exceptions.DuplicateExerciseException;
import dukecooks.testutil.Assert;
import dukecooks.testutil.exercise.ExerciseBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ExerciseCatalogueTest {

    private final ExerciseCatalogue exerciseCatalogue = new ExerciseCatalogue();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), exerciseCatalogue.getExerciseList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> exerciseCatalogue.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyDukeCooks_replacesData() {
        ExerciseCatalogue newData = getTypicalWorkoutPlanner();
        exerciseCatalogue.resetData(newData);
        assertEquals(newData, exerciseCatalogue);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Exercise editedAbsRollout = new ExerciseBuilder(ABS_ROLLOUT)
                .withDetails(null, null, null, null, 99, 99)
                .build();
        List<Exercise> newExercises = Arrays.asList(ABS_ROLLOUT, editedAbsRollout);
        ExerciseCatalogueStub newData = new ExerciseCatalogueStub(newExercises);

        Assert.assertThrows(DuplicateExerciseException.class, () -> exerciseCatalogue.resetData(newData));
    }

    @Test
    public void hasPerson_nullExercise_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> exerciseCatalogue.hasExercise(null));
    }

    @Test
    public void hasExercise_exerciseNotInDukeCooks_returnsFalse() {
        assertFalse(exerciseCatalogue.hasExercise(ABS_ROLLOUT));
    }

    @Test
    public void hasExercise_exerciseInDukeCooks_returnsTrue() {
        exerciseCatalogue.addExercise(ABS_ROLLOUT);
        assertTrue(exerciseCatalogue.hasExercise(ABS_ROLLOUT));
    }

    @Test
    public void hasExercise_exerciseWithSameIdentityFieldsInDukeCooks_returnsTrue() {
        exerciseCatalogue.addExercise(ABS_ROLLOUT);
        Exercise editedAbsRollout = new ExerciseBuilder(ABS_ROLLOUT)
                .withDetails(null, null, null, null, 99, 99)
                .build();
        assertTrue(exerciseCatalogue.hasExercise(editedAbsRollout));
    }

    @Test
    public void getExerciseList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, () -> exerciseCatalogue.getExerciseList().remove(0));
    }

    /**
     * A stub ReadOnlyDukeCooks whose persons list can violate interface constraints.
     */
    private static class ExerciseCatalogueStub implements ReadOnlyExerciseCatalogue {
        private final ObservableList<Exercise> exercises = FXCollections.observableArrayList();

        ExerciseCatalogueStub(Collection<Exercise> exercises) {
            this.exercises.setAll(exercises);
        }

        @Override
        public ObservableList<Exercise> getExerciseList() {
            return exercises;
        }
    }

}
