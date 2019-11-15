//package seedu.guilttrip.logic.commands;
//
//import static java.util.Objects.requireNonNull;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static seedu.guilttrip.testutil.Assert.assertThrows;
//
//import java.nio.file.Path;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.function.Predicate;
//
//import org.junit.jupiter.api.Test;
//
//import javafx.collections.ObservableList;
//import seedu.guilttrip.commons.core.GuiSettings;
//import seedu.guilttrip.logic.commands.exceptions.CommandException;
//import seedu.guilttrip.model.GuiltTrip;
//import seedu.guilttrip.model.Model;
//import seedu.guilttrip.model.ReadOnlyGuiltTrip;
//import seedu.guilttrip.model.ReadOnlyUserPrefs;
//import seedu.guilttrip.model.entry.Person;
//import seedu.guilttrip.testutil.EntryBuilder;
//
//public class AddCommandTest {
//
//    @Test
//    public void constructor_nullPerson_throwsNullPointerException() {
//        assertThrows(NullPointerException.class, () -> new AddCommand(null));
//    }
//
//    @Test
//    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
//        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
//        Person validPerson = new EntryBuilder().build();
//
//        CommandResult commandResult = new AddCommand(validPerson).execute(modelStub);
//
//        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validPerson), commandResult.getFeedbackToUser());
//        assertEquals(Arrays.asList(validPerson), modelStub.personsAdded);
//    }
//
//    @Test
//    public void execute_duplicatePerson_throwsCommandException() {
//        Person validPerson = new EntryBuilder().build();
//        AddCommand addCommand = new AddCommand(validPerson);
//        ModelStub modelStub = new ModelStubWithPerson(validPerson);
//
//        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_ENTRY, () -> addCommand.execute(modelStub));
//    }
//
//    @Test
//    public void equals() {
//        Person alice = new EntryBuilder().withDesc("Alice").build();
//        Person bob = new EntryBuilder().withDesc("Bob").build();
//        AddCommand addAliceCommand = new AddCommand(alice);
//        AddCommand addBobCommand = new AddCommand(bob);
//
//        // same object -> returns true
//        assertTrue(addAliceCommand.equals(addAliceCommand));
//
//        // same values -> returns true
//        AddCommand addAliceCommandCopy = new AddCommand(alice);
//        assertTrue(addAliceCommand.equals(addAliceCommandCopy));
//
//        // different types -> returns false
//        assertFalse(addAliceCommand.equals(1));
//
//        // null -> returns false
//        assertFalse(addAliceCommand.equals(null));
//
//        // different entry -> returns false
//        assertFalse(addAliceCommand.equals(addBobCommand));
//    }
//
//    /**
//     * A default model stub that have all of the methods failing.
//     */
//    private class ModelStub implements Model {
//        @Override
//        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
//            throw new AssertionError("This method should not be called.");
//        }
//
//        @Override
//        public ReadOnlyUserPrefs getUserPrefs() {
//            throw new AssertionError("This method should not be called.");
//        }
//
//        @Override
//        public GuiSettings getGuiSettings() {
//            throw new AssertionError("This method should not be called.");
//        }
//
//        @Override
//        public void setGuiSettings(GuiSettings guiSettings) {
//            throw new AssertionError("This method should not be called.");
//        }
//
//        @Override
//        public Path getAddressBookFilePath() {
//            throw new AssertionError("This method should not be called.");
//        }
//
//        @Override
//        public void setAddressBookFilePath(Path addressBookFilePath) {
//            throw new AssertionError("This method should not be called.");
//        }
//
//        @Override
//        public void addPerson(Person entry) {
//            throw new AssertionError("This method should not be called.");
//        }
//
//        @Override
//        public void setAddressBook(ReadOnlyGuiltTrip newData) {
//            throw new AssertionError("This method should not be called.");
//        }
//
//        @Override
//        public ReadOnlyGuiltTrip getAddressBook() {
//            throw new AssertionError("This method should not be called.");
//        }
//
//        @Override
//        public boolean hasPerson(Person entry) {
//            throw new AssertionError("This method should not be called.");
//        }
//
//        @Override
//        public void deletePerson(Person target) {
//            throw new AssertionError("This method should not be called.");
//        }
//
//        @Override
//        public void setPerson(Person target, Person editedPerson) {
//            throw new AssertionError("This method should not be called.");
//        }
//
//        @Override
//        public ObservableList<Person> getFilteredPersonList() {
//            throw new AssertionError("This method should not be called.");
//        }
//
//        @Override
//        public void updateFilteredPersonList(Predicate<Person> predicate) {
//            throw new AssertionError("This method should not be called.");
//        }
//    }
//
//    /**
//     * A Model stub that contains a single entry.
//     */
//    private class ModelStubWithPerson extends ModelStub {
//        private final Person entry;
//
//        ModelStubWithPerson(Person entry) {
//            requireNonNull(entry);
//            this.entry = entry;
//        }
//
//        @Override
//        public boolean hasPerson(Person entry) {
//            requireNonNull(entry);
//            return this.entry.isSamePerson(entry);
//        }
//    }
//
//    /**
//     * A Model stub that always accept the entry being added.
//     */
//    private class ModelStubAcceptingPersonAdded extends ModelStub {
//        final ArrayList<Person> personsAdded = new ArrayList<>();
//
//        @Override
//        public boolean hasPerson(Person entry) {
//            requireNonNull(entry);
//            return personsAdded.stream().anyMatch(entry::isSamePerson);
//        }
//
//        @Override
//        public void addPerson(Person entry) {
//            requireNonNull(entry);
//            personsAdded.add(entry);
//        }
//
//        @Override
//        public ReadOnlyGuiltTrip getAddressBook() {
//            return new GuiltTrip();
//        }
//    }
//
//}
