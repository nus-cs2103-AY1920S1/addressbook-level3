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
import unrealunity.visit.model.AliasTable;
import unrealunity.visit.model.Model;
import unrealunity.visit.model.ReadOnlyAddressBook;
import unrealunity.visit.model.ReadOnlyUserPrefs;
import unrealunity.visit.model.UserPrefs;
import unrealunity.visit.model.appointment.Appointment;
import unrealunity.visit.model.person.Person;

public class UnaliasCommandTest {

    @Test
    public void constructor_nullAlias_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new UnaliasCommand(null));
    }

    @Test
    public void execute_correctAlias_unaliasSuccessful() throws Exception {
        UnaliasCommandTest.ModelStubWithAliasTable modelStub = new UnaliasCommandTest.ModelStubWithAliasTable();

        CommandResult commandResult = new UnaliasCommand("h").execute(modelStub);
        AliasTable expectedResult = AliasTable.getDefaultAliasTable();
        expectedResult.removeAlias("h");

        assertEquals(String.format(UnaliasCommand.MESSAGE_SUCCESS, "h"), commandResult.getFeedbackToUser());
        assertEquals(
                expectedResult,
                modelStub.getUserPrefs().getAliasTable()
        );
    }

    @Test
    public void equals() {
        UnaliasCommand unaliasExitCommand = new UnaliasCommand("test1");
        UnaliasCommand unaliasHelpCommand = new UnaliasCommand("test2");

        assertTrue(unaliasExitCommand.equals(unaliasExitCommand));
        assertTrue(unaliasHelpCommand.equals(unaliasHelpCommand));

        assertFalse(unaliasExitCommand.equals(1));
        assertFalse(unaliasHelpCommand.equals(null));

        assertFalse(unaliasExitCommand.equals(unaliasHelpCommand));
    }

    private class ModelStubWithAliasTable extends ModelStub {
        final UserPrefs userPrefs = new UserPrefs();

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            return userPrefs;
        }

        @Override
        public boolean removeAlias(String alias) {
            return userPrefs.removeAlias(alias);
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
        public void addAppointment(int type, String description, int days) {
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
