package seedu.algobase.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.algobase.commons.core.Messages;
import seedu.algobase.model.Model;
import seedu.algobase.model.plan.NameContainsKeywordsPredicate;


/**
 * Finds and lists all plans in algobase whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindPlanCommand extends Command {

    public static final String COMMAND_WORD = "findplan";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all plans whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final NameContainsKeywordsPredicate predicate;

    public FindPlanCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPlanList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PLANS_LISTED_OVERVIEW, model.getFilteredPlanList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindPlanCommand // instanceof handles nulls
                && predicate.equals(((FindPlanCommand) other).predicate)); // state check
    }
}
