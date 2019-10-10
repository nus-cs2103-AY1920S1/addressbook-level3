package seedu.address.logic.flashcardcommands;

import seedu.address.flashcard.FlashcardList;

/**
 * abstract class for all the commands
 */
public abstract class Command {

    public abstract String execute(FlashcardList flashcardList);

}
