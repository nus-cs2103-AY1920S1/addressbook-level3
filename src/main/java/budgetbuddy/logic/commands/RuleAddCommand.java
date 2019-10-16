package budgetbuddy.logic.commands;

import static budgetbuddy.logic.parser.CliSyntax.PREFIX_ACTION;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_PREDICATE;
import static java.util.Objects.requireNonNull;

import budgetbuddy.logic.commands.exceptions.CommandException;
import budgetbuddy.model.Model;
import budgetbuddy.model.rule.Rule;

/**
 * Adds a rule to budget buddy.
 */
public class RuleAddCommand extends Command {

    public static final String COMMAND_WORD = "rule add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a new rule to budget buddy. "
            + "Parameters: "
            + PREFIX_PREDICATE + "PREDICATE "
            + PREFIX_ACTION + "ACTION "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PREDICATE + "amount less than 10 "
            + PREFIX_ACTION + "add to budget daily";

    public static final String MESSAGE_SUCCESS = "New rule added: %1$s";

    private final Rule rule;

    /**
     * Creates a RuleAddCommand to add the specified {@code Rule}
     */
    public RuleAddCommand(Rule rule) {
        requireNonNull(rule);
        this.rule = rule;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        return null;
    }
}
