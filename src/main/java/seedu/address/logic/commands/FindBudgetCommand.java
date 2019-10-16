package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.BudgetDescriptionContainsKeywordsPredicate;

/**
 * Finds and lists all budgets in finance tracker with description containing any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindBudgetCommand extends Command {

    public static final String COMMAND_WORD = "findBudget";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all budgets whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " mala computer CAP_5.0";

    private final BudgetDescriptionContainsKeywordsPredicate predicate;

    public FindBudgetCommand(BudgetDescriptionContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredBudgets(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredBudgets().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindBudgetCommand // instanceof handles nulls
                && predicate.equals(((FindBudgetCommand) other).predicate)); // state check
    }
}
