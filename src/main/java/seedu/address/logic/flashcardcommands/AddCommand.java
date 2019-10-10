package seedu.address.logic.flashcardcommands;

import static java.util.Objects.requireNonNull;

import seedu.address.flashcard.Flashcard;
import seedu.address.flashcard.FlashcardList;

/**
 * Command to add a new flashcard.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";
    private final Flashcard toAdd;

    public AddCommand (Flashcard flashcard) {
        requireNonNull(flashcard);
        toAdd = flashcard;
    }

    @Override
    public String execute(FlashcardList flashcardList) {
        requireNonNull(flashcardList);

        return "";

    }

}
