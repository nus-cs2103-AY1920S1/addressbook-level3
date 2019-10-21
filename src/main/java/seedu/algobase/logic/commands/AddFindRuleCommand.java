package seedu.algobase.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.algobase.logic.commands.exceptions.CommandException;
import seedu.algobase.model.Model;
import seedu.algobase.model.searchrule.problemsearchrule.ProblemSearchRule;

/**
 * Adds a find rule to AlgoBase.
 */
public class AddFindRuleCommand extends Command {

    public static final String COMMAND_WORD = "addfindrule";

    // TODO: Add usage string for this command.
    public static final String MESSAGE_USAGE = "placeholder";
    public static final String MESSAGE_SUCCESS = "New find rule added: %1$s";
    public static final String MESSAGE_DUPLICATE_FIND_RULE = "This find rule already exists in AlgoBase.";
    public static final String MESSAGE_NO_CONSTRAINTS =
        "You should provide at least one constraint for a new find rule";

    private final ProblemSearchRule toAdd;

    public AddFindRuleCommand(ProblemSearchRule rule) {
        requireNonNull(rule);
        this.toAdd = rule;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasFindRule(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_FIND_RULE);
        }

        model.addFindRule(toAdd);

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof AddFindRuleCommand // instanceof handles nulls
            && toAdd.equals(((AddFindRuleCommand) other).toAdd));
    }
}
