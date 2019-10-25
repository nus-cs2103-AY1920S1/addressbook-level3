package seedu.address.model.studyplan;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalStudyPlans.SP_1;
import static seedu.address.testutil.TypicalStudyPlans.SP_2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.studyplan.exceptions.DuplicateStudyPlanException;
import seedu.address.model.studyplan.exceptions.StudyPlanNotFoundException;

/**
 * A test class for {@code UniqueStudyPlanListTest}.
 */
public class UniqueStudyPlanListTest {

    private final UniqueStudyPlanList uniqueStudyPlanList = new UniqueStudyPlanList();

    @Test
    public void contains_nullStudyPlan_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueStudyPlanList.contains(null));
    }

    @Test
    public void contains_moduleNotInList_returnsFalse() {
        assertFalse(uniqueStudyPlanList.contains(SP_1));
    }


    @Test
    public void contains_personInList_returnsTrue() {
        uniqueStudyPlanList.add(SP_1);
        assertTrue(uniqueStudyPlanList.contains(SP_1));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniqueStudyPlanList.add(SP_1);
        assertTrue(uniqueStudyPlanList.contains(SP_1));
    }

    @Test
    public void add_nullStudyPlan_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueStudyPlanList.add(null));
    }

    @Test
    public void add_duplicateStudyPlan_throwsDuplicateStudyPlanException() {
        uniqueStudyPlanList.add(SP_1);
        assertThrows(DuplicateStudyPlanException.class, () -> uniqueStudyPlanList.add(SP_1));
    }

    @Test
    public void setStudyPlan_nullTargetStudyPlan_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                uniqueStudyPlanList.setStudyPlan(null, SP_1));
    }

    @Test
    public void setStudyPlan_nullEditedStudyPlan_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                uniqueStudyPlanList.setStudyPlan(SP_1, null));
    }

    @Test
    public void setStudyPlan_targetStudyPlanNotInList_throwsStudyPlanNotFoundException() {
        assertThrows(StudyPlanNotFoundException.class, () ->
                uniqueStudyPlanList.setStudyPlan(SP_1, SP_1));
    }

    @Test
    public void setStudyPlan_editedStudyPlanIsSameStudyPlan_success() {
        uniqueStudyPlanList.add(SP_1);
        uniqueStudyPlanList.setStudyPlan(SP_1, SP_1);
        UniqueStudyPlanList expectedUniqueStudyPlanList = new UniqueStudyPlanList();
        expectedUniqueStudyPlanList.add(SP_1);
        assertEquals(expectedUniqueStudyPlanList, uniqueStudyPlanList);
    }

    @Test
    public void setStudyPlan_editedStudyPlanHasDifferentIdentity_success() {
        uniqueStudyPlanList.add(SP_1);
        uniqueStudyPlanList.setStudyPlan(SP_1, SP_2);
        UniqueStudyPlanList expectedUniqueStudyPlanList = new UniqueStudyPlanList();
        expectedUniqueStudyPlanList.add(SP_2);
        assertEquals(expectedUniqueStudyPlanList, uniqueStudyPlanList);
    }

    @Test
    public void setStudyPlan_editedStudyPlanHasNonUniqueIdentity_throwsDuplicateStudyPlanException() {
        uniqueStudyPlanList.add(SP_1);
        uniqueStudyPlanList.add(SP_2);
        assertThrows(DuplicateStudyPlanException.class, () ->
                uniqueStudyPlanList.setStudyPlan(SP_1, SP_2));
    }

    @Test
    public void remove_nullStudyPlan_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueStudyPlanList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsStudyPlanNotFoundException() {
        assertThrows(StudyPlanNotFoundException.class, () -> uniqueStudyPlanList.remove(SP_1));
    }

    @Test
    public void remove_existingStudyPlan_removesStudyPlan() {
        uniqueStudyPlanList.add(SP_1);
        uniqueStudyPlanList.remove(SP_1);
        UniqueStudyPlanList expectedUniqueStudyPlanList = new UniqueStudyPlanList();
        assertEquals(expectedUniqueStudyPlanList, uniqueStudyPlanList);
    }

    @Test
    public void setStudyPlans_nullUniqueStudyPlanList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueStudyPlanList.setStudyPlans((UniqueStudyPlanList) null));
    }

    @Test
    public void setStudyPlans_uniqueStudyPlanList_replacesOwnListWithProvidedUniqueStudyPlanList() {
        uniqueStudyPlanList.add(SP_1);
        UniqueStudyPlanList expectedUniqueStudyPlanList = new UniqueStudyPlanList();
        expectedUniqueStudyPlanList.add(SP_2);
        uniqueStudyPlanList.setStudyPlans(expectedUniqueStudyPlanList);
        assertEquals(expectedUniqueStudyPlanList, uniqueStudyPlanList);
    }

    @Test
    public void setStudyPlans_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueStudyPlanList.setStudyPlans((List<StudyPlan>) null));
    }

    @Test
    public void setStudyPlans_list_replacesOwnListWithProvidedList() {
        uniqueStudyPlanList.add(SP_1);
        List<StudyPlan> personList = Collections.singletonList(SP_2);
        uniqueStudyPlanList.setStudyPlans(personList);
        UniqueStudyPlanList expectedUniqueStudyPlanList = new UniqueStudyPlanList();
        expectedUniqueStudyPlanList.add(SP_2);
        assertEquals(expectedUniqueStudyPlanList, uniqueStudyPlanList);
    }

    @Test
    public void setStudyPlans_listWithDuplicateStudyPlans_throwsDuplicateStudyPlanException() {
        List<StudyPlan> listWithDuplicateStudyPlans =
                Arrays.asList(SP_1, SP_1);
        assertThrows(DuplicateStudyPlanException.class, () ->
                uniqueStudyPlanList.setStudyPlans(listWithDuplicateStudyPlans));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () ->
                uniqueStudyPlanList.asUnmodifiableObservableList().remove(0));
    }
}
