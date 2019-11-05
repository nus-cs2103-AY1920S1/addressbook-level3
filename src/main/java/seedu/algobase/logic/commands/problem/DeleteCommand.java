package seedu.algobase.logic.commands.problem;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.logic.parser.CliSyntax.FLAG_FORCE;

import java.util.List;

import seedu.algobase.commons.core.Messages;
import seedu.algobase.commons.core.index.Index;
import seedu.algobase.logic.CommandHistory;
import seedu.algobase.logic.commands.Command;
import seedu.algobase.logic.commands.CommandResult;
import seedu.algobase.logic.commands.exceptions.CommandException;
import seedu.algobase.model.Model;
import seedu.algobase.model.problem.Problem;

/**
 * Deletes a Problem identified using its displayed index from the algobase.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "deleteprob";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the Problem identified by the index number used in the displayed Problem list.\n"
            + "Parameters:\n"
            + "INDEX (must be a positive integer)\n"
            + "Example:\n"
            + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PROBLEM_SUCCESS = "Problem [%1$s] deleted.";
    public static final String MESSAGE_PROBLEM_USED_IN_PLAN = "Problem [%1$s] is used in existing plans. "
        + "Add " + FLAG_FORCE + " to the end of your command if you want to delete it from all plans.";

    private final Index targetIndex;
    private final boolean isForced;

    public DeleteCommand(Index targetIndex, boolean isForced) {
        this.targetIndex = targetIndex;
        this.isForced = isForced;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Problem> lastShownList = model.getFilteredProblemList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PROBLEM_DISPLAYED_INDEX);
        }

        Problem problemToDelete = lastShownList.get(targetIndex.getZeroBased());
        if (model.checkIsProblemUsed(problemToDelete)) {
            if (isForced) {
                model.removeProblemFromAllPlans(problemToDelete);
            } else {
                throw new CommandException(String.format(MESSAGE_PROBLEM_USED_IN_PLAN, problemToDelete.getName()));
            }
        }
        model.deleteProblem(problemToDelete);

        return new CommandResult(String.format(MESSAGE_DELETE_PROBLEM_SUCCESS, problemToDelete.getName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }
}
