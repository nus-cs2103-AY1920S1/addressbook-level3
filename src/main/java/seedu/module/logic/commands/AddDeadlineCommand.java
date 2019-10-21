package seedu.module.logic.commands;

import static seedu.module.logic.parser.CliSyntax.PREFIX_DEADLINE;

import java.util.List;

import seedu.module.commons.core.Messages;

import seedu.module.commons.core.index.Index;
import seedu.module.logic.commands.exceptions.CommandException;
import seedu.module.model.Model;
import seedu.module.model.module.Deadline;
import seedu.module.model.module.TrackedModule;


/**
 * Adds deadline to be module.
 */
public class AddDeadlineCommand extends Command {

    public static final String COMMAND_WORD = "deadline";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a deadline to a specific module. "
            + "Parameters: "
            + "INDEX (must be a positive integer) "
            + PREFIX_DEADLINE + "DESCRIPTION\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DEADLINE + " quiz submission /by 2/2/2019 2359";

    public static final String MESSAGE_ADD_DEADLINE_SUCCESS = "Added deadline to Module: %1$s";
    public static final String MESSAGE_DELETE_DEADLINE_SUCCESS = "Removed deadline from module: %1$s";

    private final Index index;
    private final Deadline deadline;

    public AddDeadlineCommand(Index index, Deadline deadline) {
        this.index = index;
        this.deadline = deadline;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<TrackedModule> lastShownList = model.getFilteredModuleList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MODULE_DISPLAYED_INDEX);
        }

        TrackedModule moduleToEdit = lastShownList.get(index.getZeroBased());
        TrackedModule editedModule = lastShownList.get(index.getZeroBased());
        editedModule.setDeadline(deadline);

        model.setModule(moduleToEdit, editedModule);
        model.updateFilteredModuleList(Model.PREDICATE_SHOW_ALL_MODULES);
        model.displayTrackedList();

        return new CommandResult(generateSuccessMessage(editedModule));
    }

    /**
     * Generates a command execution success message based on whether the remark is added to or removed from
     * {@code moduleToEdit}.
     */
    private String generateSuccessMessage(TrackedModule moduleToEdit) {
        String message = !deadline.getValue().isEmpty() ? MESSAGE_ADD_DEADLINE_SUCCESS
                : MESSAGE_DELETE_DEADLINE_SUCCESS;
        return String.format(message, moduleToEdit);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof AddDeadlineCommand)) {
            return false;
        }
        AddDeadlineCommand e = (AddDeadlineCommand) other;
        return index.equals(e.deadline);
    }
}
