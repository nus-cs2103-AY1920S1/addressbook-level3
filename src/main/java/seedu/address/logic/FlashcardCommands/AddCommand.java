package seedu.address.logic.FlashcardCommands;

import seedu.address.flashcard.Flashcard;
import seedu.address.flashcard.FlashcardList;

import static java.util.Objects.requireNonNull;

public class AddCommand extends Command{

    public static String COMMAND_WORD = "add";
    private final Flashcard toAdd;

    public AddCommand (Flashcard flashcard){
        requireNonNull(flashcard);
        toAdd = flashcard;
    }

    @Override
    public String execute(FlashcardList flashcardList) {
        requireNonNull(flashcardList);

        return "";

    }

}
