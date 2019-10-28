package seedu.exercise.model.book;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.exercise.testutil.Assert.assertThrows;
import static seedu.exercise.testutil.CommonTestData.VALID_MUSCLE_AEROBICS;
import static seedu.exercise.testutil.CommonTestData.VALID_QUANTITY_BASKETBALL;
import static seedu.exercise.testutil.typicalutil.TypicalExercises.WALK;
import static seedu.exercise.testutil.typicalutil.TypicalExercises.getTypicalExerciseBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.exercise.model.ReadOnlyResourceBook;
import seedu.exercise.model.exceptions.DuplicateResourceException;
import seedu.exercise.model.resource.Exercise;
import seedu.exercise.testutil.builder.ExerciseBuilder;

public class ExerciseBookTest {

    private final ReadOnlyResourceBook<Exercise> exerciseBook = new ReadOnlyResourceBook<>();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), exerciseBook.getResourceList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> exerciseBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyExerciseBook_replacesData() {
        ReadOnlyResourceBook<Exercise> newData = getTypicalExerciseBook();
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
        assertThrows(DuplicateResourceException.class, () -> exerciseBook.resetData(newData));
    }

    @Test
    public void hasExercise_nullExercise_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> exerciseBook.hasResource(null));
    }

    @Test
    public void hasExercise_exerciseNotInExerciseBook_returnsFalse() {
        assertFalse(exerciseBook.hasResource(WALK));
    }

    @Test
    public void hasExercise_exerciseInExerciseBook_returnsTrue() {
        exerciseBook.addResource(WALK);
        assertTrue(exerciseBook.hasResource(WALK));
    }

    @Test
    public void hasExercise_exerciseWithSameIdentityFieldsInExerciseBook_returnsTrue() {
        exerciseBook.addResource(WALK);
        Exercise editedWalk = new ExerciseBuilder(WALK).withQuantity(VALID_QUANTITY_BASKETBALL)
            .withMuscles(VALID_MUSCLE_AEROBICS).build();
        assertTrue(exerciseBook.hasResource(editedWalk));
    }

    @Test
    public void getExerciseList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> exerciseBook.getResourceList().remove(0));
    }

    /**
     * A stub {@code ReadOnlyResourceBook<Exercise>} whose exercises list can violate interface constraints.
     */
    private static class ExerciseBookStub extends ReadOnlyResourceBook<Exercise> {
        private final ObservableList<Exercise> exercises = FXCollections.observableArrayList();

        ExerciseBookStub(Collection<Exercise> exercises) {
            this.exercises.setAll(exercises);
        }

        public ObservableList<Exercise> getResourceList() {
            return exercises;
        }
    }

}
