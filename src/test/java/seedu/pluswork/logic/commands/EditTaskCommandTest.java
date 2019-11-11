package seedu.pluswork.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.pluswork.logic.commands.CommandTestUtil.TASK_DESC_DEADLINE;
import static seedu.pluswork.logic.commands.CommandTestUtil.TASK_DESC_FINANCE;
import static seedu.pluswork.logic.commands.CommandTestUtil.TASK_DESC_PUBLICITY;
import static seedu.pluswork.logic.commands.CommandTestUtil.VALID_TAG_FINANCE;
import static seedu.pluswork.logic.commands.CommandTestUtil.VALID_TASK_NAME_FINANCE;
import static seedu.pluswork.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.pluswork.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.pluswork.logic.commands.CommandTestUtil.showTaskAtIndex;
import static seedu.pluswork.testutil.TypicalIndexes.INDEX_FIFTH_TASK;
import static seedu.pluswork.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.pluswork.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.pluswork.testutil.TypicalTasksMembers.getTypicalProjectDashboard;

import org.junit.jupiter.api.Test;

import seedu.pluswork.commons.core.Messages;
import seedu.pluswork.commons.core.index.Index;
import seedu.pluswork.logic.commands.task.EditTaskCommand;
import seedu.pluswork.logic.commands.task.EditTaskCommand.EditTaskDescriptor;
import seedu.pluswork.logic.commands.universal.ClearCommand;
import seedu.pluswork.model.Model;
import seedu.pluswork.model.ModelManager;
import seedu.pluswork.model.ProjectDashboard;
import seedu.pluswork.model.UserPrefs;
import seedu.pluswork.model.UserSettings;
import seedu.pluswork.model.task.Task;
import seedu.pluswork.testutil.EditTaskDescriptorBuilder;
import seedu.pluswork.testutil.TaskBuilder;

/**
 * Contains integration tests (interaction with the Model,
 * UndoCommand and RedoCommand) and unit tests for EditTaskCommand.
 */
public class EditTaskCommandTest {

    private Model model = new ModelManager(getTypicalProjectDashboard(), new UserPrefs(), new UserSettings());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Task editedTask = new TaskBuilder().build(); // no deadline
        EditTaskCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder(editedTask).build();
        EditTaskCommand editTaskCommand = new EditTaskCommand(INDEX_FIRST_TASK, descriptor);

        String expectedMessage = String.format(EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask);

        Model expectedModel = new ModelManager(new ProjectDashboard(
                model.getProjectDashboard()), new UserPrefs(), new UserSettings());
        expectedModel.setTask(model.getFilteredTasksList().get(0), editedTask);

