package seedu.address.model.calendar.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.calendar.commands.CommandTestUtil.VALID_TASKDAY_BOB;
import static seedu.address.logic.calendar.commands.CommandTestUtil.VALID_TASKTAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalTasks.ALICE;
import static seedu.address.testutil.TypicalTasks.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.calendar.task.exceptions.DuplicateTaskException;
import seedu.address.model.calendar.task.exceptions.TaskNotFoundException;
import seedu.address.testutil.TaskBuilder;


public class UniqueTaskListTest {

    private final UniqueTaskList uniqueTaskList = new UniqueTaskList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTaskList.contains(null));
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniqueTaskList.contains(ALICE));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniqueTaskList.add(ALICE);
        assertTrue(uniqueTaskList.contains(ALICE));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniqueTaskList.add(ALICE);
        Task editedAlice = new TaskBuilder(ALICE).withTaskDay(VALID_TASKDAY_BOB).withTaskTags(VALID_TASKTAG_HUSBAND)
                .build();
        assertTrue(uniqueTaskList.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTaskList.add(null));
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniqueTaskList.add(ALICE);
        assertThrows(DuplicateTaskException.class, () -> uniqueTaskList.add(ALICE));
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTaskList.setPerson(null, ALICE));
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTaskList.setPerson(ALICE, null));
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        assertThrows(TaskNotFoundException.class, () -> uniqueTaskList.setPerson(ALICE, ALICE));
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniqueTaskList.add(ALICE);
        uniqueTaskList.setPerson(ALICE, ALICE);
        UniqueTaskList expectedUniquePersonList = new UniqueTaskList();
        expectedUniquePersonList.add(ALICE);
        assertEquals(expectedUniquePersonList, uniqueTaskList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniqueTaskList.add(ALICE);
        Task editedAlice = new TaskBuilder(ALICE).withTaskDay(VALID_TASKDAY_BOB).withTaskTags(VALID_TASKTAG_HUSBAND)
                .build();
        uniqueTaskList.setPerson(ALICE, editedAlice);
        UniqueTaskList expectedUniquePersonList = new UniqueTaskList();
        expectedUniquePersonList.add(editedAlice);
        assertEquals(expectedUniquePersonList, uniqueTaskList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniqueTaskList.add(ALICE);
        uniqueTaskList.setPerson(ALICE, BOB);
        UniqueTaskList expectedUniquePersonList = new UniqueTaskList();
        expectedUniquePersonList.add(BOB);
        assertEquals(expectedUniquePersonList, uniqueTaskList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniqueTaskList.add(ALICE);
        uniqueTaskList.add(BOB);
        assertThrows(DuplicateTaskException.class, () -> uniqueTaskList.setPerson(ALICE, BOB));
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTaskList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(TaskNotFoundException.class, () -> uniqueTaskList.remove(ALICE));
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniqueTaskList.add(ALICE);
        uniqueTaskList.remove(ALICE);
        UniqueTaskList expectedUniquePersonList = new UniqueTaskList();
        assertEquals(expectedUniquePersonList, uniqueTaskList);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTaskList.setPersons((UniqueTaskList) null));
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        uniqueTaskList.add(ALICE);
        UniqueTaskList expectedUniquePersonList = new UniqueTaskList();
        expectedUniquePersonList.add(BOB);
        uniqueTaskList.setPersons(expectedUniquePersonList);
        assertEquals(expectedUniquePersonList, uniqueTaskList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTaskList.setPersons((List<Task>) null));
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniqueTaskList.add(ALICE);
        List<Task> personList = Collections.singletonList(BOB);
        uniqueTaskList.setPersons(personList);
        UniqueTaskList expectedUniquePersonList = new UniqueTaskList();
        expectedUniquePersonList.add(BOB);
        assertEquals(expectedUniquePersonList, uniqueTaskList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Task> listWithDuplicatePersons = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicateTaskException.class, () -> uniqueTaskList.setPersons(listWithDuplicatePersons));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueTaskList.asUnmodifiableObservableList().remove(0));
    }
}
