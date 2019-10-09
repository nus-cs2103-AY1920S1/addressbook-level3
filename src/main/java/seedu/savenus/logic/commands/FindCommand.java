package seedu.savenus.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.savenus.commons.core.Messages;
import seedu.savenus.model.Model;
import seedu.savenus.model.food.NameContainsKeywordsPredicate;

/**
 * Finds and lists all foods in menu whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all foods whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " chicken rice, nasi lemak, prawn mee.";

    private final NameContainsKeywordsPredicate predicate;

    public FindCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredFoodList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_FOOD_LISTED_OVERVIEW, model.getFilteredFoodList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
