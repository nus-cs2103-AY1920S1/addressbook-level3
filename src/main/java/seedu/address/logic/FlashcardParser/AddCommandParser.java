package seedu.address.logic.FlashcardParser;

import seedu.address.flashcard.Flashcard;
import seedu.address.logic.FlashcardCommands.AddCommand;

public class AddCommandParser implements Parser<AddCommand> {

    public AddCommand parse (String args) {

        Flashcard flashcard;
        return new AddCommand(flashcard);
    }
}
