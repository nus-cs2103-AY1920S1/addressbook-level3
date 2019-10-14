package seedu.flashcard.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.flashcard.commons.core.Messages;
import seedu.flashcard.model.Model;
import seedu.flashcard.model.flashcard.WordContainsKeywordsPredicate;

/**
 * Command to find a flashcard based on some keywords in its questions or answer.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Find all flashcards whose words contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " compact complete search";

    private final WordContainsKeywordsPredicate predicate;

    public FindCommand(WordContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredFlashcardList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_FLASHCARD_LISTED_OVERVIEW, model.getFilteredFlashcardList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof FindCommand
                && predicate.equals(((FindCommand) other).predicate));
    }
}
