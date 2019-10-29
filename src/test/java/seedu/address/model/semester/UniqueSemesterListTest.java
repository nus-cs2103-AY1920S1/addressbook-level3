package seedu.address.model.semester;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalSemester.FULL_UNBLOCKED_SEMESTER_1;
import static seedu.address.testutil.TypicalSemester.FULL_UNBLOCKED_SEMESTER_2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.semester.exceptions.DuplicateSemesterException;
import seedu.address.model.semester.exceptions.SemesterNotFoundException;


/**
 * A test class for {@code UniqueSemesterListTest}.
 */
public class UniqueSemesterListTest {

    private final UniqueSemesterList uniqueSemesterList = new UniqueSemesterList();

    @Test
    public void contains_nullSemester_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueSemesterList.contains(null));
    }

    @Test
    public void contains_moduleNotInList_returnsFalse() {
        assertFalse(uniqueSemesterList.contains(FULL_UNBLOCKED_SEMESTER_1));
    }


    @Test
    public void contains_personInList_returnsTrue() {
        uniqueSemesterList.add(FULL_UNBLOCKED_SEMESTER_1);
        assertTrue(uniqueSemesterList.contains(FULL_UNBLOCKED_SEMESTER_1));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniqueSemesterList.add(FULL_UNBLOCKED_SEMESTER_1);
        assertTrue(uniqueSemesterList.contains(FULL_UNBLOCKED_SEMESTER_1));
    }

    @Test
    public void add_nullSemester_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueSemesterList.add(null));
    }

    @Test
    public void add_duplicateSemester_throwsDuplicateSemesterException() {
        uniqueSemesterList.add(FULL_UNBLOCKED_SEMESTER_1);
        assertThrows(DuplicateSemesterException.class, () -> uniqueSemesterList.add(FULL_UNBLOCKED_SEMESTER_1));
    }

    @Test
    public void setSemester_nullTargetSemester_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                uniqueSemesterList.setSemester(null, FULL_UNBLOCKED_SEMESTER_1));
    }

    @Test
    public void setSemester_nullEditedSemester_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                uniqueSemesterList.setSemester(FULL_UNBLOCKED_SEMESTER_1, null));
    }

    @Test
    public void setSemester_targetSemesterNotInList_throwsSemesterNotFoundException() {
        assertThrows(SemesterNotFoundException.class, () ->
                uniqueSemesterList.setSemester(FULL_UNBLOCKED_SEMESTER_1, FULL_UNBLOCKED_SEMESTER_1));
    }

    @Test
    public void setSemester_editedSemesterIsSameSemester_success() {
        uniqueSemesterList.add(FULL_UNBLOCKED_SEMESTER_1);
        uniqueSemesterList.setSemester(FULL_UNBLOCKED_SEMESTER_1, FULL_UNBLOCKED_SEMESTER_1);
        UniqueSemesterList expectedUniqueSemesterList = new UniqueSemesterList();
        expectedUniqueSemesterList.add(FULL_UNBLOCKED_SEMESTER_1);
        assertEquals(expectedUniqueSemesterList, uniqueSemesterList);
    }

    @Test
    public void setSemester_editedSemesterHasDifferentIdentity_success() {
        uniqueSemesterList.add(FULL_UNBLOCKED_SEMESTER_1);
        uniqueSemesterList.setSemester(FULL_UNBLOCKED_SEMESTER_1, FULL_UNBLOCKED_SEMESTER_2);
        UniqueSemesterList expectedUniqueSemesterList = new UniqueSemesterList();
        expectedUniqueSemesterList.add(FULL_UNBLOCKED_SEMESTER_2);
        assertEquals(expectedUniqueSemesterList, uniqueSemesterList);
    }

    @Test
    public void setSemester_editedSemesterHasNonUniqueIdentity_throwsDuplicateSemesterException() {
        uniqueSemesterList.add(FULL_UNBLOCKED_SEMESTER_1);
        uniqueSemesterList.add(FULL_UNBLOCKED_SEMESTER_2);
        assertThrows(DuplicateSemesterException.class, () ->
                uniqueSemesterList.setSemester(FULL_UNBLOCKED_SEMESTER_1, FULL_UNBLOCKED_SEMESTER_2));
    }

    @Test
    public void remove_nullSemester_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueSemesterList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsSemesterNotFoundException() {
        assertThrows(SemesterNotFoundException.class, () -> uniqueSemesterList.remove(FULL_UNBLOCKED_SEMESTER_1));
    }

    @Test
    public void remove_existingSemester_removesSemester() {
        uniqueSemesterList.add(FULL_UNBLOCKED_SEMESTER_1);
        uniqueSemesterList.remove(FULL_UNBLOCKED_SEMESTER_1);
        UniqueSemesterList expectedUniqueSemesterList = new UniqueSemesterList();
        assertEquals(expectedUniqueSemesterList, uniqueSemesterList);
    }

    @Test
    public void setSemesters_nullUniqueSemesterList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueSemesterList.setSemesters((UniqueSemesterList) null));
    }

    @Test
    public void setSemesters_uniqueSemesterList_replacesOwnListWithProvidedUniqueSemesterList() {
        uniqueSemesterList.add(FULL_UNBLOCKED_SEMESTER_1);
        UniqueSemesterList expectedUniqueSemesterList = new UniqueSemesterList();
        expectedUniqueSemesterList.add(FULL_UNBLOCKED_SEMESTER_2);
        uniqueSemesterList.setSemesters(expectedUniqueSemesterList);
        assertEquals(expectedUniqueSemesterList, uniqueSemesterList);
    }

    @Test
    public void setSemesters_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueSemesterList.setSemesters((List<Semester>) null));
    }

    @Test
    public void setSemesters_list_replacesOwnListWithProvidedList() {
        uniqueSemesterList.add(FULL_UNBLOCKED_SEMESTER_1);
        List<Semester> personList = Collections.singletonList(FULL_UNBLOCKED_SEMESTER_2);
        uniqueSemesterList.setSemesters(personList);
        UniqueSemesterList expectedUniqueSemesterList = new UniqueSemesterList();
        expectedUniqueSemesterList.add(FULL_UNBLOCKED_SEMESTER_2);
        assertEquals(expectedUniqueSemesterList, uniqueSemesterList);
    }

    @Test
    public void setSemesters_listWithDuplicateSemesters_throwsDuplicateSemesterException() {
        List<Semester> listWithDuplicateSemesters =
                Arrays.asList(FULL_UNBLOCKED_SEMESTER_1, FULL_UNBLOCKED_SEMESTER_1);
        assertThrows(DuplicateSemesterException.class, () ->
                uniqueSemesterList.setSemesters(listWithDuplicateSemesters));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () ->
                uniqueSemesterList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void clone_test() throws CloneNotSupportedException {
        uniqueSemesterList.add(FULL_UNBLOCKED_SEMESTER_1);
        uniqueSemesterList.add(FULL_UNBLOCKED_SEMESTER_2);
        UniqueSemesterList clone = uniqueSemesterList.clone();
        assertTrue(clone.contains(FULL_UNBLOCKED_SEMESTER_1));
        assertTrue(clone.contains(FULL_UNBLOCKED_SEMESTER_2));
    }
}
