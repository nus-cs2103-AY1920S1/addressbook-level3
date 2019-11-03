package seedu.module.logic.commands.deadlinecommands;
import static seedu.module.logic.parser.CliSyntax.PREFIX_ACTION;
import static seedu.module.logic.parser.CliSyntax.PREFIX_TASK_LIST_NUMBER;

import seedu.module.commons.core.index.Index;
import seedu.module.logic.commands.CommandResult;
import seedu.module.logic.commands.exceptions.CommandException;
import seedu.module.model.Model;
import seedu.module.model.module.TrackedModule;

/**
 * Marks a specified deadline task from a module's deadline list as 'In-Progress' with a dash.
 */
public class InProgressDeadlineCommand extends DeadlineCommand {

    public static final String COMMAND_WORD = "inProgress";

    public static final String MESSAGE_IN_PROGRESS_DEADLINE_SUCCESS = "Mark deadline task as 'In-Progress' "
            + "for module: %1$s";
    public static final String MESSAGE_IN_PROGRESS_DEADLINE_FAIL = "Unable to mark deadline task as 'In-Progress' "
            + "for module: %1$s";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the deadline task identified by the index number as 'in progress' with a dash.\n"
            + "Parameters: INDEX (must be a positive integer), \n"
            + "TASK(must be a positive integer) \n"
            + "Example: deadline 2 " + PREFIX_ACTION + " " + COMMAND_WORD + " " + PREFIX_TASK_LIST_NUMBER + " 1";

    private Index index;
    private int taskListNum;
    private TrackedModule moduleToMarkInProgress;

    public InProgressDeadlineCommand(Index index, int taskListNum) {
        this.index = index;
        this.taskListNum = taskListNum;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        TrackedModule moduleToMarkInProgress = model.getTrackedModuleByIndex(model, index);
        moduleToMarkInProgress.markDeadlineTaskAsInProgress(taskListNum - 1);

        model.updateFilteredModuleList(Model.PREDICATE_SHOW_ALL_MODULES);
        model.showAllTrackedModules();

        return new CommandResult(generateSuccessMessage(moduleToMarkInProgress),
                false, true, false);
    }

    /**
     * Generates a command execution success message based on whether the deadline task is marked as done from
     * {@code moduleToMarkInProgress}.
     */
    private String generateSuccessMessage(TrackedModule moduleToMarkInProgress) {
        String message = MESSAGE_IN_PROGRESS_DEADLINE_SUCCESS;
        return String.format(message, moduleToMarkInProgress);
    }
}
