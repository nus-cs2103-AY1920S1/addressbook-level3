package seedu.algobase.logic.commands.findrule;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.algobase.commons.core.Messages;
import seedu.algobase.commons.core.index.Index;
import seedu.algobase.logic.CommandHistory;
import seedu.algobase.logic.commands.Command;
import seedu.algobase.logic.commands.CommandResult;
import seedu.algobase.logic.commands.exceptions.CommandException;
import seedu.algobase.model.Model;
import seedu.algobase.model.searchrule.problemsearchrule.ProblemSearchRule;

/**
 * Deletes a Find Rule using its displayed index in the AlgoBase UI.
 */
public class DeleteFindRuleCommand extends Command {

    public static final String COMMAND_WORD = "deletefindrule";
    public static final String SHORT_COMMAND_WORD = "dfr";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the find rule identified by the index number "
            + "used in the displayed Find Rules list.\n"
            + "Parameters:\n"
            + "INDEX (must be a positive integer)\n"
            + "Example:\n"
            + COMMAND_WORD + " 1\n";
    public static final String MESSAGE_SUCCESS = "Find Rule [%1$s] deleted.";

    private final Index targetIndex;

    public DeleteFindRuleCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<ProblemSearchRule> lastShownList = model.getFilteredFindRuleList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_FIND_RULE_DISPLAYED_INDEX);
        }

        ProblemSearchRule ruleToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteFindRule(ruleToDelete);
        return new CommandResult(String.format(MESSAGE_SUCCESS, ruleToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteFindRuleCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteFindRuleCommand) other).targetIndex)); // state check
    }
}
