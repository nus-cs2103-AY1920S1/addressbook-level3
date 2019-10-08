package seedu.exercise.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.exercise.logic.commands.CommandTestUtil.VALID_MUSCLE_AEROBICS;
import static seedu.exercise.logic.commands.CommandTestUtil.VALID_QUANTITY_BASKETBALL;
import static seedu.exercise.testutil.Assert.assertThrows;
import static seedu.exercise.testutil.TypicalExercises.WALK;
import static seedu.exercise.testutil.TypicalExercises.getTypicalExerciseBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.exercise.model.exercise.Exercise;
import seedu.exercise.model.exercise.exceptions.DuplicateExerciseException;
import seedu.exercise.testutil.ExerciseBuilder;

public class ExerciseBookTest {

    private final ExerciseBook exerciseBook = new ExerciseBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), exerciseBook.getExerciseList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> exerciseBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyExerciseBook_replacesData() {
        ExerciseBook newData = getTypicalExerciseBook();
        exerciseBook.resetData(newData);
        assertEquals(newData, exerciseBook);
    }

    @Test
    public void resetData_withDuplicateExercises_throwsDuplicateExerciseException() {
        // Two exercises with the same identity fields
        Exercise editedWalk = new ExerciseBuilder(WALK).withQuantity(VALID_QUANTITY_BASKETBALL)
            .withMuscles(VALID_MUSCLE_AEROBICS).build();
        List<Exercise> newExercises = Arrays.asList(WALK, editedWalk);
        ExerciseBookStub newData = new ExerciseBookStub(newExercises);

        assertThrows(DuplicateExerciseException.class, () -> exerciseBook.resetData(newData));
    }

    @Test
    public void hasExercise_nullExercise_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> exerciseBook.hasExercise(null));
    }

    @Test
    public void hasExercise_exerciseNotInExerciseBook_returnsFalse() {
        assertFalse(exerciseBook.hasExercise(WALK));
    }

    @Test
    public void hasExercise_exerciseInExerciseBook_returnsTrue() {
        exerciseBook.addExercise(WALK);
        assertTrue(exerciseBook.hasExercise(WALK));
    }

    @Test
    public void hasExercise_exerciseWithSameIdentityFieldsInExerciseBook_returnsTrue() {
        exerciseBook.addExercise(WALK);
        Exercise editedWalk = new ExerciseBuilder(WALK).withQuantity(VALID_QUANTITY_BASKETBALL)
            .withMuscles(VALID_MUSCLE_AEROBICS).build();
        assertTrue(exerciseBook.hasExercise(editedWalk));
    }

    @Test
    public void getExerciseList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> exerciseBook.getExerciseList().remove(0));
    }

    /**
     * A stub ReadOnlyExerciseBook whose exercises list can violate interface constraints.
     */
    private static class ExerciseBookStub implements ReadOnlyExerciseBook {
        private final ObservableList<Exercise> exercises = FXCollections.observableArrayList();

        ExerciseBookStub(Collection<Exercise> exercises) {
            this.exercises.setAll(exercises);
        }

        @Override
        public ObservableList<Exercise> getExerciseList() {
            return exercises;
        }
    }

}
