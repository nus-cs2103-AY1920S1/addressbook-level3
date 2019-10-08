package seedu.algobase.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.algobase.commons.core.Messages;
import seedu.algobase.commons.core.index.Index;
import seedu.algobase.logic.commands.exceptions.CommandException;
import seedu.algobase.model.Model;
import seedu.algobase.model.problem.Problem;

/**
 * Deletes a Problem identified using it's displayed index from the algobase.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the Problem identified by the index number used in the displayed Problem list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PROBLEM_SUCCESS = "Deleted Problem: %1$s";

    private final Index targetIndex;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Problem> lastShownList = model.getFilteredProblemList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PROBLEM_DISPLAYED_INDEX);
        }

        Problem problemToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteProblem(problemToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PROBLEM_SUCCESS, problemToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }
}
