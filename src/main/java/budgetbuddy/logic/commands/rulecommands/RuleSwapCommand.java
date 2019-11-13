package budgetbuddy.logic.commands.rulecommands;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;

import budgetbuddy.commons.core.Messages;
import budgetbuddy.commons.core.index.Index;
import budgetbuddy.logic.commands.Command;
import budgetbuddy.logic.commands.CommandCategory;
import budgetbuddy.logic.commands.CommandResult;
import budgetbuddy.logic.commands.exceptions.CommandException;
import budgetbuddy.model.Model;
import budgetbuddy.model.RuleManager;
import budgetbuddy.model.rule.exceptions.RuleNotFoundException;

/**
 * Swaps the position of two rules.
 */
public class RuleSwapCommand extends Command {

    public static final String COMMAND_WORD = "rule swap";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Swaps the position of 2 rules.\n"
            + "Parameters: "
            + "<rule 1 ID> <rule 2 ID>"
            + "Example: " + COMMAND_WORD + " 3 5";

    public static final String MESSAGE_SUCCESS = "Rules #%1$d and #%2$d swapped.";
    public static final String MESSAGE_NO_EFFECT = "Swapping rule #%1$d with itself will do nothing.";

    private final Index firstIndex;
    private final Index secondIndex;

    public RuleSwapCommand(Index firstIndex, Index secondIndex) {
        this.firstIndex = firstIndex;
        this.secondIndex = secondIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireAllNonNull(model, model.getRuleManager());
        RuleManager ruleManager = model.getRuleManager();

        try {
            if (firstIndex.equals(secondIndex)) {
                throw new CommandException(String.format(MESSAGE_NO_EFFECT, firstIndex.getOneBased()));
            }

            ruleManager.swapRules(firstIndex, secondIndex);

            return new CommandResult(
                    String.format(MESSAGE_SUCCESS, firstIndex.getOneBased(), secondIndex.getOneBased()),
                    CommandCategory.RULE);
        } catch (RuleNotFoundException e) {
            throw new CommandException(Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RuleSwapCommand)
                && firstIndex.equals(((RuleSwapCommand) other).firstIndex)
                && secondIndex.equals(((RuleSwapCommand) other).secondIndex);
    }
}
