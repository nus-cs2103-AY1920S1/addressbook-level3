package dukecooks.logic.commands.dashboard;

import static java.util.Objects.requireNonNull;

import java.util.List;

import dukecooks.commons.core.Event;
import dukecooks.commons.core.Messages;
import dukecooks.commons.core.index.Index;
import dukecooks.logic.commands.CommandResult;
import dukecooks.logic.commands.DoneCommand;
import dukecooks.logic.commands.exceptions.CommandException;
import dukecooks.model.Model;
import dukecooks.model.dashboard.components.Dashboard;
import dukecooks.model.dashboard.components.TaskStatus;

/**
 * Marks a task as complete into DukeCooks
 */
public class DoneTaskCommand extends DoneCommand {

    public static final String COMMAND_WORD = "done";
    public static final String VARIANT_WORD = "task";

    private static final String MESSAGE_DONE_TASK_SUCCESS = "Task marked as complete";
    private static final String MESSAGE_DONE_FIVE_SUCCESS = "Congrats! You've completed 5 new tasks!";

    private final Index targetIndex;

    public DoneTaskCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Dashboard> lastShownList = model.getFilteredDashboardList();

        // Navigate to dashboard tab
        Event event = Event.getInstance();
        event.set("dashboard", "all");

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        Dashboard taskToMark = lastShownList.get(targetIndex.getZeroBased());

        if (taskToMark.getTaskStatus().getDoneStatus()) {

            throw new CommandException(Messages.MESSAGE_TASK_IS_ALREADY_MARKED_AS_COMPLETE);
        }

        Dashboard createDoneTask = createDoneTask(taskToMark);
        model.doneDashboard(createDoneTask);

        model.updateFilteredDashboardList(Dashboard::isValidDashboard);

        if (model.checkForPrize(lastShownList)) {

            model.changeDashboard(lastShownList);
            return new CommandResult(MESSAGE_DONE_FIVE_SUCCESS, true, false, false, false);

        } else {
            return new CommandResult(MESSAGE_DONE_TASK_SUCCESS);
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
