package seedu.module.logic.commands.deadlinecommands;

import seedu.module.commons.core.index.Index;
import seedu.module.logic.commands.CommandResult;
import seedu.module.logic.commands.exceptions.CommandException;
import seedu.module.model.Model;
import seedu.module.model.module.Deadline;
import seedu.module.model.module.TrackedModule;
import seedu.module.model.module.exceptions.DeadlineParseException;

/**
 * Edits deadline time of a module.
 */
public class EditDeadlineTimeCommand extends EditDeadlineCommand {
    private Index index;
    private String time;
    private int taskListNum;
    private Deadline deadline;

    public EditDeadlineTimeCommand(Index index, String time, int taskListNum) {
        this.index = index;
        this.time = time;
        this.taskListNum = taskListNum;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        TrackedModule moduleToEditTime = model.getTrackedModuleByIndex(model, index);
        if (taskListNum <= 0 || taskListNum > moduleToEditTime.getDeadlineList().size()) {
            throw new CommandException(DeadlineCommand.MESSAGE_TASK_LIST_NUMBER_NOT_FOUND);
        }
        deadline = moduleToEditTime.getDeadlineList().get(taskListNum - 1);
        try {
            deadline.editTime(time);
        } catch (DeadlineParseException e) {
            throw new CommandException(e.getMessage());
        }

        model.updateFilteredModuleList(Model.PREDICATE_SHOW_ALL_MODULES);
        model.showAllTrackedModules();

        return new CommandResult(generateSuccessMessage(moduleToEditTime),
                false, true, false);
    }

    /**
     * Generates a command execution success message based on whether the time of deadline task is edited in
     * {@code moduleToEditTime}.
     */
    private String generateSuccessMessage(TrackedModule moduleToEditTime) {
        String message = !deadline.getDescription().isEmpty() ? MESSAGE_EDIT_DEADLINE_SUCCESS
                : MESSAGE_EDIT_DEADLINE_FAIL;
        return String.format(message, moduleToEditTime.getModuleCode() + " " + moduleToEditTime.getTitle());
    }
}
