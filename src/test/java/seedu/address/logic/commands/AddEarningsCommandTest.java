package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyTutorAid;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.TutorAid;
import seedu.address.model.commands.CommandObject;
import seedu.address.model.earnings.Earnings;
import seedu.address.model.note.Notes;
import seedu.address.model.person.Person;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.task.Task;
import seedu.address.testutil.EarningsBuilder;

public class AddEarningsCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddEarningsCommand(null));
    }

    @Test
    public void execute_earningsAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingEarningsAdded modelStub = new ModelStubAcceptingEarningsAdded();
        Earnings validEarnings = new EarningsBuilder().build();

        CommandResult commandResult = new AddEarningsCommand(validEarnings).execute(modelStub);

        assertEquals(String.format(AddEarningsCommand.MESSAGE_SUCCESS, validEarnings),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validEarnings), modelStub.earningsAdded);
    }

    @Test
    public void execute_duplicateEarnings_throwsCommandException() {
        Earnings validEarnings = new EarningsBuilder().build();
        AddEarningsCommand addEarningsCommand = new AddEarningsCommand(validEarnings);
        ModelStub modelStub = new ModelStubWithEarnings(validEarnings);

        assertThrows(CommandException.class,
                AddEarningsCommand.MESSAGE_DUPLICATE_EARNINGS, () -> addEarningsCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Earnings earnings1 = new EarningsBuilder().withDate("02/02/2019").build();
        Earnings earnings2 = new EarningsBuilder().withDate("03/03/2020").build();
        AddEarningsCommand addEarnings1Command = new AddEarningsCommand(earnings1);
        AddEarningsCommand addEarnings2Command = new AddEarningsCommand(earnings2);

        // same object -> returns true
        assertTrue(addEarnings1Command.equals(addEarnings1Command));

        // same values -> returns true
        AddEarningsCommand addEarnings1CommandCopy = new AddEarningsCommand(earnings1);
        assertTrue(addEarnings1Command.equals(addEarnings1CommandCopy));

        // different types -> returns false
        assertFalse(addEarnings1Command.equals(1));

        // null -> returns false
        assertFalse(addEarnings1Command.equals(null));

        // different earnings -> returns false
        assertFalse(addEarnings1Command.equals(addEarnings2Command));
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
        public void deleteEarnings(Earnings earnings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String getSavedCommand() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void saveCommand(String command) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredCommandsList(Predicate<CommandObject> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredCalendarList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCommands(CommandObject target, CommandObject editedCommands) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteCommand(CommandObject command) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addCommand(CommandObject command) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasCommand(CommandObject command) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<CommandObject> getFilteredCommandsList() {
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
        public void setReminder(Reminder reminder, Reminder editedReminder) {
            throw new AssertionError("This method should not be called.");
        };

        @Override
        public boolean hasReminder(Reminder reminder) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addReminder(Reminder reminder) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteReminder(Reminder reminder) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getTutorAidFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTutorAidFilePath(Path tutorAidFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addEarnings(Earnings earnings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addNotes(Notes notes) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setVersionedTutorAid(ReadOnlyTutorAid newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyTutorAid getTutorAid() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasEarnings(Earnings earnings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void saveToMap(String key, ArrayList<Earnings> list) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void saveListToMap(String key, Earnings earnings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public HashMap<String, ArrayList<Earnings>> getMap() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasNotes(Notes notes) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteNotes(Notes target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setNotes(Notes target, Notes editedNote) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setEarnings(Earnings target, Earnings editedEarnings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Earnings> getFilteredEarningsList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Reminder> getFilteredReminderList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Notes> getFilteredNotesList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredNotesList(Predicate<Notes> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addTask(Task task) {
            throw new AssertionError("This method should not be called.");
        }


        @Override
        public boolean userHasLoggedIn() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void isLoggedIn() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void isLoggedOut() {
            throw new AssertionError("This method should not be called.");
        }


        public void setTask(Task target, Task editedTask) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Task> getFilteredTaskList() {
            return null;
        }

        @Override
        public void updateFilteredTaskList(Predicate<Task> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredEarningsList(Predicate<Earnings> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredReminderList(Predicate<Reminder> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasTask(Task task) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteTask(Task target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitTutorAid() {

        }

        @Override
        public boolean canUndoTutorAid() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoTutorAid() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoTutorAid() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoTutorAid() {
            throw new AssertionError("This method should not be called.");
        }

    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithEarnings extends ModelStub {
        private final Earnings earnings;

        ModelStubWithEarnings(Earnings earnings) {
            requireNonNull(earnings);
            this.earnings = earnings;
        }

        @Override
        public boolean hasEarnings(Earnings earnings) {
            requireNonNull(earnings);
            return this.earnings.isSameEarnings(earnings);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingEarningsAdded extends ModelStub {
        final ArrayList<Earnings> earningsAdded = new ArrayList<>();

        @Override
        public boolean hasEarnings(Earnings earnings) {
            requireNonNull(earnings);
            return earningsAdded.stream().anyMatch(earnings::isSameEarnings);
        }

        @Override
        public void addEarnings(Earnings earnings) {
            requireNonNull(earnings);
            earningsAdded.add(earnings);
        }

        @Override
        public ReadOnlyTutorAid getTutorAid() {
            return new TutorAid();
        }
    }
}
