package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.util.PersonBuilder;
import seedu.address.model.person.Person;

class StatefulAddressBookTest {

    private StatefulAddressBook addressBook;
    private StatefulAddressBook expectedAddressBook;

    @BeforeEach
    private void setUp() {
        addressBook = new StatefulAddressBook(getTypicalAddressBook());
        expectedAddressBook = new StatefulAddressBook(addressBook);
    }

    @Test
    public void undo() {
        Person person1 = new PersonBuilder().build();
        Person person2 = new PersonBuilder().withName(VALID_NAME_BOB).build();

        expectedAddressBook.addPerson(person1);
        expectedAddressBook.saveAddressBookState();
        expectedAddressBook.addPerson(person2);
        expectedAddressBook.saveAddressBookState();

        // first undo does not mean the state is equal yet
        expectedAddressBook.undo();
        assertNotEquals(expectedAddressBook.getPersonList(), addressBook.getPersonList());

        // second undo sets the person list equal to the main address book's person list
        expectedAddressBook.undo();
        assertEquals(expectedAddressBook.getPersonList(), addressBook.getPersonList());
    }

    @Test
    public void redo() {
        Person person1 = new PersonBuilder().build();
        Person person2 = new PersonBuilder().withName(VALID_NAME_BOB).build();

        expectedAddressBook.addPerson(person1);
        expectedAddressBook.saveAddressBookState();
        addressBook.addPerson(person1);
        addressBook.saveAddressBookState();
        expectedAddressBook.addPerson(person2);
        expectedAddressBook.saveAddressBookState();

        expectedAddressBook.undo();
        expectedAddressBook.undo();

        // two undoes do n9ot make the person list equal
        assertNotEquals(expectedAddressBook.getPersonList(), addressBook.getPersonList());

        // a redo would make the two address books equal
        expectedAddressBook.redo();
        assertEquals(expectedAddressBook.getPersonList(), addressBook.getPersonList());
    }

    @Test
    public void canUndo() {
        expectedAddressBook.saveAddressBookState();
        expectedAddressBook.saveAddressBookState();

        // multiple undoable states
        assertTrue(expectedAddressBook.canUndo());
        expectedAddressBook.undo();

        // single undoable state
        assertTrue(expectedAddressBook.canUndo());
        expectedAddressBook.undo();

        // no undoable state
        assertFalse(expectedAddressBook.canUndo());
    }

    @Test
    public void canRedo() {
        expectedAddressBook.saveAddressBookState();
        expectedAddressBook.saveAddressBookState();
        expectedAddressBook.undo();
        expectedAddressBook.undo();

        // multiple redoable states
        assertTrue(expectedAddressBook.canRedo());
        expectedAddressBook.redo();

        // single redoable state
        assertTrue(expectedAddressBook.canRedo());
        expectedAddressBook.redo();

        // no redoable state
        assertFalse(expectedAddressBook.canRedo());
    }

    @Test
    public void equals() {
        // exactly the same object -> returns true
        assertEquals(addressBook, addressBook);

        // different kinds of objects -> returns false
        assertNotEquals(addressBook, new UserPrefs());

        // same parameters -> returns true
        StatefulAddressBook standardAddressBook = new StatefulAddressBook(getTypicalAddressBook());
        assertEquals(addressBook, standardAddressBook);

        // different parameters -> returns false
        standardAddressBook.saveAddressBookState();
        assertNotEquals(addressBook, standardAddressBook);
    }
}
