package seedu.tarence.model.tutorial;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tarence.testutil.Assert.assertThrows;
import static seedu.tarence.testutil.TypicalTutorials.CS1020_LAB01;
import static seedu.tarence.testutil.TypicalTutorials.CS1231_TUT10;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.tarence.model.builder.TutorialBuilder;
import seedu.tarence.model.tutorial.exceptions.DuplicateTutorialException;
import seedu.tarence.model.tutorial.exceptions.TutorialNotFoundException;

public class UniqueTutorialListTest {

    private final UniqueTutorialList uniqueTutorialList = new UniqueTutorialList();

    @Test
    public void contains_nullTutorial_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTutorialList.contains(null));
    }

    @Test
    public void contains_tutorialNotInList_returnsFalse() {
        assertFalse(uniqueTutorialList.contains(CS1020_LAB01));
    }

    @Test
    public void contains_tutorialInList_returnsTrue() {
        uniqueTutorialList.add(CS1020_LAB01);
        assertTrue(uniqueTutorialList.contains(CS1020_LAB01));
    }

    @Test
    public void contains_tutorialWithSameIdentityFieldsInList_returnsTrue() {
        uniqueTutorialList.add(CS1020_LAB01);
        Tutorial editedCs1020Lab01 = new TutorialBuilder(CS1020_LAB01).build();
        assertTrue(uniqueTutorialList.contains(editedCs1020Lab01));
    }

    @Test
    public void add_nullTutorial_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTutorialList.add(null));
    }

    @Test
    public void add_duplicateTutorial_throwsDuplicateTutorialException() {
        uniqueTutorialList.add(CS1020_LAB01);
        assertThrows(DuplicateTutorialException.class, () -> uniqueTutorialList.add(CS1020_LAB01));
    }

    @Test
    public void setTutorial_nullTargetTutorial_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTutorialList.setTutorial(null, CS1020_LAB01));
    }

    @Test
    public void setTutorial_nullEditedTutorial_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTutorialList.setTutorial(CS1020_LAB01, null));
    }

    @Test
    public void setTutorial_targetTutorialNotInList_throwsTutorialNotFoundException() {
        assertThrows(TutorialNotFoundException.class, () -> uniqueTutorialList.setTutorial(CS1020_LAB01, CS1020_LAB01));
    }

    @Test
    public void setTutorial_editedTutorialIsSameTutorial_success() {
        uniqueTutorialList.add(CS1020_LAB01);
        uniqueTutorialList.setTutorial(CS1020_LAB01, CS1020_LAB01);
        UniqueTutorialList expectedUniqueTutorialList = new UniqueTutorialList();
        expectedUniqueTutorialList.add(CS1020_LAB01);
        assertEquals(expectedUniqueTutorialList, uniqueTutorialList);
    }

    @Test
    public void setTutorial_editedTutorialHasSameIdentity_success() {
        uniqueTutorialList.add(CS1020_LAB01);
        Tutorial editedCs1020Lab01 = new TutorialBuilder(CS1020_LAB01).build();
        uniqueTutorialList.setTutorial(CS1020_LAB01, editedCs1020Lab01);
        UniqueTutorialList expectedUniqueTutorialList = new UniqueTutorialList();
        expectedUniqueTutorialList.add(editedCs1020Lab01);
        assertEquals(expectedUniqueTutorialList, uniqueTutorialList);
    }

    @Test
    public void setTutorial_editedHasDifferentIdentity_success() {
        uniqueTutorialList.add(CS1020_LAB01);
        uniqueTutorialList.setTutorial(CS1020_LAB01, CS1231_TUT10);
        UniqueTutorialList expectedUniqueTutorialList = new UniqueTutorialList();
        expectedUniqueTutorialList.add(CS1231_TUT10);
        assertEquals(expectedUniqueTutorialList, uniqueTutorialList);
    }

    @Test
    public void setTutorial_editedTutorialHasNonUniqueIdentity_throwsDuplicateTutorialException() {
        uniqueTutorialList.add(CS1020_LAB01);
        uniqueTutorialList.add(CS1231_TUT10);
        assertThrows(DuplicateTutorialException.class, ()
            -> uniqueTutorialList.setTutorial(CS1020_LAB01, CS1231_TUT10));
    }

    @Test
    public void remove_nullTutorial_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTutorialList.remove(null));
    }

    @Test
    public void remove_tutorialDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(TutorialNotFoundException.class, () -> uniqueTutorialList.remove(CS1020_LAB01));
    }

    @Test
    public void remove_existingTutorial_removesTutorial() {
        uniqueTutorialList.add(CS1020_LAB01);
        uniqueTutorialList.remove(CS1020_LAB01);
        UniqueTutorialList expectedUniqueTutorialList = new UniqueTutorialList();
        assertEquals(expectedUniqueTutorialList, uniqueTutorialList);
    }

    @Test
    public void setTutorials_nullUniqueTutorialList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTutorialList.setTutorials((UniqueTutorialList) null));
    }

    @Test
    public void setTutorials_uniqueTutorialList_replacesOwnListWithProvidedUniqueTutorialList() {
        uniqueTutorialList.add(CS1020_LAB01);
        UniqueTutorialList expectedUniqueTutorialList = new UniqueTutorialList();
        expectedUniqueTutorialList.add(CS1231_TUT10);
        uniqueTutorialList.setTutorials(expectedUniqueTutorialList);
        assertEquals(expectedUniqueTutorialList, uniqueTutorialList);
    }

    @Test
    public void setTutorials_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTutorialList.setTutorials((List<Tutorial>) null));
    }

    @Test
    public void setTutorials_list_replacesOwnListWithProvidedList() {
        uniqueTutorialList.add(CS1020_LAB01);
        List<Tutorial> tutorialList = Collections.singletonList(CS1231_TUT10);
        uniqueTutorialList.setTutorials(tutorialList);
        UniqueTutorialList expectedUniquePersonList = new UniqueTutorialList();
        expectedUniquePersonList.add(CS1231_TUT10);
        assertEquals(expectedUniquePersonList, uniqueTutorialList);
    }

    @Test
    public void setTutorials_listWithDuplicateTutorials_throwsDuplicateTutorialException() {
        List<Tutorial> listWithDuplicateTutorials = Arrays.asList(CS1020_LAB01, CS1020_LAB01);
        assertThrows(DuplicateTutorialException.class, ()
            -> uniqueTutorialList.setTutorials(listWithDuplicateTutorials));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueTutorialList.asUnmodifiableObservableList().remove(0));
    }
}
