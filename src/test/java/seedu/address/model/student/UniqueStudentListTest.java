package seedu.address.model.student;

import org.junit.jupiter.api.Test;
import seedu.address.model.student.exceptions.DuplicateStudentException;
import seedu.address.model.student.exceptions.StudentNotFoundException;
import seedu.address.testutil.student.StudentBuilder;
import seedu.address.testutil.student.TypicalStudents;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

public class UniqueStudentListTest {

    private final UniqueStudentList uniqueStudentList = new UniqueStudentList();

    @Test
    public void contains_nullStudent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueStudentList.contains(null));
    }

    @Test
    public void contains_studentNotInList_returnsFalse() {
        assertFalse(uniqueStudentList.contains(TypicalStudents.StudentOne));
    }

    @Test
    public void contains_studentInList_returnsTrue() {
        uniqueStudentList.add(TypicalStudents.StudentOne);
        assertTrue(uniqueStudentList.contains(TypicalStudents.StudentOne));
    }

    @Test
    public void contains_studentWithSameIdentityFieldsInList_returnsTrue() {
        uniqueStudentList.add(TypicalStudents.StudentFour);
        Student editedStudent = new StudentBuilder(TypicalStudents.StudentFour)
                .withMark(true).withTags(TypicalStudents.tagSetFour)
                .build();
        assertTrue(uniqueStudentList.contains(editedStudent));
    }

    @Test
    public void add_nullStudent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueStudentList.add(null));
    }

    @Test
    public void add_duplicateStudent_throwsDuplicateStudentException() {
        uniqueStudentList.add(TypicalStudents.StudentThree);
        assertThrows(DuplicateStudentException.class, () -> uniqueStudentList.add(TypicalStudents.StudentThree));
    }

    @Test
    public void setStudent_nullTargetPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueStudentList
                .setStudent((Student) null, TypicalStudents.StudentTwo));
    }

    @Test
    public void setStudent_nullEditedStudent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueStudentList
                .setStudent(TypicalStudents.StudentOne, null));
    }

    @Test
    public void setStudent_targetStudentNotInList_throwsPersonNotFoundException() {
        assertThrows(StudentNotFoundException.class, () -> uniqueStudentList
                .setStudent(TypicalStudents.StudentOne, TypicalStudents.StudentOne));
    }

    @Test
    public void setStudent_editedStudentIsSameStudent_success() {
        uniqueStudentList.add(TypicalStudents.StudentOne);
        uniqueStudentList.setStudent(TypicalStudents.StudentOne, TypicalStudents.StudentOne);
        UniqueStudentList expectedUniqueStudentList = new UniqueStudentList();
        expectedUniqueStudentList.add(TypicalStudents.StudentOne);
        assertEquals(expectedUniqueStudentList, uniqueStudentList);
    }

    @Test
    public void setStudent_editedStudentHasSameIdentity_success() {
        uniqueStudentList.add(TypicalStudents.StudentOne);
        Student editedStudent = new StudentBuilder(TypicalStudents.StudentOne)
                .withMark(true)
                .build();
        uniqueStudentList.setStudent(TypicalStudents.StudentOne, editedStudent);
        UniqueStudentList expectedUniqueStudentList = new UniqueStudentList();
        expectedUniqueStudentList.add(TypicalStudents.StudentOne);
        assertEquals(expectedUniqueStudentList, uniqueStudentList);
    }

    @Test
    public void setStudent_editedStudentHasDifferentIdentity_success() {
        uniqueStudentList.add(TypicalStudents.StudentOne);
        uniqueStudentList.setStudent(TypicalStudents.StudentOne, TypicalStudents.StudentTwo);
        UniqueStudentList expectedUniqueStudentList = new UniqueStudentList();
        expectedUniqueStudentList.add(TypicalStudents.StudentTwo);
        assertEquals(expectedUniqueStudentList, uniqueStudentList);
    }


    @Test
    public void setStudent_editedStudentHasNonUniqueIdentity_throwsDuplicateStudentException() {
        uniqueStudentList.add(TypicalStudents.StudentOne);
        uniqueStudentList.add(TypicalStudents.StudentTwo);
        assertThrows(DuplicateStudentException.class, () -> uniqueStudentList
                .setStudent(TypicalStudents.StudentOne, TypicalStudents.StudentTwo));
    }

    @Test
    public void remove_nullStudent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueStudentList.remove((Student)null));
    }

    @Test
    public void remove_studentDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(StudentNotFoundException.class, () -> uniqueStudentList.remove(TypicalStudents.StudentOne));
    }

    @Test
    public void remove_existingStudent_removesStudent() {
        uniqueStudentList.add(TypicalStudents.StudentOne);
        uniqueStudentList.remove(TypicalStudents.StudentOne);
        UniqueStudentList expectedUniqueStudentList = new UniqueStudentList();
        assertEquals(expectedUniqueStudentList, uniqueStudentList);
    }

    @Test
    public void setStudents_nullUniqueStudentList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueStudentList.setStudents((UniqueStudentList) null));
    }

    @Test
    public void setStudents_uniqueStudentList_replacesOwnListWithProvidedUniqueStudentList() {
        uniqueStudentList.add(TypicalStudents.StudentOne);
        UniqueStudentList expectedUniqueStudentList = new UniqueStudentList();
        expectedUniqueStudentList.add(TypicalStudents.StudentTwo);
        uniqueStudentList.setStudents(expectedUniqueStudentList);
        assertEquals(expectedUniqueStudentList, uniqueStudentList);
    }

    @Test
    public void setStudents_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueStudentList.setStudents((List<Student>) null));
    }

    @Test
    public void setStudents_list_replacesOwnListWithProvidedList() {
        uniqueStudentList.add(TypicalStudents.StudentOne);
        List<Student> studentList = Collections.singletonList(TypicalStudents.StudentTwo);
        uniqueStudentList.setStudents(studentList);
        UniqueStudentList expectedUniqueStudentList = new UniqueStudentList();
        expectedUniqueStudentList.add(TypicalStudents.StudentTwo);
        assertEquals(expectedUniqueStudentList, uniqueStudentList);
    }

    @Test
    public void setStudents_listWithDuplicateStudents_throwsDuplicateStudentException() {
        List<Student> listWithDuplicateStudents = Arrays
                .asList(TypicalStudents.StudentOne, TypicalStudents.StudentOne);
        assertThrows(DuplicateStudentException.class, () -> uniqueStudentList.setStudents(listWithDuplicateStudents));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
                -> uniqueStudentList.asUnmodifiableObservableList().remove(0));
    }

}
