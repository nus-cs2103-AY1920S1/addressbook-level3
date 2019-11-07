package unrealunity.visit.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static unrealunity.visit.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import unrealunity.visit.commons.core.GuiSettings;
import unrealunity.visit.logic.commands.exceptions.CommandException;
import unrealunity.visit.model.Model;
import unrealunity.visit.model.ReadOnlyAddressBook;
import unrealunity.visit.model.ReadOnlyUserPrefs;
import unrealunity.visit.model.UserPrefs;
import unrealunity.visit.model.appointment.Appointment;
import unrealunity.visit.model.appointment.AppointmentTable;
import unrealunity.visit.model.person.Person;

public class ReminderCommandTest {
    @Test
    public void constructor_nullDescription_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ReminderCommand(null, 0));
    }

    @Test
    public void execute_correctReminder_reminderSuccessful() throws Exception {
        ReminderCommandTest.ModelStubWithAppointmentTable modelStub = new ReminderCommandTest
                .ModelStubWithAppointmentTable();

        CommandResult commandResult = new ReminderCommand("test", 1).execute(modelStub);

        assertEquals(String.format(ReminderCommand.MESSAGE_SUCCESS, "test", 1), commandResult.getFeedbackToUser());
        assertEquals(
                AppointmentTable.getDefaultAppointments().addAppointment(0, "test", 1),
                modelStub.getUserPrefs().getAppointmentTable()
        );
    }

    @Test
    public void equals() {
        ReminderCommand reminderCommand = new ReminderCommand("test1", 1);
        ReminderCommand reminderCommand2 = new ReminderCommand("test2", 2);

        assertTrue(reminderCommand.equals(reminderCommand));
        assertTrue(reminderCommand2.equals(reminderCommand2));

        assertFalse(reminderCommand.equals(1));
        assertFalse(reminderCommand2.equals(null));

        assertFalse(reminderCommand.equals(reminderCommand2));
    }

    private class ModelStubWithAppointmentTable extends ModelStub {
        final UserPrefs userPrefs = new UserPrefs();

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            return userPrefs;
        }

        @Override
        public void addAppointment(int type, String description, int days) throws CommandException {
            userPrefs.addAppointment(type, description, days);
        }
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
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addAppointment(int type, String description, int days) throws CommandException {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteAppointment(String description, int days) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortAppointments() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String outputAppointments() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetAppointments() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addAlias(String alias, String aliasTo) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean removeAlias(String alias) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String applyAlias(String commandText) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String getAliases(boolean reusable) {
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
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Appointment> getFilteredAppointmentList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredAppointmentList(Predicate<Appointment> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }
}
