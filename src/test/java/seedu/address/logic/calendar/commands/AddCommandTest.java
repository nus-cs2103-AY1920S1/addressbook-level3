//package seedu.address.logic.calendar.commands;
//
//import static java.util.Objects.requireNonNull;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static seedu.address.testutil.Assert.assertThrows;
//
//import java.nio.file.Path;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.function.Predicate;
//
//import org.junit.jupiter.api.Test;
//
//import javafx.collections.ObservableList;
//import seedu.address.commons.core.GuiSettings;
//import seedu.address.logic.commands.AddCommand;
//import seedu.address.logic.commands.CommandResult;
//import seedu.address.logic.commands.exceptions.CommandException;
//import seedu.address.calendarModel.task.Task;
//import seedu.address.testutil.PersonBuilder;
//
//
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
//        Task validPerson = new PersonBuilder().build();
//
//        CommandResult commandResult = new AddCommand(validPerson).execute(modelStub);
//
//        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validPerson), commandResult.getFeedbackToUser());
//        assertEquals(Arrays.asList(validPerson), modelStub.personsAdded);
//    }
//
//    @Test
//    public void execute_duplicatePerson_throwsCommandException() {
//        Task validPerson = new PersonBuilder().build();
//        AddCommand addCommand = new AddCommand(validPerson);
//        ModelStub modelStub = new ModelStubWithPerson(validPerson);
//
//        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_PERSON,
//                () -> addCommand.execute(modelStub));
//    }
//
//    @Test
//    public void equals() {
//        Task alice = new PersonBuilder().withName("Alice").build();
//        Task bob = new PersonBuilder().withName("Bob").build();
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
//        // different task -> returns false
//        assertFalse(addAliceCommand.equals(addBobCommand));
//    }
//
//    /**
//     * A default calendarModel stub that have all of the methods failing.
//     */
//    private class ModelStub implements CalendarModel {
//        @Override
//        public void setUserPrefs(ReadOnlyCalendarUserPrefs userPrefs) {
//            throw new AssertionError("This method should not be called.");
//        }
//
//        @Override
//        public ReadOnlyCalendarUserPrefs getUserPrefs() {
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
//        public void addPerson(Task task) {
//            throw new AssertionError("This method should not be called.");
//        }
//
//        @Override
//        public void setCalendarAddressBook(ReadOnlyCalendarAddressBook newData) {
//            throw new AssertionError("This method should not be called.");
//        }
//
//        @Override
//        public ReadOnlyCalendarAddressBook getCalendarAddressBook() {
//            throw new AssertionError("This method should not be called.");
//        }
//
//        @Override
//        public boolean hasPerson(Task task) {
//            throw new AssertionError("This method should not be called.");
//        }
//
//        @Override
//        public void deletePerson(Task target) {
//            throw new AssertionError("This method should not be called.");
//        }
//
//        @Override
//        public void setPerson(Task target, Task editedPerson) {
//            throw new AssertionError("This method should not be called.");
//        }
//
//        @Override
//        public ObservableList<Task> getFilteredPersonList() {
//            throw new AssertionError("This method should not be called.");
//        }
//
//        @Override
//        public void updateFilteredPersonList(Predicate<Task> predicate) {
//            throw new AssertionError("This method should not be called.");
//        }
//    }
//
//    /**
//     * A CalendarModel stub that contains a single task.
//     */
//    private class ModelStubWithPerson extends ModelStub {
//        private final Task task;
//
//        ModelStubWithPerson(Task task) {
//            requireNonNull(task);
//            this.task = task;
//        }
//
//        @Override
//        public boolean hasPerson(Task task) {
//            requireNonNull(task);
//            return this.task.isSamePerson(task);
//        }
//    }
//
//    /**
//     * A CalendarModel stub that always accept the task being added.
//     */
//    private class ModelStubAcceptingPersonAdded extends ModelStub {
//        final ArrayList<Task> personsAdded = new ArrayList<>();
//
//        @Override
//        public boolean hasPerson(Task task) {
//            requireNonNull(task);
//            return personsAdded.stream().anyMatch(task::isSamePerson);
//        }
//
//        @Override
//        public void addPerson(Task task) {
//            requireNonNull(task);
//            personsAdded.add(task);
//        }
//
//        @Override
//        public ReadOnlyCalendarAddressBook getCalendarAddressBook() {
//            return new CalendarCalendarAddressBook();
//        }
//    }
//
//}
