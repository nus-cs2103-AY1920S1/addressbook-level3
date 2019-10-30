package seedu.address.logic.commands.employee;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyEventBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.distinctdate.DistinctDate;
import seedu.address.model.employee.Employee;
import seedu.address.model.event.Event;
import seedu.address.testutil.PersonBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Employee validEmployee = new PersonBuilder().build();

        CommandResult commandResult = new AddCommand(validEmployee).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validEmployee), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validEmployee), modelStub.employeesAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Employee validEmployee = new PersonBuilder().build();
        AddCommand addCommand = new AddCommand(validEmployee);
        ModelStub modelStub = new ModelStubWithPerson(validEmployee);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_PERSON, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Employee alice = new PersonBuilder().withName("Alice").build();
        Employee bob = new PersonBuilder().withName("Bob").build();
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

        // different employee -> returns false
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
        public void addEmployee(Employee employee) {
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
        public ObservableList<Employee> getFullListEmployees() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasEmployee(Employee employee) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteEmployee(Employee target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setEmployee(Employee target, Employee editedEmployee) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Employee> getFilteredEmployeeList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredEmployeeList(Predicate<Employee> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<DistinctDate> getEventDistinctDatesList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateEmployeeDistinctDateList(List<DistinctDate> list) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateEventDistinctDatesList(List<DistinctDate> list) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getEventBookFilePath() {
            return null;
        }

        @Override
        public void setEventBookFilePath(Path eventBookFilePath) {

        }

        @Override
        public void setEventBook(ReadOnlyEventBook eventBook) {

        }

        @Override
        public ReadOnlyEventBook getEventBook() {
            return null;
        }

        @Override
        public ObservableList<Event> getFullListEvents() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasEvent(Event event) {
            return false;
        }

        @Override
        public void deleteEvent(Event target) {

        }

        @Override
        public void addEvent(Event event) {

        }

        @Override
        public void setEvent(Event target, Event editedEvent) {

        }

        @Override
        public ObservableList<Event> getFilteredEventList() {
            return null;
        }

        @Override
        public void updateFilteredEventList(Predicate<Event> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Event> getFilteredScheduledEventList() {
            return null;
        }

        @Override
        public void updateFilteredScheduledEventList(Predicate<Event> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<DistinctDate> getEmployeeDistinctDatesList() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single employee.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final Employee employee;

        ModelStubWithPerson(Employee employee) {
            requireNonNull(employee);
            this.employee = employee;
        }

        @Override
        public boolean hasEmployee(Employee employee) {
            requireNonNull(employee);
            return this.employee.isSameEmployee(employee);
        }
    }

    /**
     * A Model stub that always accept the employee being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Employee> employeesAdded = new ArrayList<>();

        @Override
        public boolean hasEmployee(Employee employee) {
            requireNonNull(employee);
            return employeesAdded.stream().anyMatch(employee::isSameEmployee);
        }

        @Override
        public void addEmployee(Employee employee) {
            requireNonNull(employee);
            employeesAdded.add(employee);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }


    }

}
