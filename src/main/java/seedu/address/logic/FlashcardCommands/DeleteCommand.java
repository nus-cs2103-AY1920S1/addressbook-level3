package seedu.address.logic.FlashcardCommands;

import seedu.address.flashcard.Flashcard;
import seedu.address.flashcard.FlashcardList;

import static java.util.Objects.requireNonNull;

public class DeleteCommand extends Command{

    public static String COMMAND_WORD = "add";
    private final Flashcard toDelete;

    public DeleteCommand (Flashcard flashcard){
        requireNonNull(flashcard);
        toDelete = flashcard;
    }

    @Override
    public String execute(FlashcardList flashcardList) {
        requireNonNull(flashcardList);

        return "";

    }

}

