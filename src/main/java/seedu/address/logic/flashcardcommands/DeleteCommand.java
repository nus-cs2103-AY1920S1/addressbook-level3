package seedu.address.logic.flashcardcommands;

import static java.util.Objects.requireNonNull;

import seedu.address.flashcard.Flashcard;
import seedu.address.flashcard.FlashcardList;

/**
 * Command to delete a flashcard
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";
    private final Flashcard toDelete;

    public DeleteCommand (Flashcard flashcard) {
        requireNonNull(flashcard);
        toDelete = flashcard;
    }

    @Override
    public String execute(FlashcardList flashcardList) {
        requireNonNull(flashcardList);

        return "";

    }

}

