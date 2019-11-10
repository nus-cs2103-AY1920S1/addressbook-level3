package seedu.address.model.classroom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalClassrooms.CLASSROOM_ONE;
import static seedu.address.testutil.TypicalClassrooms.CLASSROOM_TWO;
import static seedu.address.testutil.TypicalStudents.getTypicalStudents;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.classroom.exceptions.ClassroomNotFoundException;
import seedu.address.model.classroom.exceptions.DuplicateClassroomException;
import seedu.address.testutil.ClassroomBuilder;

public class UniqueClassroomListTest {

    private final UniqueClassroomList uniqueClassroomList = new UniqueClassroomList();

    @Test
    public void contains_nullClassroom_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueClassroomList.contains(null));
    }

    @Test
    public void contains_classroomNotInList_returnsFalse() {
        assertFalse(uniqueClassroomList.contains(CLASSROOM_ONE));
    }

    @Test
    public void contains_classroomInList_returnsTrue() {
        uniqueClassroomList.add(CLASSROOM_ONE);
        assertTrue(uniqueClassroomList.contains(CLASSROOM_ONE));
    }

    @Test
    public void contains_classroomWithSameIdentityFieldsInList_returnsTrue() {
        uniqueClassroomList.add(CLASSROOM_ONE);
        Classroom editedClassroomOne = new ClassroomBuilder(CLASSROOM_ONE)
                .withStudents(getTypicalStudents()).build();
        assertTrue(uniqueClassroomList.contains(editedClassroomOne));
    }

    @Test
    public void add_nullClassroom_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueClassroomList.add(null));
    }

    @Test
    public void add_duplicateClassroom_throwsDuplicateClassroomException() {
        uniqueClassroomList.add(CLASSROOM_ONE);
        assertThrows(DuplicateClassroomException.class, () -> uniqueClassroomList.add(CLASSROOM_ONE));
    }

    @Test
    public void setClassroom_nullTargetClassroom_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueClassroomList.setClassroom(null, CLASSROOM_ONE));
    }

    @Test
    public void setClassroom_nullEditedClassroom_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueClassroomList.setClassroom(CLASSROOM_ONE,
                null));
    }

    @Test
    public void setClassroom_targetClassroomNotInList_throwsClassroomNotFoundException() {
        assertThrows(ClassroomNotFoundException.class, () -> uniqueClassroomList.setClassroom(CLASSROOM_ONE,
                CLASSROOM_ONE));
    }

    @Test
    public void setClassroom_editedClassroomIsSameClassroom_success() {
        uniqueClassroomList.add(CLASSROOM_ONE);
        uniqueClassroomList.setClassroom(CLASSROOM_ONE, CLASSROOM_ONE);
        UniqueClassroomList expectedUniqueClassroomList = new UniqueClassroomList();
        expectedUniqueClassroomList.add(CLASSROOM_ONE);
        assertEquals(expectedUniqueClassroomList, uniqueClassroomList);
    }

    @Test
    public void setClassroom_editedClassroomHasSameIdentity_success() {
        uniqueClassroomList.add(CLASSROOM_ONE);
        Classroom editedClassroom = new ClassroomBuilder(CLASSROOM_ONE)
                .withStudents(getTypicalStudents()).build();

        uniqueClassroomList.setClassroom(CLASSROOM_ONE, editedClassroom);
        UniqueClassroomList expectedUniqueClassroomList = new UniqueClassroomList();
        expectedUniqueClassroomList.add(editedClassroom);
        assertEquals(expectedUniqueClassroomList, uniqueClassroomList);
    }

    @Test
    public void setClassroom_editedClassroomHasDifferentIdentity_success() {
        uniqueClassroomList.add(CLASSROOM_ONE);
        uniqueClassroomList.setClassroom(CLASSROOM_ONE, CLASSROOM_TWO);
        UniqueClassroomList expectedUniqueClassroomList = new UniqueClassroomList();
        expectedUniqueClassroomList.add(CLASSROOM_TWO);
        assertEquals(expectedUniqueClassroomList, uniqueClassroomList);
    }

    @Test
    public void setClassroom_editedClassroomHasNonUniqueIdentity_throwsDuplicateClassroomException() {
        uniqueClassroomList.add(CLASSROOM_ONE);
        uniqueClassroomList.add(CLASSROOM_TWO);
        assertThrows(DuplicateClassroomException.class, () -> uniqueClassroomList
                .setClassroom(CLASSROOM_ONE, CLASSROOM_TWO));
    }

    @Test
    public void remove_nullClassroom_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueClassroomList.remove(null));
    }

    @Test
    public void remove_classroomDoesNotExist_throwsClassroomNotFoundException() {
        assertThrows(ClassroomNotFoundException.class, () -> uniqueClassroomList.remove(CLASSROOM_ONE));
    }

    @Test
    public void remove_existingClassroom_removesClassroom() {
        uniqueClassroomList.add(CLASSROOM_ONE);
        uniqueClassroomList.remove(CLASSROOM_ONE);
        UniqueClassroomList expectedUniqueClassroomList = new UniqueClassroomList();
        assertEquals(expectedUniqueClassroomList, uniqueClassroomList);
    }

    @Test
    public void setClassrooms_nullUniqueClassroomList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueClassroomList.setClassrooms((UniqueClassroomList) null));
    }

    @Test
    public void setClassrooms_uniqueClassroomList_replacesOwnListWithProvidedUniqueClassroomList() {
        uniqueClassroomList.add(CLASSROOM_ONE);
        UniqueClassroomList expectedUniqueClassroomList = new UniqueClassroomList();
        expectedUniqueClassroomList.add(CLASSROOM_TWO);
        uniqueClassroomList.setClassrooms(expectedUniqueClassroomList);
        assertEquals(expectedUniqueClassroomList, uniqueClassroomList);
    }

    @Test
    public void setClassrooms_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueClassroomList.setClassrooms((List<Classroom>) null));
    }

    @Test
    public void setClassrooms_list_replacesOwnListWithProvidedList() {
        uniqueClassroomList.add(CLASSROOM_ONE);
        List<Classroom> classroomList = Collections.singletonList(CLASSROOM_TWO);
        uniqueClassroomList.setClassrooms(classroomList);
        UniqueClassroomList expectedUniqueClassroomList = new UniqueClassroomList();
        expectedUniqueClassroomList.add(CLASSROOM_TWO);
        assertEquals(expectedUniqueClassroomList, uniqueClassroomList);
    }

    @Test
    public void setClassrooms_listWithDuplicateClassrooms_throwsDuplicateClassroomException() {
        List<Classroom> listWithDuplicateClassrooms = Arrays.asList(CLASSROOM_ONE, CLASSROOM_ONE);
        assertThrows(
                DuplicateClassroomException.class, () -> uniqueClassroomList
                        .setClassrooms(listWithDuplicateClassrooms));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, (
                ) -> uniqueClassroomList.asUnmodifiableObservableList().remove(0));
    }
}
