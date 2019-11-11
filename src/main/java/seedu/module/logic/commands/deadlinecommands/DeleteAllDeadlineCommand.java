package seedu.module.logic.commands.deadlinecommands;

import static seedu.module.logic.parser.CliSyntax.PREFIX_ACTION;

import seedu.module.commons.core.index.Index;
import seedu.module.logic.commands.CommandResult;
import seedu.module.logic.commands.exceptions.CommandException;
import seedu.module.model.Model;
import seedu.module.model.module.TrackedModule;

/**
 * Deletes all the deadline tasks from a module's deadline list.
 */
public class DeleteAllDeadlineCommand extends DeadlineCommand {

    public static final String COMMAND_WORD = "deleteAll";

    public static final String MESSAGE_DELETE_ALL_DEADLINE_SUCCESS = "Deleted all the deadline tasks from module: %1$s";
    public static final String MESSAGE_DELETE_ALL_DEADLINE_FAIL = "Unable to delete all "
            + "deadline task from module: %1$s";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes all Deadline tasks in the displayed Module list.\n"
            + "Parameters: MODULE_INDEX (must be a positive integer), \n"
            + "Example: deadline 2 " + PREFIX_ACTION + COMMAND_WORD;

    private Index index;

    public DeleteAllDeadlineCommand(Index index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        TrackedModule moduleToDeleteDeadline = model.getTrackedModuleByIndex(model, index);
        moduleToDeleteDeadline.deleteAllDeadlineTasks();

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
        String message = MESSAGE_DELETE_ALL_DEADLINE_SUCCESS;
        return String.format(message, moduleToDeleteDeadline.getModuleCode() + " " + moduleToDeleteDeadline.getTitle());
    }
}
