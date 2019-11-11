package seedu.address.model.student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.student.exceptions.DuplicateStudentException;
import seedu.address.model.student.exceptions.StudentNotFoundException;
import seedu.address.testutil.student.StudentBuilder;
import seedu.address.testutil.student.TypicalStudents;

/**
 * Test for the UniqueStudentList model.
 */
public class UniqueStudentListTest {

    private final UniqueStudentList uniqueStudentList = new UniqueStudentList();

    @Test
    public void contains_nullStudent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueStudentList.contains(null));
    }

    @Test
    public void contains_studentNotInList_returnsFalse() {
        assertFalse(uniqueStudentList.contains(TypicalStudents.STUDENT_ONE));
    }

    @Test
    public void contains_studentInList_returnsTrue() {
        uniqueStudentList.add(TypicalStudents.STUDENT_ONE);
        assertTrue(uniqueStudentList.contains(TypicalStudents.STUDENT_ONE));
    }

    @Test
    public void contains_studentWithSameIdentityFieldsInList_returnsTrue() {
        uniqueStudentList.add(TypicalStudents.STUDENT_FOUR);
        Student editedStudent = new StudentBuilder(TypicalStudents.STUDENT_FOUR)
                .withMark(true).withTags(TypicalStudents.TAG_SET_FOUR)
                .build();
        assertTrue(uniqueStudentList.contains(editedStudent));
    }

    @Test
    public void add_nullStudent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueStudentList.add(null));
    }

    @Test
    public void add_duplicateStudent_throwsDuplicateStudentException() {
        uniqueStudentList.add(TypicalStudents.STUDENT_THREE);
        assertThrows(DuplicateStudentException.class, () -> uniqueStudentList.add(TypicalStudents.STUDENT_THREE));
    }

    @Test
    public void setStudent_nullTargetPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueStudentList
                .setStudent((Student) null, TypicalStudents.STUDENT_TWO));
    }

    @Test
    public void setStudent_nullEditedStudent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueStudentList
                .setStudent(TypicalStudents.STUDENT_ONE, null));
    }

    @Test
    public void setStudent_targetStudentNotInList_throwsPersonNotFoundException() {
        assertThrows(StudentNotFoundException.class, () -> uniqueStudentList
                .setStudent(TypicalStudents.STUDENT_ONE, TypicalStudents.STUDENT_ONE));
    }

    @Test
    public void setStudent_editedStudentIsSameStudent_success() {
        uniqueStudentList.add(TypicalStudents.STUDENT_ONE);
        uniqueStudentList.setStudent(TypicalStudents.STUDENT_ONE, TypicalStudents.STUDENT_ONE);
        UniqueStudentList expectedUniqueStudentList = new UniqueStudentList();
        expectedUniqueStudentList.add(TypicalStudents.STUDENT_ONE);
        assertEquals(expectedUniqueStudentList, uniqueStudentList);
    }

    @Test
    public void setStudent_editedStudentHasSameIdentity_success() {
        uniqueStudentList.add(TypicalStudents.STUDENT_ONE);
        Student editedStudent = new StudentBuilder(TypicalStudents.STUDENT_ONE)
                .withMark(true)
                .build();
        uniqueStudentList.setStudent(TypicalStudents.STUDENT_ONE, editedStudent);
        UniqueStudentList expectedUniqueStudentList = new UniqueStudentList();
        expectedUniqueStudentList.add(TypicalStudents.STUDENT_ONE);
        assertEquals(expectedUniqueStudentList, uniqueStudentList);
    }

    @Test
    public void setStudent_editedStudentHasDifferentIdentity_success() {
        uniqueStudentList.add(TypicalStudents.STUDENT_ONE);
        uniqueStudentList.setStudent(TypicalStudents.STUDENT_ONE, TypicalStudents.STUDENT_TWO);
        UniqueStudentList expectedUniqueStudentList = new UniqueStudentList();
        expectedUniqueStudentList.add(TypicalStudents.STUDENT_TWO);
        assertEquals(expectedUniqueStudentList, uniqueStudentList);
    }


    @Test
    public void setStudent_editedStudentHasNonUniqueIdentity_throwsDuplicateStudentException() {
        uniqueStudentList.add(TypicalStudents.STUDENT_ONE);
        uniqueStudentList.add(TypicalStudents.STUDENT_TWO);
        assertThrows(DuplicateStudentException.class, () -> uniqueStudentList
                .setStudent(TypicalStudents.STUDENT_ONE, TypicalStudents.STUDENT_TWO));
    }

    @Test
    public void remove_nullStudent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueStudentList.remove((Student) null));
    }

    @Test
    public void remove_studentDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(StudentNotFoundException.class, () -> uniqueStudentList.remove(TypicalStudents.STUDENT_ONE));
    }

    @Test
    public void remove_existingStudent_removesStudent() {
        uniqueStudentList.add(TypicalStudents.STUDENT_ONE);
        uniqueStudentList.remove(TypicalStudents.STUDENT_ONE);
        UniqueStudentList expectedUniqueStudentList = new UniqueStudentList();
        assertEquals(expectedUniqueStudentList, uniqueStudentList);
    }

    @Test
    public void setStudents_nullUniqueStudentList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueStudentList.setStudents((UniqueStudentList) null));
    }

    @Test
    public void setStudents_uniqueStudentList_replacesOwnListWithProvidedUniqueStudentList() {
        uniqueStudentList.add(TypicalStudents.STUDENT_ONE);
        UniqueStudentList expectedUniqueStudentList = new UniqueStudentList();
        expectedUniqueStudentList.add(TypicalStudents.STUDENT_TWO);
        uniqueStudentList.setStudents(expectedUniqueStudentList);
        assertEquals(expectedUniqueStudentList, uniqueStudentList);
    }

    @Test
    public void setStudents_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueStudentList.setStudents((List<Student>) null));
    }

    @Test
    public void setStudents_list_replacesOwnListWithProvidedList() {
        uniqueStudentList.add(TypicalStudents.STUDENT_ONE);
        List<Student> studentList = Collections.singletonList(TypicalStudents.STUDENT_TWO);
        uniqueStudentList.setStudents(studentList);
        UniqueStudentList expectedUniqueStudentList = new UniqueStudentList();
        expectedUniqueStudentList.add(TypicalStudents.STUDENT_TWO);
        assertEquals(expectedUniqueStudentList, uniqueStudentList);
    }

    @Test
    public void setStudents_listWithDuplicateStudents_throwsDuplicateStudentException() {
        List<Student> listWithDuplicateStudents = Arrays
                .asList(TypicalStudents.STUDENT_ONE, TypicalStudents.STUDENT_ONE);
        assertThrows(DuplicateStudentException.class, () -> uniqueStudentList.setStudents(listWithDuplicateStudents));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(
                UnsupportedOperationException.class, () -> uniqueStudentList.asUnmodifiableObservableList()
                        .remove(0));
    }
}
