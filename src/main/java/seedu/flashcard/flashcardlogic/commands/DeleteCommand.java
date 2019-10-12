package seedu.flashcard.flashcardlogic.commands;

import static java.util.Objects.requireNonNull;

import seedu.flashcard.flashcardmodel.flashcard.Flashcard;
import seedu.flashcard.flashcardmodel.FlashcardList;

/**
 * Command to delete a flashcardmodel
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";
    private final Flashcard toDelete;

    public DeleteCommand (Flashcard flashcard) {
        requireNonNull(flashcard);
        toDelete = flashcard;
    }

    @Override
    public CommandResult execute(FlashcardList flashcardList) {
        return null;
    }

}

