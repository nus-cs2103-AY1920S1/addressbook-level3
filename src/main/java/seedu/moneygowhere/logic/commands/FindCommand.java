package seedu.moneygowhere.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.moneygowhere.commons.core.Messages;
import seedu.moneygowhere.model.Model;
import seedu.moneygowhere.model.spending.NameContainsKeywordsPredicate;

/**
 * Finds and lists all spending in MoneyGoWhere whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all spending entries whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final NameContainsKeywordsPredicate predicate;

    public FindCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredSpendingList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_SPENDINGS_LISTED_OVERVIEW, model.getFilteredSpendingList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
