package seedu.address.model.exercise;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SETS_FIVE;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.exercise.TypicalExercises.ABS_ROLLOUT;
import static seedu.address.testutil.exercise.TypicalExercises.SITUP;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.exercise.components.Exercise;
import seedu.address.model.exercise.components.UniqueExerciseList;
import seedu.address.model.exercise.exceptions.DuplicateExerciseException;
import seedu.address.model.exercise.exceptions.ExerciseNotFoundException;
import seedu.address.testutil.exercise.ExerciseBuilder;

public class UniqueExerciseListTest {

    private final UniqueExerciseList uniqueExerciseList = new UniqueExerciseList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueExerciseList.contains(null));
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniqueExerciseList.contains(ABS_ROLLOUT));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniqueExerciseList.add(ABS_ROLLOUT);
        assertTrue(uniqueExerciseList.contains(ABS_ROLLOUT));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniqueExerciseList.add(ABS_ROLLOUT);
        Exercise editedAbsRollout = new ExerciseBuilder(ABS_ROLLOUT)
                .withDetails(null, null, null, null, null, VALID_SETS_FIVE)
                .build();
        assertTrue(uniqueExerciseList.contains(editedAbsRollout));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueExerciseList.add(null));
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniqueExerciseList.add(ABS_ROLLOUT);
        assertThrows(DuplicateExerciseException.class, () -> uniqueExerciseList.add(ABS_ROLLOUT));
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueExerciseList.setPerson(null, ABS_ROLLOUT));
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueExerciseList.setPerson(ABS_ROLLOUT, null));
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        assertThrows(ExerciseNotFoundException.class, () -> uniqueExerciseList.setPerson(ABS_ROLLOUT, ABS_ROLLOUT));
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniqueExerciseList.add(ABS_ROLLOUT);
        uniqueExerciseList.setPerson(ABS_ROLLOUT, ABS_ROLLOUT);
        UniqueExerciseList expectedUniqueExerciseList = new UniqueExerciseList();
        expectedUniqueExerciseList.add(ABS_ROLLOUT);
        assertEquals(expectedUniqueExerciseList, uniqueExerciseList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniqueExerciseList.add(ABS_ROLLOUT);
        Exercise editedAlice = new ExerciseBuilder(ABS_ROLLOUT)
                .withDetails(null, null, null, null, null, VALID_SETS_FIVE)
                .build();
        uniqueExerciseList.setPerson(ABS_ROLLOUT, editedAlice);
        UniqueExerciseList expectedUniqueExerciseList = new UniqueExerciseList();
        expectedUniqueExerciseList.add(editedAlice);
        assertEquals(expectedUniqueExerciseList, uniqueExerciseList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniqueExerciseList.add(ABS_ROLLOUT);
        uniqueExerciseList.setPerson(ABS_ROLLOUT, SITUP);
        UniqueExerciseList expectedUniqueExerciseList = new UniqueExerciseList();
        expectedUniqueExerciseList.add(SITUP);
        assertEquals(expectedUniqueExerciseList, uniqueExerciseList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniqueExerciseList.add(ABS_ROLLOUT);
        uniqueExerciseList.add(SITUP);
        assertThrows(DuplicateExerciseException.class, () -> uniqueExerciseList.setPerson(ABS_ROLLOUT, SITUP));
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueExerciseList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(ExerciseNotFoundException.class, () -> uniqueExerciseList.remove(ABS_ROLLOUT));
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniqueExerciseList.add(ABS_ROLLOUT);
        uniqueExerciseList.remove(ABS_ROLLOUT);
        UniqueExerciseList expectedUniqueExerciseList = new UniqueExerciseList();
        assertEquals(expectedUniqueExerciseList, uniqueExerciseList);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueExerciseList.setExercises((UniqueExerciseList) null));
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        uniqueExerciseList.add(ABS_ROLLOUT);
        UniqueExerciseList expectedUniqueExerciseList = new UniqueExerciseList();
        expectedUniqueExerciseList.add(SITUP);
        uniqueExerciseList.setExercises(expectedUniqueExerciseList);
        assertEquals(expectedUniqueExerciseList, uniqueExerciseList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueExerciseList.setExercises((List<Exercise>) null));
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniqueExerciseList.add(ABS_ROLLOUT);
        List<Exercise> exerciseList = Collections.singletonList(SITUP);
        uniqueExerciseList.setExercises(exerciseList);
        UniqueExerciseList expectedUniqueExerciseList = new UniqueExerciseList();
        expectedUniqueExerciseList.add(SITUP);
        assertEquals(expectedUniqueExerciseList, uniqueExerciseList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Exercise> listWithDuplicateExercises = Arrays.asList(ABS_ROLLOUT, ABS_ROLLOUT);
        assertThrows(DuplicateExerciseException.class, () -> uniqueExerciseList
                .setExercises(listWithDuplicateExercises));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueExerciseList.asUnmodifiableObservableList().remove(0));
    }
}
