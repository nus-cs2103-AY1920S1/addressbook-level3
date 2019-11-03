package seedu.module.logic.commands.deadlinecommands;
import static seedu.module.logic.parser.CliSyntax.PREFIX_ACTION;
import static seedu.module.logic.parser.CliSyntax.PREFIX_TASK_LIST_NUMBER;

import seedu.module.commons.core.index.Index;
import seedu.module.logic.commands.CommandResult;
import seedu.module.logic.commands.exceptions.CommandException;
import seedu.module.model.Model;
import seedu.module.model.module.TrackedModule;

/**
 * Marks a specified deadline task from a module's deadline list as 'Done' with a tick.
 */
public class DoneDeadlineCommand extends DeadlineCommand {

    public static final String COMMAND_WORD = "done";

    public static final String MESSAGE_DONE_DEADLINE_SUCCESS = "Marked deadline task as 'Done' for module: %1$s";
    public static final String MESSAGE_DONE_DEADLINE_FAIL = "Unable to mark deadline task as 'Done' for module: %1$s";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the deadline task identified by the index and task number as done.\n"
            + "Parameters: INDEX (must be a positive integer), \n"
            + "TASK(must be a positive integer) \n"
            + "Example: deadline 2 " + PREFIX_ACTION + " " + COMMAND_WORD + " " + PREFIX_TASK_LIST_NUMBER + " 1";

    private Index index;
    private int taskListNum;
    private TrackedModule moduleToMarkDone;

    public DoneDeadlineCommand(Index index, int taskListNum) {
        this.index = index;
        this.taskListNum = taskListNum;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        TrackedModule moduleToMarkDone = model.getTrackedModuleByIndex(model, index);
        moduleToMarkDone.markDeadlineTaskAsDone(taskListNum - 1);

        model.updateFilteredModuleList(Model.PREDICATE_SHOW_ALL_MODULES);
        model.showAllTrackedModules();

        return new CommandResult(generateSuccessMessage(moduleToMarkDone),
                false, true, false);
    }

    /**
     * Generates a command execution success message based on whether the deadline task is marked as done from
     * {@code moduleMarkDone}.
     */
    private String generateSuccessMessage(TrackedModule moduleToMarkDone) {
        String message = MESSAGE_DONE_DEADLINE_SUCCESS;
        return String.format(message, moduleToMarkDone);
    }
}
