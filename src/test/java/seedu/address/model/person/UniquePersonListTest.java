//package seedu.address.model.person;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FOOD;
//import static seedu.address.testutil.Assert.assertThrows;
//import static seedu.address.testutil.TypicalEntries.ALICE;
//import static seedu.address.testutil.TypicalEntries.BOB;
//
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//
//import org.junit.jupiter.api.Test;
//
//import seedu.address.model.person.exceptions.DuplicateEntryException;
//import seedu.address.model.person.exceptions.EntryNotFoundException;
//import seedu.address.testutil.EntryBuilder;
//
//public class UniqueEntryListTest {
//
//    private final UniqueEntryList uniquePersonList = new UniqueEntryList();
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
//        Person editedAlice = new EntryBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_FOOD)
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
//        assertThrows(DuplicateEntryException.class, () -> uniquePersonList.add(ALICE));
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
//        assertThrows(EntryNotFoundException.class, () -> uniquePersonList.setPerson(ALICE, ALICE));
//    }
//
//    @Test
//    public void setPerson_editedPersonIsSamePerson_success() {
//        uniquePersonList.add(ALICE);
//        uniquePersonList.setPerson(ALICE, ALICE);
//        UniqueEntryList expectedUniquePersonList = new UniqueEntryList();
//        expectedUniquePersonList.add(ALICE);
//        assertEquals(expectedUniquePersonList, uniquePersonList);
//    }
//
//    @Test
//    public void setPerson_editedPersonHasSameIdentity_success() {
//        uniquePersonList.add(ALICE);
//        Person editedAlice = new EntryBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_FOOD)
//                .build();
//        uniquePersonList.setPerson(ALICE, editedAlice);
//        UniqueEntryList expectedUniquePersonList = new UniqueEntryList();
//        expectedUniquePersonList.add(editedAlice);
//        assertEquals(expectedUniquePersonList, uniquePersonList);
//    }
//
//    @Test
//    public void setPerson_editedPersonHasDifferentIdentity_success() {
//        uniquePersonList.add(ALICE);
//        uniquePersonList.setPerson(ALICE, BOB);
//        UniqueEntryList expectedUniquePersonList = new UniqueEntryList();
//        expectedUniquePersonList.add(BOB);
//        assertEquals(expectedUniquePersonList, uniquePersonList);
//    }
//
//    @Test
//    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
//        uniquePersonList.add(ALICE);
//        uniquePersonList.add(BOB);
//        assertThrows(DuplicateEntryException.class, () -> uniquePersonList.setPerson(ALICE, BOB));
//    }
//
//    @Test
//    public void remove_nullPerson_throwsNullPointerException() {
//        assertThrows(NullPointerException.class, () -> uniquePersonList.remove(null));
//    }
//
//    @Test
//    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
//        assertThrows(EntryNotFoundException.class, () -> uniquePersonList.remove(ALICE));
//    }
//
//    @Test
//    public void remove_existingPerson_removesPerson() {
//        uniquePersonList.add(ALICE);
//        uniquePersonList.remove(ALICE);
//        UniqueEntryList expectedUniquePersonList = new UniqueEntryList();
//        assertEquals(expectedUniquePersonList, uniquePersonList);
//    }
//
//    @Test
//    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
//        assertThrows(NullPointerException.class, () -> uniquePersonList.setEntries((UniqueEntryList) null));
//    }
//
//    @Test
//    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
//        uniquePersonList.add(ALICE);
//        UniqueEntryList expectedUniquePersonList = new UniqueEntryList();
//        expectedUniquePersonList.add(BOB);
//        uniquePersonList.setEntries(expectedUniquePersonList);
//        assertEquals(expectedUniquePersonList, uniquePersonList);
//    }
//
//    @Test
//    public void setPersons_nullList_throwsNullPointerException() {
//        assertThrows(NullPointerException.class, () -> uniquePersonList.setEntries((List<Person>) null));
//    }
//
//    @Test
//    public void setPersons_list_replacesOwnListWithProvidedList() {
//        uniquePersonList.add(ALICE);
//        List<Person> personList = Collections.singletonList(BOB);
//        uniquePersonList.setEntries(personList);
//        UniqueEntryList expectedUniquePersonList = new UniqueEntryList();
//        expectedUniquePersonList.add(BOB);
//        assertEquals(expectedUniquePersonList, uniquePersonList);
//    }
//
//    @Test
//    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
//        List<Person> listWithDuplicatePersons = Arrays.asList(ALICE, ALICE);
//        assertThrows(DuplicateEntryException.class, () -> uniquePersonList.setEntries(listWithDuplicatePersons));
//    }
//
//    @Test
//    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
//        assertThrows(UnsupportedOperationException.class, ()
//            -> uniquePersonList.asUnmodifiableObservableList().remove(0));
//    }
//}
