package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.card.WordContainsKeywordsPredicate;

/**
 * Finds and lists all cards in word bank whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends AppCommand {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all cards whose words contain any of "
            + "the specified keywords (case-insensitive).\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " Singapore";

    private final WordContainsKeywordsPredicate predicate;

    public FindCommand(WordContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredCardList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_CARDS_LISTED_OVERVIEW, model.getFilteredCardList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
