package seedu.module.logic.commands.deadlineCommands;
import java.util.List;

import seedu.module.commons.core.Messages;

import seedu.module.commons.core.index.Index;
import seedu.module.logic.commands.CommandResult;
import seedu.module.logic.commands.exceptions.CommandException;
import seedu.module.model.Model;
import seedu.module.model.module.Deadline;
import seedu.module.model.module.TrackedModule;


/**
 * Adds deadline to be module.
 */
public class AddDeadlineCommand extends DeadlineCommand {

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

        TrackedModule moduleToAddDeadline = lastShownList.get(index.getZeroBased());
        moduleToAddDeadline.addDeadline(deadline);

        model.updateFilteredModuleList(Model.PREDICATE_SHOW_ALL_MODULES);
        model.displayTrackedList();

        return new CommandResult(generateSuccessMessage(moduleToAddDeadline));
    }

    /**
     * Generates a command execution success message based on whether the remark is added to or removed from
     * {@code moduleToEdit}.
     */
    private String generateSuccessMessage(TrackedModule moduleToAddDeadline) {
        String message = !deadline.getDescription().isEmpty() ? MESSAGE_ADD_DEADLINE_SUCCESS
                : MESSAGE_DELETE_DEADLINE_SUCCESS;
        return String.format(message, moduleToAddDeadline);
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
