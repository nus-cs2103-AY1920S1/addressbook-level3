package seedu.address.model.studyplan;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalStudyPlans.ALICE;
import static seedu.address.testutil.TypicalStudyPlans.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.studyplan.exceptions.DuplicateStudyPlanException;
import seedu.address.model.studyplan.exceptions.StudyPlanNotFoundException;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.testutil.StudyPlanBuilder;

public class UniqueStudyPlanListTest {

    private final UniqueStudyPlanList uniqueStudyPlanList = new UniqueStudyPlanList();

    @Test
    public void contains_nullStudyPlan_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueStudyPlanList.contains(null));
    }

    @Test
    public void contains_studyPlanNotInList_returnsFalse() {
        assertFalse(uniqueStudyPlanList.contains(ALICE));
    }

    @Test
    public void contains_studyPlanInList_returnsTrue() {
        uniqueStudyPlanList.add(ALICE);
        assertTrue(uniqueStudyPlanList.contains(ALICE));
    }

    @Test
    public void contains_studyPlanWithSameIdentityFieldsInList_returnsTrue() {
        uniqueStudyPlanList.add(ALICE);
        StudyPlan editedAlice = new StudyPlanBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueStudyPlanList.contains(editedAlice));
    }

    @Test
    public void add_nullStudyPlan_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueStudyPlanList.add(null));
    }

    @Test
    public void add_duplicateStudyPlan_throwsDuplicateStudyPlanException() {
        uniqueStudyPlanList.add(ALICE);
        assertThrows(DuplicateStudyPlanException.class, () -> uniqueStudyPlanList.add(ALICE));
    }

    @Test
    public void setStudyPlan_nullTargetStudyPlan_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueStudyPlanList.setStudyPlan(null, ALICE));
    }

    @Test
    public void setStudyPlan_nullEditedStudyPlan_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueStudyPlanList.setStudyPlan(ALICE, null));
    }

    @Test
    public void setStudyPlan_targetStudyPlanNotInList_throwsStudyPlanNotFoundException() {
        assertThrows(StudyPlanNotFoundException.class, () -> uniqueStudyPlanList.setStudyPlan(ALICE, ALICE));
    }

    @Test
    public void setStudyPlan_editedStudyPlanIsSameStudyPlan_success() {
        uniqueStudyPlanList.add(ALICE);
        uniqueStudyPlanList.setStudyPlan(ALICE, ALICE);
        UniqueStudyPlanList expectedUniqueStudyPlanList = new UniqueStudyPlanList();
        expectedUniqueStudyPlanList.add(ALICE);
        assertEquals(expectedUniqueStudyPlanList, uniqueStudyPlanList);
    }

    @Test
    public void setStudyPlan_editedStudyPlanHasSameIdentity_success() {
        uniqueStudyPlanList.add(ALICE);
        StudyPlan editedAlice = new StudyPlanBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueStudyPlanList.setStudyPlan(ALICE, editedAlice);
        UniqueStudyPlanList expectedUniqueStudyPlanList = new UniqueStudyPlanList();
        expectedUniqueStudyPlanList.add(editedAlice);
        assertEquals(expectedUniqueStudyPlanList, uniqueStudyPlanList);
    }

    @Test
    public void setStudyPlan_editedStudyPlanHasDifferentIdentity_success() {
        uniqueStudyPlanList.add(ALICE);
        uniqueStudyPlanList.setStudyPlan(ALICE, BOB);
        UniqueStudyPlanList expectedUniqueStudyPlanList = new UniqueStudyPlanList();
        expectedUniqueStudyPlanList.add(BOB);
        assertEquals(expectedUniqueStudyPlanList, uniqueStudyPlanList);
    }

    @Test
    public void setStudyPlan_editedStudyPlanHasNonUniqueIdentity_throwsDuplicateStudyPlanException() {
        uniqueStudyPlanList.add(ALICE);
        uniqueStudyPlanList.add(BOB);
        assertThrows(DuplicateStudyPlanException.class, () -> uniqueStudyPlanList.setStudyPlan(ALICE, BOB));
    }

    @Test
    public void remove_nullStudyPlan_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueStudyPlanList.remove(null));
    }

    @Test
    public void remove_studyPlanDoesNotExist_throwsStudyPlanNotFoundException() {
        assertThrows(StudyPlanNotFoundException.class, () -> uniqueStudyPlanList.remove(ALICE));
    }

    @Test
    public void remove_existingStudyPlan_removesStudyPlan() {
        uniqueStudyPlanList.add(ALICE);
        uniqueStudyPlanList.remove(ALICE);
        UniqueStudyPlanList expectedUniqueStudyPlanList = new UniqueStudyPlanList();
        assertEquals(expectedUniqueStudyPlanList, uniqueStudyPlanList);
    }

    @Test
    public void setStudyPlans_nullUniqueStudyPlanList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueStudyPlanList.setStudyPlans((UniqueStudyPlanList) null));
    }

    @Test
    public void setStudyPlans_uniqueStudyPlanList_replacesOwnListWithProvidedUniqueStudyPlanList() {
        uniqueStudyPlanList.add(ALICE);
        UniqueStudyPlanList expectedUniqueStudyPlanList = new UniqueStudyPlanList();
        expectedUniqueStudyPlanList.add(BOB);
        uniqueStudyPlanList.setStudyPlans(expectedUniqueStudyPlanList);
        assertEquals(expectedUniqueStudyPlanList, uniqueStudyPlanList);
    }

    @Test
    public void setStudyPlans_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueStudyPlanList.setStudyPlans((List<StudyPlan>) null));
    }

    @Test
    public void setStudyPlans_list_replacesOwnListWithProvidedList() {
        uniqueStudyPlanList.add(ALICE);
        List<StudyPlan> studyPlanList = Collections.singletonList(BOB);
        uniqueStudyPlanList.setStudyPlans(studyPlanList);
        UniqueStudyPlanList expectedUniqueStudyPlanList = new UniqueStudyPlanList();
        expectedUniqueStudyPlanList.add(BOB);
        assertEquals(expectedUniqueStudyPlanList, uniqueStudyPlanList);
    }

    @Test
    public void setStudyPlans_listWithDuplicateStudyPlans_throwsDuplicateStudyPlanException() {
        List<StudyPlan> listWithDuplicateStudyPlans = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicateStudyPlanException.class, () -> uniqueStudyPlanList.setStudyPlans(listWithDuplicateStudyPlans));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueStudyPlanList.asUnmodifiableObservableList().remove(0));
    }
}
