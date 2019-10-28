package seedu.exercise.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.exercise.testutil.Assert.assertThrows;
import static seedu.exercise.testutil.CommonTestData.VALID_MUSCLE_AEROBICS;
import static seedu.exercise.testutil.CommonTestData.VALID_QUANTITY_BASKETBALL;
import static seedu.exercise.testutil.typicalutil.TypicalExercises.BASKETBALL;
import static seedu.exercise.testutil.typicalutil.TypicalExercises.WALK;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.exercise.model.exceptions.DuplicateResourceException;
import seedu.exercise.model.exceptions.ResourceNotFoundException;
import seedu.exercise.model.resource.Exercise;
import seedu.exercise.testutil.builder.ExerciseBuilder;

public class UniqueExerciseListTest {

    private final UniqueResourceList<Exercise> uniqueExerciseList = new UniqueResourceList<>();

    @Test
    public void contains_nullExercise_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueExerciseList.contains(null));
    }

    @Test
    public void contains_exerciseNotInList_returnsFalse() {
        assertFalse(uniqueExerciseList.contains(WALK));
    }

    @Test
    public void contains_exerciseInList_returnsTrue() {
        uniqueExerciseList.add(WALK);
        assertTrue(uniqueExerciseList.contains(WALK));
    }

    @Test
    public void contains_exerciseWithSameIdentityFieldsInList_returnsTrue() {
        uniqueExerciseList.add(WALK);
        Exercise editedExercise = new ExerciseBuilder(WALK).withQuantity(VALID_QUANTITY_BASKETBALL)
            .withMuscles(VALID_MUSCLE_AEROBICS).build();
        assertTrue(uniqueExerciseList.contains(editedExercise));
    }

    @Test
    public void add_nullExercise_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueExerciseList.add(null));
    }

    @Test
    public void add_duplicateExercise_throwsDuplicateExerciseException() {
        uniqueExerciseList.add(WALK);
        assertThrows(DuplicateResourceException.class, () -> uniqueExerciseList.add(WALK));
    }

    @Test
    public void setExercise_nullTargetExercise_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueExerciseList.set(null, WALK));
    }

    @Test
    public void setExercise_nullEditedExercise_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueExerciseList.set(WALK, null));
    }

    @Test
    public void setExercise_targetExerciseNotInList_throwsExerciseNotFoundException() {
        assertThrows(ResourceNotFoundException.class, () -> uniqueExerciseList.set(WALK, WALK));
    }

    @Test
    public void setExercise_editedExerciseIsSameExercise_success() {
        uniqueExerciseList.add(WALK);
        uniqueExerciseList.set(WALK, WALK);
        UniqueResourceList<Exercise> expectedUniqueExerciseList = new UniqueResourceList<>();
        expectedUniqueExerciseList.add(WALK);
        assertEquals(expectedUniqueExerciseList, uniqueExerciseList);
    }

    @Test
    public void setExercise_editedExerciseHasSameIdentity_success() {
        uniqueExerciseList.add(WALK);
        Exercise editedWalk = new ExerciseBuilder(WALK).withQuantity(VALID_QUANTITY_BASKETBALL)
            .withMuscles(VALID_MUSCLE_AEROBICS).build();
        uniqueExerciseList.set(WALK, editedWalk);
        UniqueResourceList<Exercise> expectedUniqueExerciseList = new UniqueResourceList<>();
        expectedUniqueExerciseList.add(editedWalk);
        assertEquals(expectedUniqueExerciseList, uniqueExerciseList);
    }

    @Test
    public void setExercise_editedExerciseHasDifferentIdentity_success() {
        uniqueExerciseList.add(WALK);
        uniqueExerciseList.set(WALK, BASKETBALL);
        UniqueResourceList<Exercise> expectedUniqueExerciseList = new UniqueResourceList<>();
        expectedUniqueExerciseList.add(BASKETBALL);
        assertEquals(expectedUniqueExerciseList, uniqueExerciseList);
    }

    @Test
    public void setExercise_editedExerciseHasNonUniqueIdentity_throwsDuplicateExerciseException() {
        uniqueExerciseList.add(WALK);
        uniqueExerciseList.add(BASKETBALL);
        assertThrows(DuplicateResourceException.class, () -> uniqueExerciseList.set(WALK, BASKETBALL));
    }

    @Test
    public void remove_nullExercise_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueExerciseList.remove(null));
    }

    @Test
    public void remove_exerciseDoesNotExist_throwsExerciseNotFoundException() {
        assertThrows(ResourceNotFoundException.class, () -> uniqueExerciseList.remove(WALK));
    }

    @Test
    public void remove_existingExercise_removesExercise() {
        uniqueExerciseList.add(WALK);
        uniqueExerciseList.remove(WALK);
        UniqueResourceList<Exercise> expectedUniqueExerciseList = new UniqueResourceList<>();
        assertEquals(expectedUniqueExerciseList, uniqueExerciseList);
    }

    @Test
    public void setExercises_nullUniqueExerciseList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueExerciseList.setAll((UniqueResourceList<Exercise>) null));
    }

    @Test
    public void setExercises_uniqueExerciseList_replacesOwnListWithProvidedUniqueExerciseList() {
        uniqueExerciseList.add(WALK);
        UniqueResourceList<Exercise> expectedUniqueExerciseList = new UniqueResourceList<>();
        expectedUniqueExerciseList.add(BASKETBALL);
        uniqueExerciseList.setAll(expectedUniqueExerciseList);
        assertEquals(expectedUniqueExerciseList, uniqueExerciseList);
    }

    @Test
    public void setExercises_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueExerciseList.setAll((List<Exercise>) null));
    }

    @Test
    public void setExercises_list_replacesOwnListWithProvidedList() {
        uniqueExerciseList.add(WALK);
        List<Exercise> exerciseList = Collections.singletonList(BASKETBALL);
        uniqueExerciseList.setAll(exerciseList);
        UniqueResourceList<Exercise> expectedUniqueExerciseList = new UniqueResourceList<>();
        expectedUniqueExerciseList.add(BASKETBALL);
        assertEquals(expectedUniqueExerciseList, uniqueExerciseList);
    }

    @Test
    public void setExercises_listWithDuplicateExercises_throwsDuplicateExerciseException() {
        List<Exercise> listWithDuplicateExercises = Arrays.asList(WALK, WALK);
        assertThrows(DuplicateResourceException.class, () ->
            uniqueExerciseList.setAll(listWithDuplicateExercises));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueExerciseList.asUnmodifiableObservableList().remove(0));
    }
}
