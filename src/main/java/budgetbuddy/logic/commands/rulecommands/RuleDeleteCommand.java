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
 * Delete a rule from budget buddy.
 */
public class RuleDeleteCommand extends Command {

    public static final String COMMAND_WORD = "rule delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a rule.\n"
            + "Parameters: "
            + "<rule index> "
            + "Example: " + COMMAND_WORD + " 3";

    public static final String MESSAGE_SUCCESS = "Deleted Rule #%1$s";

    private final Index targetIndex;

    public RuleDeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireAllNonNull(model, model.getRuleManager());
        RuleManager ruleManager = model.getRuleManager();

        try {
            ruleManager.deleteRule(targetIndex);
            return new CommandResult(String.format(MESSAGE_SUCCESS, targetIndex.getOneBased()), CommandCategory.RULE);
        } catch (RuleNotFoundException e) {
            throw new CommandException(Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RuleDeleteCommand);
    }
}
