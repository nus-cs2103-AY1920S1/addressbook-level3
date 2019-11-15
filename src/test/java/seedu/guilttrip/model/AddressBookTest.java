//package seedu.guilttrip.model;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static seedu.guilttrip.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
//import static seedu.guilttrip.logic.commands.CommandTestUtil.VALID_TAG_FOOD;
//import static seedu.guilttrip.testutil.Assert.assertThrows;
//import static seedu.guilttrip.testutil.TypicalEntries.ALICE;
//import static seedu.guilttrip.testutil.TypicalEntries.getTypicalAddressBook;
//
//import java.util.Arrays;
//import java.util.Collection;
//import java.util.Collections;
//import java.util.List;
//
//import org.junit.jupiter.api.Test;
//
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import seedu.guilttrip.model.entry.Person;
//import seedu.guilttrip.model.entry.exceptions.DuplicateEntryException;
//import seedu.guilttrip.testutil.EntryBuilder;
//
//public class AddressBookTest {
//
//    private final GuiltTrip addressBook = new GuiltTrip();
//
//    @Test
//    public void constructor() {
//        assertEquals(Collections.emptyList(), addressBook.getEntryList());
//    }
//
//    @Test
//    public void resetData_null_throwsNullPointerException() {
//        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
//    }
//
//    @Test
//    public void resetData_withValidReadOnlyAddressBook_replacesData() {
//        GuiltTrip newData = getTypicalAddressBook();
//        addressBook.resetData(newData);
//        assertEquals(newData, addressBook);
//    }
//
//    @Test
//    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
//        // Two persons with the same identity fields
//        Person editedAlice = new EntryBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_FOOD)
//                .build();
//        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
//        AddressBookStub newData = new AddressBookStub(newPersons);
//
//        assertThrows(DuplicateEntryException.class, () -> addressBook.resetData(newData));
//    }
//
//    @Test
//    public void hasPerson_nullPerson_throwsNullPointerException() {
//        assertThrows(NullPointerException.class, () -> addressBook.hasPerson(null));
//    }
//
//    @Test
//    public void hasPerson_personNotInAddressBook_returnsFalse() {
//        assertFalse(addressBook.hasPerson(ALICE));
//    }
//
//    @Test
//    public void hasPerson_personInAddressBook_returnsTrue() {
//        addressBook.addPerson(ALICE);
//        assertTrue(addressBook.hasPerson(ALICE));
//    }
//
//    @Test
//    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
//        addressBook.addPerson(ALICE);
//        Person editedAlice = new EntryBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_FOOD)
//                .build();
//        assertTrue(addressBook.hasPerson(editedAlice));
//    }
//
//    @Test
//    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
//        assertThrows(UnsupportedOperationException.class, () -> addressBook.getEntryList().remove(0));
//    }
//
//    /**
//     * A stub ReadOnlyGuiltTrip whose persons list can violate interface constraints.
//     */
//    private static class AddressBookStub implements ReadOnlyGuiltTrip {
//        private final ObservableList<Person> persons = FXCollections.observableArrayList();
//
//        AddressBookStub(Collection<Person> persons) {
//            this.persons.setAll(persons);
//        }
//
//        @Override
//        public ObservableList<Person> getEntryList() {
//            return persons;
//        }
//    }
//
//}
