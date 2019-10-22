package seedu.address.logic.commands.dashboard;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.dashboard.components.Dashboard;

import java.util.List;

import static java.util.Objects.requireNonNull;

public class DeleteTaskCommand extends DeleteCommand {

    public static final String COMMAND_WORD = "taskdelete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the index number used in the displayed task list. \n"
            + "Parameters: INDEX (must be a positive integer)\n";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task: %1$s";

    private final Index targetIndex;

    public DeleteTaskCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Dashboard> lastShownList = model.getFilteredDashboardList();

        if(targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        Dashboard taskToDelete = lastShownList.get(targetIndex.getZeroBased());
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
