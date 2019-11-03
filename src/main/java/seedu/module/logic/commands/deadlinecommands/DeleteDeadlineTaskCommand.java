package seedu.module.logic.commands.deadlinecommands;

import static seedu.module.logic.parser.CliSyntax.PREFIX_ACTION;
import static seedu.module.logic.parser.CliSyntax.PREFIX_TASK_LIST_NUMBER;

import seedu.module.commons.core.index.Index;
import seedu.module.logic.commands.CommandResult;
import seedu.module.logic.commands.exceptions.CommandException;
import seedu.module.model.Model;
import seedu.module.model.module.TrackedModule;

/**
 * Deletes a specified deadline task from a module's deadline list.
 */
public class DeleteDeadlineTaskCommand extends DeadlineCommand {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_DELETE_DEADLINE_SUCCESS = "Deleted the deadline task from module: %1$s";
    public static final String MESSAGE_DELETE_DEADLINE_FAIL = "Unable to delete the deadline task from module: %1$s";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes Deadline task identified by the index number used in the displayed Module list.\n"
            + "Parameters: INDEX (must be a positive integer), \n"
            + "TASK(must be a positive integer) \n"
            + "Example: deadline 2 " + PREFIX_ACTION + " " + COMMAND_WORD + " " + PREFIX_TASK_LIST_NUMBER + " 2";

    private Index index;
    private int taskListNum;
    private TrackedModule moduleToDeleteDeadline;

    public DeleteDeadlineTaskCommand(Index index, int taskListNum) {
        this.index = index;
        this.taskListNum = taskListNum;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        TrackedModule moduleToDeleteDeadline = model.getTrackedModuleByIndex(model, index);
        moduleToDeleteDeadline.deleteDeadlineTask(taskListNum - 1);

        model.updateFilteredModuleList(Model.PREDICATE_SHOW_ALL_MODULES);
        model.showAllTrackedModules();

        return new CommandResult(generateSuccessMessage(moduleToDeleteDeadline),
                false, true, false);
    }

    /**
     * Generates a command execution success message based on whether the deadline task is removed from
     * {@code moduleToDeleteDeadline}.
     */
    private String generateSuccessMessage(TrackedModule moduleToDeleteDeadline) {
        String message = MESSAGE_DELETE_DEADLINE_SUCCESS;
        return String.format(message, moduleToDeleteDeadline);
    }
}
