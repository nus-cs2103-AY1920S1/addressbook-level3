//@@author shutingy-reused

package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.category.CategoryContainsAnyKeywordsPredicate;


/**
 * Finds and lists all persons in address book whose question contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCategoryCommand extends Command {

    public static final String COMMAND_WORD = "findcat";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all flashcards which contain any of "
            + "the specified category keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " math science";

    private final CategoryContainsAnyKeywordsPredicate predicate;

    public FindCategoryCommand(CategoryContainsAnyKeywordsPredicate predicate) {
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
                || (other instanceof FindCategoryCommand // instanceof handles nulls
                && predicate.equals(((FindCategoryCommand) other).predicate)); // state check
    }
}
