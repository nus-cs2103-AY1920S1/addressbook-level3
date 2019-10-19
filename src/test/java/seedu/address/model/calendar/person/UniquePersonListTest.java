//package seedu.address.calendarModel.calendar.task;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
//import static seedu.address.testutil.Assert.assertThrows;
//import static seedu.address.testutil.TypicalPersons.ALICE;
//import static seedu.address.testutil.TypicalPersons.BOB;
//
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//
//import org.junit.jupiter.api.Test;
//
//import seedu.address.calendarModel.task.Task;
//import seedu.address.calendarModel.task.UniqueTaskList;
//import seedu.address.calendarModel.task.exceptions.DuplicateTaskException;
//import seedu.address.calendarModel.task.exceptions.TaskNotFoundException;
//import seedu.address.testutil.PersonBuilder;
//
//
//
//public class UniquePersonListTest {
//
//    private final UniqueTaskList uniquePersonList = new UniqueTaskList();
//
//    @Test
//    public void contains_nullPerson_throwsNullPointerException() {
//        assertThrows(NullPointerException.class, () -> uniquePersonList.contains(null));
//    }
//
//    @Test
//    public void contains_personNotInList_returnsFalse() {
//        assertFalse(uniquePersonList.contains(ALICE));
//    }
//
//    @Test
//    public void contains_personInList_returnsTrue() {
//        uniquePersonList.add(ALICE);
//        assertTrue(uniquePersonList.contains(ALICE));
//    }
//
//    @Test
//    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
//        uniquePersonList.add(ALICE);
//        Task editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
//                .build();
//        assertTrue(uniquePersonList.contains(editedAlice));
//    }
//
//    @Test
//    public void add_nullPerson_throwsNullPointerException() {
//        assertThrows(NullPointerException.class, () -> uniquePersonList.add(null));
//    }
//
//    @Test
//    public void add_duplicatePerson_throwsDuplicatePersonException() {
//        uniquePersonList.add(ALICE);
//        assertThrows(DuplicateTaskException.class, () -> uniquePersonList.add(ALICE));
//    }
//
//    @Test
//    public void setPerson_nullTargetPerson_throwsNullPointerException() {
//        assertThrows(NullPointerException.class, () -> uniquePersonList.setPerson(null, ALICE));
//    }
//
//    @Test
//    public void setPerson_nullEditedPerson_throwsNullPointerException() {
//        assertThrows(NullPointerException.class, () -> uniquePersonList.setPerson(ALICE, null));
//    }
//
//    @Test
//    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
//        assertThrows(TaskNotFoundException.class, () -> uniquePersonList.setPerson(ALICE, ALICE));
//    }
//
//    @Test
//    public void setPerson_editedPersonIsSamePerson_success() {
//        uniquePersonList.add(ALICE);
//        uniquePersonList.setPerson(ALICE, ALICE);
//        UniqueTaskList expectedUniquePersonList = new UniqueTaskList();
//        expectedUniquePersonList.add(ALICE);
//        assertEquals(expectedUniquePersonList, uniquePersonList);
//    }
//
//    @Test
//    public void setPerson_editedPersonHasSameIdentity_success() {
//        uniquePersonList.add(ALICE);
//        Task editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
//                .build();
//        uniquePersonList.setPerson(ALICE, editedAlice);
//        UniqueTaskList expectedUniquePersonList = new UniqueTaskList();
//        expectedUniquePersonList.add(editedAlice);
//        assertEquals(expectedUniquePersonList, uniquePersonList);
//    }
//
//    @Test
//    public void setPerson_editedPersonHasDifferentIdentity_success() {
//        uniquePersonList.add(ALICE);
//        uniquePersonList.setPerson(ALICE, BOB);
//        UniqueTaskList expectedUniquePersonList = new UniqueTaskList();
//        expectedUniquePersonList.add(BOB);
//        assertEquals(expectedUniquePersonList, uniquePersonList);
//    }
//
//    @Test
//    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
//        uniquePersonList.add(ALICE);
//        uniquePersonList.add(BOB);
//        assertThrows(DuplicateTaskException.class, () -> uniquePersonList.setPerson(ALICE, BOB));
//    }
//
//    @Test
//    public void remove_nullPerson_throwsNullPointerException() {
//        assertThrows(NullPointerException.class, () -> uniquePersonList.remove(null));
//    }
//
//    @Test
//    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
//        assertThrows(TaskNotFoundException.class, () -> uniquePersonList.remove(ALICE));
//    }
//
//    @Test
//    public void remove_existingPerson_removesPerson() {
//        uniquePersonList.add(ALICE);
//        uniquePersonList.remove(ALICE);
//        UniqueTaskList expectedUniquePersonList = new UniqueTaskList();
//        assertEquals(expectedUniquePersonList, uniquePersonList);
//    }
//
//    @Test
//    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
//        assertThrows(NullPointerException.class, () -> uniquePersonList.setPersons((UniqueTaskList) null));
//    }
//
//    @Test
//    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
//        uniquePersonList.add(ALICE);
//        UniqueTaskList expectedUniquePersonList = new UniqueTaskList();
//        expectedUniquePersonList.add(BOB);
//        uniquePersonList.setPersons(expectedUniquePersonList);
//        assertEquals(expectedUniquePersonList, uniquePersonList);
//    }
//
//    @Test
//    public void setPersons_nullList_throwsNullPointerException() {
//        assertThrows(NullPointerException.class, () -> uniquePersonList.setPersons((List<Task>) null));
//    }
//
//    @Test
//    public void setPersons_list_replacesOwnListWithProvidedList() {
//        uniquePersonList.add(ALICE);
//        List<Task> personList = Collections.singletonList(BOB);
//        uniquePersonList.setPersons(personList);
//        UniqueTaskList expectedUniquePersonList = new UniqueTaskList();
//        expectedUniquePersonList.add(BOB);
//        assertEquals(expectedUniquePersonList, uniquePersonList);
//    }
//
//    @Test
//    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
//        List<Task> listWithDuplicatePersons = Arrays.asList(ALICE, ALICE);
//        assertThrows(DuplicateTaskException.class, () -> uniquePersonList.setPersons(listWithDuplicatePersons));
//    }
//
//    @Test
//    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
//        assertThrows(UnsupportedOperationException.class, ()
//            -> uniquePersonList.asUnmodifiableObservableList().remove(0));
//    }
//}
