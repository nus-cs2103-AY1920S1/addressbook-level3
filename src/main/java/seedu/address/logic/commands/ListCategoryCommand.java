//@@author shutingy-reused

package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.category.CategoryContainsAnyKeywordsPredicate;


/**
 * Finds and lists all flashcards in address book whose question contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class ListCategoryCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List all FlashCard(s) which contain any of "
            + "the specified category keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " math science";

    private final CategoryContainsAnyKeywordsPredicate predicate;

    public ListCategoryCommand(CategoryContainsAnyKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredFlashCardList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_FLASHCARD_LISTED_OVERVIEW,
                        model.getFilteredFlashCardList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListCategoryCommand // instanceof handles nulls
                && predicate.equals(((ListCategoryCommand) other).predicate)); // state check
    }
}
