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
import seedu.address.logic.commands.note.AddNotesCommand;
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
import seedu.address.testutil.NotesBuilder;

public class AddNotesCommandTest {
    @Test
    public void constructor_nullNotes_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddNotesCommand(null));
    }

    @Test
    public void execute_notesAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingNotesAdded modelStub = new ModelStubAcceptingNotesAdded();
        Notes validNotes = new NotesBuilder().build();

        CommandResult commandResult = new AddNotesCommand(validNotes).execute(modelStub);

        assertEquals(String.format(AddNotesCommand.MESSAGE_SUCCESS, validNotes),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validNotes), modelStub.notesAdded);
    }

    @Test
    public void execute_duplicateNotes_throwsCommandException() {
        Notes validNotes = new NotesBuilder().build();
        AddNotesCommand addNotesCommand = new AddNotesCommand(validNotes);
        ModelStub modelStub = new ModelStubWithNotes(validNotes);

        assertThrows(CommandException.class,
                AddNotesCommand.MESSAGE_DUPLICATE_NOTE, () -> addNotesCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Notes notesA = new NotesBuilder().withClassType("tut").build();
        Notes notesB = new NotesBuilder().withClassType("tut").build();
        AddNotesCommand addNotesACommand = new AddNotesCommand(notesA);
        AddNotesCommand addNotesBCommand = new AddNotesCommand(notesB);

        // same object -> returns true
        assertTrue(addNotesACommand.equals(addNotesACommand));

        // different types -> returns false
        assertFalse(addNotesACommand.equals(1));

        // null -> returns false
        assertFalse(addNotesACommand.equals(null));

        // different earnings -> returns false
        assertFalse(addNotesACommand.equals(addNotesBCommand));
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
        public void deleteNotes(Notes notes) {
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
        }

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
        public void deleteEarnings(Earnings earnings) {
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
        public void saveEarningsToMap(String key, Earnings earnings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeEarningsFromMap(String currentDay, Earnings earnings) {
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
    private class ModelStubWithNotes extends ModelStub {
        private final Notes notes;

        ModelStubWithNotes(Notes notes) {
            requireNonNull(notes);
            this.notes = notes;
        }

        @Override
        public boolean hasNotes(Notes notes) {
            requireNonNull(notes);
            return this.notes.isSameNote(notes);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingNotesAdded extends ModelStub {
        final ArrayList<Notes> notesAdded = new ArrayList<>();

        @Override
        public boolean hasNotes(Notes notes) {
            requireNonNull(notes);
            return notesAdded.stream().anyMatch(notes::isSameNote);
        }

        @Override
        public void addNotes(Notes notes) {
            requireNonNull(notes);
            notesAdded.add(notes);
        }

        @Override
        public ReadOnlyTutorAid getTutorAid() {
            return new TutorAid();
        }

    }
}
