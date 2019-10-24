package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.assignment.Assignment;

/**
 * Deletes an assignment identified using it's displayed index from the classroom.
 */
public class DeleteAssignmentCommand extends Command {

    public static final String COMMAND_WORD = "delassign";

    public static final String MESSAGE_USAGE = COMMAND_WORD
                                                   + ": Deletes the student identified by the index number "
                                                   + "used in the displayed student list.\n"
                                                   + "Parameters: INDEX (must be a positive integer)\n"
                                                   + "Example: " + COMMAND_WORD + " 1";

    private static final String MESSAGE_DELETE_ASSIGNMENT_SUCCESS = "Deleted Assignment: %1$s";

    private final Index targetIndex;

    public DeleteAssignmentCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Assignment> lastShownList = model.getFilteredAssignmentList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ASSIGNMENT_DISPLAYED_INDEX);
        }

        Assignment assignmentToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteAssignment(assignmentToDelete);
        model.saveState();
        return new CommandResult(String.format(MESSAGE_DELETE_ASSIGNMENT_SUCCESS, assignmentToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                   || (other instanceof DeleteCommand // instanceof handles nulls
                           && targetIndex.equals(((DeleteAssignmentCommand) other).targetIndex)); // state check
    }
}
