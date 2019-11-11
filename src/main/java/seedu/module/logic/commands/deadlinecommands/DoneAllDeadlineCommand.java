package seedu.module.logic.commands.deadlinecommands;

import static seedu.module.logic.parser.CliSyntax.PREFIX_ACTION;

import seedu.module.commons.core.index.Index;
import seedu.module.logic.commands.CommandResult;
import seedu.module.logic.commands.exceptions.CommandException;
import seedu.module.model.Model;
import seedu.module.model.module.TrackedModule;

/**
 * Marks all deadline tasks from a module's deadline list as 'Done' with a tick.
 */
public class DoneAllDeadlineCommand extends DeadlineCommand {

    public static final String COMMAND_WORD = "doneAll";

    public static final String MESSAGE_DONE_ALL_DEADLINE_SUCCESS = "Marked all "
            + "deadline tasks as 'Done' for module: %1$s";
    public static final String MESSAGE_DONE_ALL_DEADLINE_FAIL = "Unable to mark all "
            + "deadline task as 'Done' for module: %1$s";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks all the deadline tasks as done.\n"
            + "Parameters: MODULE_INDEX (must be a positive integer), \n"
            + "Example: deadline 2 " + PREFIX_ACTION + COMMAND_WORD;

    private Index index;

    public DoneAllDeadlineCommand(Index index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        TrackedModule moduleToMarkDone = model.getTrackedModuleByIndex(model, index);
        moduleToMarkDone.markAllDone();

        model.updateFilteredModuleList(Model.PREDICATE_SHOW_ALL_MODULES);
        model.showAllTrackedModules();

        return new CommandResult(generateSuccessMessage(moduleToMarkDone),
                false, true, false);
    }

    /**
     * Generates a command execution success message based on whether all deadline tasks are marked as done from
     * {@code moduleToMarkDone}.
     */
    private String generateSuccessMessage(TrackedModule moduleToMarkDone) {
        String message = MESSAGE_DONE_ALL_DEADLINE_SUCCESS;
        return String.format(message, moduleToMarkDone.getModuleCode() + " " + moduleToMarkDone.getTitle());
    }
}

