package seedu.address.logic.FlashcardCommands;

import seedu.address.flashcard.FlashcardList;

public abstract class Command {

    public abstract String execute(FlashcardList flashcardList);

}
