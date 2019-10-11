package tagline.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tagline.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;

import tagline.commons.core.GuiSettings;
import tagline.logic.commands.contact.CreateContactCommand;
import tagline.logic.commands.exceptions.CommandException;
import tagline.model.AddressBook;
import tagline.model.Model;
import tagline.model.ReadOnlyAddressBook;
import tagline.model.ReadOnlyUserPrefs;
import tagline.model.contact.Contact;
import tagline.testutil.ContactBuilder;

public class CreateContactCommandTest {

    @Test
    public void constructor_nullContact_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CreateContactCommand(null));
    }

    @Test
    public void execute_contactAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingContactAdded modelStub = new ModelStubAcceptingContactAdded();
        Contact validContact = new ContactBuilder().build();

        CommandResult commandResult = new CreateContactCommand(validContact).execute(modelStub);

        assertEquals(String.format(CreateContactCommand.MESSAGE_SUCCESS, validContact),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validContact), modelStub.contactsAdded);
    }

    @Test
    public void execute_duplicateContact_throwsCommandException() {
        Contact validContact = new ContactBuilder().build();
        CreateContactCommand createContactCommand = new CreateContactCommand(validContact);
        ModelStub modelStub = new ModelStubWithContact(validContact);

        assertThrows(CommandException.class,
                CreateContactCommand.MESSAGE_DUPLICATE_CONTACT, () -> createContactCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Contact alice = new ContactBuilder().withName("Alice").build();
        Contact bob = new ContactBuilder().withName("Bob").build();
        CreateContactCommand addAliceCommand = new CreateContactCommand(alice);
        CreateContactCommand addBobCommand = new CreateContactCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        CreateContactCommand addAliceCommandCopy = new CreateContactCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different contact -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addContact(Contact contact) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasContact(Contact contact) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteContact(Contact target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setContact(Contact target, Contact editedContact) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Contact> getFilteredContactList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredContactList(Predicate<Contact> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single contact.
     */
    private class ModelStubWithContact extends ModelStub {
        private final Contact contact;

        ModelStubWithContact(Contact contact) {
            requireNonNull(contact);
            this.contact = contact;
        }

        @Override
        public boolean hasContact(Contact contact) {
            requireNonNull(contact);
            return this.contact.isSameContact(contact);
        }
    }

    /**
     * A Model stub that always accept the contact being added.
     */
    private class ModelStubAcceptingContactAdded extends ModelStub {
        final ArrayList<Contact> contactsAdded = new ArrayList<>();

        @Override
        public boolean hasContact(Contact contact) {
            requireNonNull(contact);
            return contactsAdded.stream().anyMatch(contact::isSameContact);
        }

        @Override
        public void addContact(Contact contact) {
            requireNonNull(contact);
            contactsAdded.add(contact);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
