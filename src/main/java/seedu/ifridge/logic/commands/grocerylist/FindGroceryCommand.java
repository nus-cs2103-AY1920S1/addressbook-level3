package seedu.ifridge.logic.commands.grocerylist;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.ifridge.commons.core.Messages;
import seedu.ifridge.logic.commands.Command;
import seedu.ifridge.logic.commands.CommandResult;
import seedu.ifridge.model.Model;
import seedu.ifridge.model.food.GroceryItem;
import seedu.ifridge.model.food.NameContainsKeywordsPredicate;
import seedu.ifridge.model.food.TagContainsKeywordsPredicate;

/**
 * Finds and lists all grocery items in grocery list whose name or tag contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindGroceryCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all items whose names or tags contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " chicken boba milk";

    private final NameContainsKeywordsPredicate namePredicate;
    private final TagContainsKeywordsPredicate tagPredicate;

    public FindGroceryCommand(NameContainsKeywordsPredicate namePredicate,
                              TagContainsKeywordsPredicate tagPredicate) {
        this.namePredicate = namePredicate;
        this.tagPredicate = tagPredicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        Predicate<GroceryItem> nameAndTagPredicate = namePredicate.or(tagPredicate);
        model.updateFilteredGroceryItemList(nameAndTagPredicate);
        return new CommandResult(String.format(Messages.MESSAGE_GROCERY_LIST_LISTED_OVERVIEW,
                model.getFilteredGroceryItemList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindGroceryCommand // instanceof handles nulls
                && namePredicate.equals(((FindGroceryCommand) other).namePredicate)
                && tagPredicate.equals(((FindGroceryCommand) other).tagPredicate)); // state check
    }
}