        // additionally tests if a deadline is successfully removed when editing (deadline -> null)
        assertCommandSuccess(editTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastTask = Index.fromOneBased(model.getFilteredTasksList().size());
        Task firstTask = model.getFilteredTasksList().get(INDEX_FIRST_TASK.getZeroBased());

        TaskBuilder taskInList = new TaskBuilder(firstTask);
        Task editedTask = taskInList.withName(VALID_TASK_NAME_FINANCE)
                .withTags(VALID_TAG_FINANCE).withDeadline(null).build();

        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withName(VALID_TASK_NAME_FINANCE)
                .withTags(VALID_TAG_FINANCE).build();
        EditTaskCommand editTaskCommand = new EditTaskCommand(INDEX_FIRST_TASK, descriptor);

        String expectedMessage = String.format(EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask);

        Model expectedModel = new ModelManager(new ProjectDashboard(
                model.getProjectDashboard()), new UserPrefs(), new UserSettings());
        expectedModel.setTask(firstTask, editedTask);

        assertCommandSuccess(editTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        Task firstTask = model.getFilteredTasksList().get(INDEX_FIRST_TASK.getZeroBased());
        EditTaskCommand editTaskCommand = new EditTaskCommand(INDEX_FIRST_TASK,
                new EditTaskDescriptorBuilder().withDeadline(firstTask.getDeadline()).build());
        Task editedTask = model.getFilteredTasksList().get(INDEX_FIRST_TASK.getZeroBased());

        String expectedMessage = String.format(EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask);

        Model expectedModel = new ModelManager(new ProjectDashboard(
                model.getProjectDashboard()), new UserPrefs(), new UserSettings());

        assertCommandSuccess(editTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showTaskAtIndex(model, INDEX_FIRST_TASK);

        Task taskInFilteredList = model.getFilteredTasksList().get(INDEX_FIRST_TASK.getZeroBased());
        Task editedTask = new TaskBuilder(taskInFilteredList).withName(VALID_TASK_NAME_FINANCE).build();
        EditTaskCommand editTaskCommand = new EditTaskCommand(INDEX_FIRST_TASK,
                new EditTaskDescriptorBuilder().withName(VALID_TASK_NAME_FINANCE)
                        .withDeadline(taskInFilteredList.getDeadline()).build());

        String expectedMessage = String.format(EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask);

        Model expectedModel = new ModelManager(new ProjectDashboard(
                model.getProjectDashboard()), new UserPrefs(), new UserSettings());
        expectedModel.setTask(model.getFilteredTasksList().get(0), editedTask);

        assertCommandSuccess(editTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateTaskUnfilteredList_failure() {
        Task firstTask = model.getFilteredTasksList().get(INDEX_FIRST_TASK.getZeroBased());
        EditTaskCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder(firstTask).build();
        EditTaskCommand editTaskCommand = new EditTaskCommand(INDEX_FIFTH_TASK, descriptor);

        assertCommandFailure(editTaskCommand, model, EditTaskCommand.MESSAGE_DUPLICATE_TASK);
    }

    @Test
    public void execute_duplicateTaskFilteredList_failure() {
        showTaskAtIndex(model, INDEX_FIRST_TASK);

        // edit task in filtered list into a duplicate in address book
        Task taskInList = model.getProjectDashboard().getTaskList().get(INDEX_SECOND_TASK.getZeroBased());
        EditTaskCommand editTaskCommand = new EditTaskCommand(INDEX_FIRST_TASK,
                new EditTaskDescriptorBuilder(taskInList).build());

        assertCommandFailure(editTaskCommand, model, EditTaskCommand.MESSAGE_DUPLICATE_TASK);
    }

    @Test
    public void execute_invalidTaskIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTasksList().size() + 1);
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withName(VALID_TASK_NAME_FINANCE).build();
        EditTaskCommand editTaskCommand = new EditTaskCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editTaskCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidTaskIndexFilteredList_failure() {
        showTaskAtIndex(model, INDEX_FIRST_TASK);
        Index outOfBoundIndex = INDEX_SECOND_TASK;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getProjectDashboard().getTaskList().size());

        EditTaskCommand editTaskCommand = new EditTaskCommand(outOfBoundIndex,
                new EditTaskDescriptorBuilder().withName(VALID_TASK_NAME_FINANCE).build());

        assertCommandFailure(editTaskCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditTaskCommand standardCommand = new EditTaskCommand(INDEX_FIRST_TASK, TASK_DESC_FINANCE);

        // same values -> returns true
        EditTaskCommand.EditTaskDescriptor copyDescriptor = new EditTaskDescriptor(TASK_DESC_FINANCE);
        EditTaskCommand commandWithSameValues = new EditTaskCommand(INDEX_FIRST_TASK, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditTaskCommand(INDEX_SECOND_TASK, TASK_DESC_FINANCE)));

        // different deadline -> returns false
        assertFalse(standardCommand.equals(new EditTaskCommand(INDEX_FIRST_TASK, TASK_DESC_DEADLINE)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditTaskCommand(INDEX_FIRST_TASK, TASK_DESC_PUBLICITY)));
    }

}
