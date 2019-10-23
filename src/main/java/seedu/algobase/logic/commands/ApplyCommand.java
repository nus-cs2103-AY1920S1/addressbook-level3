package seedu.algobase.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.function.Predicate;

import seedu.algobase.commons.core.Messages;
import seedu.algobase.commons.core.index.Index;
import seedu.algobase.logic.commands.exceptions.CommandException;
import seedu.algobase.model.Model;
import seedu.algobase.model.problem.Problem;
import seedu.algobase.model.searchrule.problemsearchrule.ProblemSearchRule;

/**
 * Apply a find rule by specifying the index.
 */
public class ApplyCommand extends Command {

    public static final String COMMAND_WORD = "apply";
    // TODO: write message usage string for apply command
    public static final String MESSAGE_USAGE = "apply usage";
    public static final String MESSAGE_SUCCESS = "Applied find rule [%1$s].";

    private final Index targetIndex;

    public ApplyCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<ProblemSearchRule> lastShownList = model.getFilteredFindRuleList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_FIND_RULE_DISPLAYED_INDEX);
        }

        ProblemSearchRule findRuleToApply = lastShownList.get(targetIndex.getZeroBased());
        Predicate<Problem> findProblemPredicate = findRuleToApply.getFindProblemPredicate();
        model.updateFilteredProblemList(findProblemPredicate);
        return new CommandResult(String.format(MESSAGE_SUCCESS, findRuleToApply));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ApplyCommand // instanceof handles nulls
                && targetIndex.equals(((ApplyCommand) other).targetIndex)); // state check
    }

}
