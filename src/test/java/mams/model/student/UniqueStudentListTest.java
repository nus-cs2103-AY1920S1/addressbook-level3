package mams.model.student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import mams.logic.commands.CommandTestUtil;
import mams.model.student.exceptions.DuplicateStudentException;
import mams.model.student.exceptions.StudentNotFoundException;
import mams.testutil.Assert;
import mams.testutil.StudentBuilder;
import mams.testutil.TypicalStudents;

public class UniqueStudentListTest {

    private final UniqueStudentList uniqueStudentList = new UniqueStudentList();

    @Test
    public void contains_nullStudent_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueStudentList.contains(null));
    }

    @Test
    public void contains_studentNotInList_returnsFalse() {
        assertFalse(uniqueStudentList.contains(TypicalStudents.ALICE));
    }

    @Test
    public void contains_studentInList_returnsTrue() {
        uniqueStudentList.add(TypicalStudents.ALICE);
        assertTrue(uniqueStudentList.contains(TypicalStudents.ALICE));
    }

    @Test
    public void contains_studentWithSameIdentityFieldsInList_returnsTrue() {
        uniqueStudentList.add(TypicalStudents.ALICE);
        Student editedAlice = new StudentBuilder(TypicalStudents.ALICE)
                .withAddress(CommandTestUtil.VALID_ADDRESS_BOB).withTags(CommandTestUtil.VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueStudentList.contains(editedAlice));
    }

    @Test
    public void add_nullStudent_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueStudentList.add(null));
    }

    @Test
    public void add_duplicateStudent_throwsDuplicateStudentException() {
        uniqueStudentList.add(TypicalStudents.ALICE);
        Assert.assertThrows(DuplicateStudentException.class, () -> uniqueStudentList.add(TypicalStudents.ALICE));
    }

    @Test
    public void setStudent_nullTargetStudent_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueStudentList
                .setStudent(null, TypicalStudents.ALICE));
    }

    @Test
    public void setStudent_nullEditedStudent_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueStudentList
                .setStudent(TypicalStudents.ALICE, null));
    }

    @Test
    public void setStudent_targetStudentNotInList_throwsStudentNotFoundException() {
        Assert.assertThrows(StudentNotFoundException.class, () -> uniqueStudentList
                .setStudent(TypicalStudents.ALICE, TypicalStudents.ALICE));
    }

    @Test
    public void setStudent_editedStudentIsSameStudent_success() {
        uniqueStudentList.add(TypicalStudents.ALICE);
        uniqueStudentList.setStudent(TypicalStudents.ALICE, TypicalStudents.ALICE);
        UniqueStudentList expectedUniqueStudentList = new UniqueStudentList();
        expectedUniqueStudentList.add(TypicalStudents.ALICE);
        assertEquals(expectedUniqueStudentList, uniqueStudentList);
    }

    @Test
    public void setStudent_editedStudentHasSameIdentity_success() {
        uniqueStudentList.add(TypicalStudents.ALICE);
        Student editedAlice = new StudentBuilder(TypicalStudents.ALICE)
                .withAddress(CommandTestUtil.VALID_ADDRESS_BOB).withTags(CommandTestUtil.VALID_TAG_HUSBAND)
                .build();
        uniqueStudentList.setStudent(TypicalStudents.ALICE, editedAlice);
        UniqueStudentList expectedUniqueStudentList = new UniqueStudentList();
        expectedUniqueStudentList.add(editedAlice);
        assertEquals(expectedUniqueStudentList, uniqueStudentList);
    }

    @Test
    public void setStudent_editedStudentHasDifferentIdentity_success() {
        uniqueStudentList.add(TypicalStudents.ALICE);
        uniqueStudentList.setStudent(TypicalStudents.ALICE, TypicalStudents.BOB);
        UniqueStudentList expectedUniqueStudentList = new UniqueStudentList();
        expectedUniqueStudentList.add(TypicalStudents.BOB);
        assertEquals(expectedUniqueStudentList, uniqueStudentList);
    }

    @Test
    public void setStudent_editedStudentHasNonUniqueIdentity_throwsDuplicateStudentException() {
        uniqueStudentList.add(TypicalStudents.ALICE);
        uniqueStudentList.add(TypicalStudents.BOB);
        Assert.assertThrows(DuplicateStudentException.class, () -> uniqueStudentList
                .setStudent(TypicalStudents.ALICE, TypicalStudents.BOB));
    }

    @Test
    public void remove_nullStudent_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueStudentList.remove(null));
    }

    @Test
    public void remove_studentDoesNotExist_throwsStudentNotFoundException() {
        Assert.assertThrows(StudentNotFoundException.class, () -> uniqueStudentList.remove(TypicalStudents.ALICE));
    }

    @Test
    public void remove_existingStudent_removesStudent() {
        uniqueStudentList.add(TypicalStudents.ALICE);
        uniqueStudentList.remove(TypicalStudents.ALICE);
        UniqueStudentList expectedUniqueStudentList = new UniqueStudentList();
        assertEquals(expectedUniqueStudentList, uniqueStudentList);
    }

    @Test
    public void setStudents_nullUniqueStudentList_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueStudentList.setStudents((UniqueStudentList) null));
    }

    @Test
    public void setStudents_uniqueStudentList_replacesOwnListWithProvidedUniqueStudentList() {
        uniqueStudentList.add(TypicalStudents.ALICE);
        UniqueStudentList expectedUniqueStudentList = new UniqueStudentList();
        expectedUniqueStudentList.add(TypicalStudents.BOB);
        uniqueStudentList.setStudents(expectedUniqueStudentList);
        assertEquals(expectedUniqueStudentList, uniqueStudentList);
    }

    @Test
    public void setStudents_nullList_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueStudentList.setStudents((List<Student>) null));
    }

    @Test
    public void setStudents_list_replacesOwnListWithProvidedList() {
        uniqueStudentList.add(TypicalStudents.ALICE);
        List<Student> studentList = Collections.singletonList(TypicalStudents.BOB);
        uniqueStudentList.setStudents(studentList);
        UniqueStudentList expectedUniqueStudentList = new UniqueStudentList();
        expectedUniqueStudentList.add(TypicalStudents.BOB);
        assertEquals(expectedUniqueStudentList, uniqueStudentList);
    }

    @Test
    public void setStudents_listWithDuplicateStudents_throwsDuplicateStudentException() {
        List<Student> listWithDuplicateStudents = Arrays.asList(TypicalStudents.ALICE, TypicalStudents.ALICE);
        Assert.assertThrows(DuplicateStudentException.class, () -> uniqueStudentList
                .setStudents(listWithDuplicateStudents));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, ()
            -> uniqueStudentList.asUnmodifiableObservableList().remove(0));
    }
}
