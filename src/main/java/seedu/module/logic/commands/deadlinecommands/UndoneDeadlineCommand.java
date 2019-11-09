package seedu.module.logic.commands.deadlinecommands;

import static seedu.module.logic.parser.CliSyntax.PREFIX_ACTION;
import static seedu.module.logic.parser.CliSyntax.PREFIX_TASK_LIST_NUMBER;

import seedu.module.commons.core.index.Index;
import seedu.module.logic.commands.CommandResult;
import seedu.module.logic.commands.exceptions.CommandException;
import seedu.module.model.Model;
import seedu.module.model.module.TrackedModule;
import seedu.module.model.module.exceptions.DeadlineMarkException;

/**
 * Marks a specified deadline task from a module's deadline list as 'Undone' removing the tick or dash.
 */
public class UndoneDeadlineCommand extends DeadlineCommand {

    public static final String COMMAND_WORD = "undone";

    public static final String MESSAGE_UNDONE_DEADLINE_SUCCESS = "Marked deadline task as 'Undone' for module: "
            + "%1$s";
    public static final String MESSAGE_UNDONE_DEADLINE_FAIL = "Unable to mark deadline task as 'Undone' for module: "
            + "%1$s";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the deadline task identified by the index and task number as undone.\n"
            + "Parameters: MODULE_INDEX (must be a positive integer), "
            + "TASK(must be a positive integer) \n"
            + "Example: deadline 2 " + PREFIX_ACTION + COMMAND_WORD + " " + PREFIX_TASK_LIST_NUMBER + "1";

    private Index index;
    private int taskListNum;

    public UndoneDeadlineCommand(Index index, int taskListNum) {
        this.index = index;
        this.taskListNum = taskListNum;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        TrackedModule moduleToMarkUndone = model.getTrackedModuleByIndex(model, index);
        if (taskListNum <= 0 || taskListNum > moduleToMarkUndone.getDeadlineList().size()) {
            throw new CommandException(DeadlineCommand.MESSAGE_TASK_LIST_NUMBER_NOT_FOUND);
        }
        try {
            moduleToMarkUndone.markDeadlineTaskAsUndone(taskListNum - 1);
        } catch (DeadlineMarkException e) {
            throw new CommandException(e.getMessage());
        }

        model.updateFilteredModuleList(Model.PREDICATE_SHOW_ALL_MODULES);
        model.showAllTrackedModules();

        return new CommandResult(generateSuccessMessage(moduleToMarkUndone),
                false, true, false);
    }

    /**
     * Generates a command execution success message based on whether the deadline task is marked as done from
     * {@code moduleToMarkDone}.
     */
    private String generateSuccessMessage(TrackedModule moduleToMarkUndone) {
        String message = MESSAGE_UNDONE_DEADLINE_SUCCESS;
        return String.format(message, moduleToMarkUndone.getModuleCode() + " " + moduleToMarkUndone.getTitle());
    }
}

