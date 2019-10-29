package seedu.ifridge.logic.commands.grocerylist;

import static java.util.Objects.requireNonNull;

import seedu.ifridge.commons.core.Messages;
import seedu.ifridge.logic.commands.Command;
import seedu.ifridge.logic.commands.CommandResult;
import seedu.ifridge.model.Model;
import seedu.ifridge.model.food.NameContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindGroceryCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all items whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " chicken boba milk";

    private final NameContainsKeywordsPredicate predicate;

    public FindGroceryCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredGroceryItemList(predicate);
        return new CommandResult(String.format(Messages.MESSAGE_GROCERY_LIST_LISTED_OVERVIEW,
                model.getFilteredGroceryItemList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindGroceryCommand // instanceof handles nulls
                && predicate.equals(((FindGroceryCommand) other).predicate)); // state check
    }
}
