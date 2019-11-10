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
import seedu.address.logic.commands.calendar.AddTaskCommand;
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
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TaskBuilder;

public class AddTaskCommandTest {
    @Test
    public void constructor_nullTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddTaskCommand(null));
    }

    @Test
    public void execute_taskAcceptedByModel_addSuccessful() throws Exception {
        AddTaskCommandTest.ModelStubAcceptingTaskAdded modelStub = new AddTaskCommandTest.ModelStubAcceptingTaskAdded();
        Task validTask = new TaskBuilder().build();
        CommandResult commandResult = new AddTaskCommand(validTask).execute(modelStub);

        assertEquals(String.format(AddTaskCommand.MESSAGE_SUCCESS, validTask), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validTask), modelStub.tasksAdded);
    }

    @Test
    public void execute_duplicateTask_throwsCommandException() {
        Task validTask = new TaskBuilder().build();
        AddTaskCommand addTaskCommand = new AddTaskCommand(validTask);
        ModelStub modelStub = new ModelStubWithTask(validTask);

        assertThrows(CommandException.class,
            AddTaskCommand.MESSAGE_DUPLICATE_TASKS, () -> addTaskCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
        ArrayList<Person> testListAlice = new ArrayList<>();
        testListAlice.add(alice);
        ArrayList<Person> testListBob = new ArrayList<>();
        testListAlice.add(bob);
        AddCommand addAliceCommand = new AddCommand(testListAlice);
        AddCommand addBobCommand = new AddCommand(testListBob);
        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(testListAlice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
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
        public void addTask(Task task) {
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
        public void setReminder(Reminder reminders, Reminder editedReminders) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteReminder(Reminder reminders) {
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

        /*@Override
        public ReadOnlyCalendar getCalendar() {
        }*/

        public void setTask(Task target, Task editedTask) {

        }

        @Override
        public ObservableList<Task> getFilteredTaskList() {
            return null;
        }

        @Override
        public void updateFilteredTaskList(Predicate<Task> predicate) {

        }

        @Override
        public void updateFilteredEarningsList(Predicate<Earnings> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasTask(Task task) {
            return false;
        }

        @Override
        public void deleteTask(Task target) {

        }

        @Override
        public void addReminder(Reminder reminder) {

        }

        @Override
        public boolean hasReminder(Reminder reminders) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredReminderList(Predicate<Reminder> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredCalendarList() {
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
     * A Model stub that contains a single task.
     */
    private class ModelStubWithTask extends ModelStub {
        private final Task task;

        ModelStubWithTask(Task task) {
            requireNonNull(task);
            this.task = task;
        }

        @Override
        public boolean hasTask(Task task) {
            requireNonNull(task);
            return this.task.isSameTask(task);
        }
    }

    /**
     * A Model stub that always accept the task being added.
     */
    private class ModelStubAcceptingTaskAdded extends ModelStub {
        final ArrayList<Task> tasksAdded = new ArrayList<>();

        @Override
        public boolean hasTask(Task task) {
            requireNonNull(task);
            return tasksAdded.stream().anyMatch(task::isSameTask);
        }

        @Override
        public void addTask(Task task) {
            requireNonNull(task);
            tasksAdded.add(task);
        }

        @Override
        public ReadOnlyTutorAid getTutorAid() {
            return new TutorAid();
        }
    }
}
