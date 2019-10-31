package dukecooks.logic.commands.dashboard;

import static java.util.Objects.requireNonNull;

import java.util.List;

import dukecooks.commons.core.Messages;
import dukecooks.commons.core.index.Index;
import dukecooks.logic.commands.Command;
import dukecooks.logic.commands.CommandResult;
import dukecooks.logic.commands.exceptions.CommandException;
import dukecooks.model.Model;
import dukecooks.model.dashboard.components.Dashboard;
import dukecooks.model.dashboard.components.TaskStatus;

/**
 * Adds a task into Duke Cooks
 */
public class DoneTaskCommand extends Command {

    public static final String COMMAND_WORD = "done";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the task identified by the index number used in the displayed task list as complete. \n"
            + "Parameters: INDEX (must be a positive integer)\n";

    public static final String MESSAGE_DONE_TASK_SUCCESS = "Task marked as complete";
    public static final String MESSAGE_DONE_FIVE_SUCCESS = "Congrats! You've completed 5 new tasks!";

    private final Index targetIndex;

    public DoneTaskCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Dashboard> lastShownList = model.getFilteredDashboardList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        Dashboard taskToMark = lastShownList.get(targetIndex.getZeroBased());

        if (taskToMark.getTaskStatus().getDoneStatus()) {

            throw new CommandException(Messages.MESSAGE_TASK_IS_ALREADY_MARKED_AS_COMPLETE);
        }
        Dashboard createDoneTask = createDoneTask(taskToMark);
        model.doneDashboard(createDoneTask);

        if (model.checkForPrize(lastShownList)) {

            model.changeDashboard(lastShownList);
            return new CommandResult(MESSAGE_DONE_FIVE_SUCCESS, true, false, false);

        } else {
            return new CommandResult(String.format(MESSAGE_DONE_TASK_SUCCESS, createDoneTask));

        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DoneTaskCommand // instanceof handles nulls
                && targetIndex.equals(((DoneTaskCommand) other).targetIndex)); // state check
    }

    /**
     * Creates and returns a {@code Dashboard} that is marked as done.
     */
    private static Dashboard createDoneTask(Dashboard key) {
        assert key != null;

        TaskStatus status = new TaskStatus("RECENTLY COMPLETED");
        return new Dashboard(key.getDashboardName(), key.getTaskDate(), status);
    }

}
