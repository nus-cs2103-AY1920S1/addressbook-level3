package unrealunity.visit.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

public class SortAppointmentsCommandTest {
    @Test
    public void execute_correctSort_sortSuccessful() throws Exception {
        SortAppointmentsCommandTest.ModelStubWithAppointmentTable modelStub = new SortAppointmentsCommandTest
                .ModelStubWithAppointmentTable();

        new ReminderCommand("test", 99).execute(modelStub);
        new ReminderCommand("test2", 1).execute(modelStub);
        CommandResult commandResult = new SortAppointmentsCommand().execute(modelStub);

        assertEquals(String.format(SortAppointmentsCommand.MESSAGE_SUCCESS), commandResult.getFeedbackToUser());
        assertEquals(
                AppointmentTable.getDefaultAppointments().addAppointment(0, "test2", 1)
                .addAppointment(0, "test", 99),
                modelStub.getUserPrefs().getAppointmentTable()
        );
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

        @Override
        public void sortAppointments() {
            userPrefs.sortAppointments();
        }

        @Override
        public void updateFilteredAppointmentList(Predicate<Appointment> predicate) {
            // Do Nothing.
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
