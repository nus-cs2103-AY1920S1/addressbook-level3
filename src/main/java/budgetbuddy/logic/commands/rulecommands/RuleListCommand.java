package budgetbuddy.logic.commands.rulecommands;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;

import budgetbuddy.logic.commands.Command;
import budgetbuddy.logic.commands.CommandCategory;
import budgetbuddy.logic.commands.CommandResult;
import budgetbuddy.model.Model;

/**
 * Lists all rules.
 */
public class RuleListCommand extends Command {

    public static final String COMMAND_WORD = "rule list";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all rules.\n"
            + "Example: "
            + "rule list";

    public static final String MESSAGE_SUCCESS = "Rules listed in order of addition.";
    public static final String MESSAGE_NO_RULES =
            "No rules found in your list. Make a new rule with `rule add`.";

    @Override
    public CommandResult execute(Model model) {
        requireAllNonNull(model, model.getRuleManager());
        return new CommandResult(
                model.getRuleManager().getRules().isEmpty()
                        ? MESSAGE_NO_RULES
                        : MESSAGE_SUCCESS,
                CommandCategory.RULE);
    }
}
