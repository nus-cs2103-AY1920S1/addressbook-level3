package seedu.algobase.logic.commands.findrule;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.function.Predicate;

import seedu.algobase.commons.core.Messages;
import seedu.algobase.commons.core.index.Index;
import seedu.algobase.logic.CommandHistory;
import seedu.algobase.logic.commands.Command;
import seedu.algobase.logic.commands.CommandResult;
import seedu.algobase.logic.commands.exceptions.CommandException;
import seedu.algobase.model.Model;
import seedu.algobase.model.problem.Problem;
import seedu.algobase.model.searchrule.problemsearchrule.ProblemSearchRule;

/**
 * Applies a find rule by specifying the index.
 */
public class ApplyCommand extends Command {

    public static final String COMMAND_WORD = "apply";
    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Applies the find rule identified by the index number "
        + "used in the displayed Find Rules list.\n"
        + "Parameters:\n"
        + "INDEX (must be a positive integer)\n"
        + "Example:\n"
        + COMMAND_WORD + " 1\n";
    public static final String MESSAGE_SUCCESS = "Applied find rule [%1$s].";

    private final Index targetIndex;

    public ApplyCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<ProblemSearchRule> lastShownList = model.getFilteredFindRuleList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_FIND_RULE_DISPLAYED_INDEX);
        }

        ProblemSearchRule findRuleToApply = lastShownList.get(targetIndex.getZeroBased());
        Predicate<Problem> findProblemPredicate = findRuleToApply.getFindProblemPredicate();
        model.updateFilteredProblemList(findProblemPredicate);
        return new CommandResult(String.format(MESSAGE_SUCCESS, findRuleToApply.getName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ApplyCommand // instanceof handles nulls
                && targetIndex.equals(((ApplyCommand) other).targetIndex)); // state check
    }

}
