package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.WishDescriptionContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindWishCommand extends Command {

    public static final String COMMAND_WORD = "findWish";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all wishes whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " mala computer CAP_5.0";

    private final WishDescriptionContainsKeywordsPredicate predicate;

    public FindWishCommand(WishDescriptionContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredWishes(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredWishes().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindWishCommand // instanceof handles nulls
                && predicate.equals(((FindWishCommand) other).predicate)); // state check
    }
}