package dukecooks.logic.commands.dashboard;

import static java.util.Objects.requireNonNull;

import java.util.List;

import dukecooks.commons.core.Event;
import dukecooks.commons.core.Messages;
import dukecooks.commons.core.index.Index;
import dukecooks.logic.commands.CommandResult;
import dukecooks.logic.commands.DeleteCommand;
import dukecooks.logic.commands.exceptions.CommandException;
import dukecooks.model.Model;
import dukecooks.model.dashboard.components.Dashboard;

/**
 * Deletes a Dashboard identified using it's displayed index from DukeCooks.
 */
public class DeleteTaskCommand extends DeleteCommand {

    public static final String VARIANT_WORD = "task";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the index number used in the displayed task list. \n"
            + "Parameters: INDEX (must be a positive integer)\n";

    private static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task: %1$s";

    private final Index targetIndex;

    public DeleteTaskCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Dashboard> lastShownList = model.getFilteredDashboardList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        Dashboard taskToDelete = lastShownList.get(targetIndex.getZeroBased());

        if (taskToDelete.getTaskStatus().getDoneStatus()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_IS_COMPLETE);
        }

        // Navigate to dashboard tab
        Event event = Event.getInstance();
        event.set("dashboard", "all");

        model.deleteDashboard(taskToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteTaskCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteTaskCommand) other).targetIndex)); // state check
    }
}
