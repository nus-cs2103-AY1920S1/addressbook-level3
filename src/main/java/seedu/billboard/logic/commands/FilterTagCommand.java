package seedu.billboard.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.billboard.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.billboard.commons.core.Messages;
import seedu.billboard.model.Model;
import seedu.billboard.model.tag.ContainsTagPredicate;

/**
 * Filters and lists all expenses which has any tags given in argument.
 */
public class FilterTagCommand extends TagCommand {
    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = TagCommand.COMMAND_WORD + " " + COMMAND_WORD
            + ": Filters all expenses with the specified tags and displays them as a list with index numbers.\n"
            + "Parameters: " + PREFIX_TAG + "[TAG]\n"
            + "Example: " + TagCommand.COMMAND_WORD + " " + COMMAND_WORD + " " + PREFIX_TAG + "drinks";

    private final ContainsTagPredicate predicate;

    public FilterTagCommand(ContainsTagPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredExpenses(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_EXPENSES_LISTED_OVERVIEW, model.getFilteredExpenses().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilterTagCommand // instanceof handles nulls
                && predicate.equals(((FilterTagCommand) other).predicate));
    }
}
