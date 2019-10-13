package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Itinerary;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyItinerary;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Contact;
import seedu.address.testutil.PersonBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand((Contact) null));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Contact validContact = new PersonBuilder().build();

        CommandResult commandResult = new AddCommand(validContact).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validContact), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validContact), modelStub.personsAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Contact validContact = new PersonBuilder().build();
        AddCommand addCommand = new AddCommand(validContact);
        ModelStub modelStub = new ModelStubWithPerson(validContact);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_PERSON, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Contact alice = new PersonBuilder().withName("Alice").build();
        Contact bob = new PersonBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different contacts -> returns false
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
        public void addPerson(Contact contact) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setItinerary(ReadOnlyItinerary newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyItinerary getItinerary() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Contact contact) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Contact target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Contact target, Contact editedContact) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Contact> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Contact> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single contacts.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final Contact contact;

        ModelStubWithPerson(Contact contact) {
            requireNonNull(contact);
            this.contact = contact;
        }

        @Override
        public boolean hasPerson(Contact contact) {
            requireNonNull(contact);
            return this.contact.isSamePerson(contact);
        }
    }

    /**
     * A Model stub that always accept the contacts being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Contact> personsAdded = new ArrayList<>();

        @Override
        public boolean hasPerson(Contact contact) {
            requireNonNull(contact);
            return personsAdded.stream().anyMatch(contact::isSamePerson);
        }

        @Override
        public void addPerson(Contact contact) {
            requireNonNull(contact);
            personsAdded.add(contact);
        }

        @Override
        public ReadOnlyItinerary getItinerary() {
            return new Itinerary();
        }
    }

}
