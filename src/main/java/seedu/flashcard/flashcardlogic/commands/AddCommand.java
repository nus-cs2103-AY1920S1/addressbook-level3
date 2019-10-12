package seedu.flashcard.flashcardlogic.commands;

import static java.util.Objects.requireNonNull;

import seedu.flashcard.flashcardmodel.flashcard.Flashcard;
import seedu.flashcard.flashcardmodel.FlashcardList;

/**
 * Command to add a new flashcardmodel.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";
    private final Flashcard toAdd;

    public AddCommand (Flashcard flashcard) {
        requireNonNull(flashcard);
        toAdd = flashcard;
    }

    @Override
    public CommandResult execute(FlashcardList flashcardList) {
        return null;
    }

}
