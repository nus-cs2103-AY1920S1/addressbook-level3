package seedu.ichifund.logic.commands.budget;

import static java.util.Objects.requireNonNull;

import seedu.ichifund.commons.core.Messages;
import seedu.ichifund.logic.commands.Command;
import seedu.ichifund.logic.commands.CommandResult;
import seedu.ichifund.model.Model;
import seedu.ichifund.model.budget.BudgetDescriptionPredicate;

/**
 * Finds and lists all budgets in fund book whose description contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindBudgetCommand extends Command {

    public static final String COMMAND_WORD = "findbud";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all budgets whose descriptions contain"
            + "any of the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " food anime";

    private final BudgetDescriptionPredicate predicate;

    public FindBudgetCommand(BudgetDescriptionPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredBudgetList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_BUDGETS_LISTED_OVERVIEW, model.getFilteredBudgetList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindBudgetCommand // instanceof handles nulls
                && predicate.equals(((FindBudgetCommand) other).predicate)); // state check
    }
}
