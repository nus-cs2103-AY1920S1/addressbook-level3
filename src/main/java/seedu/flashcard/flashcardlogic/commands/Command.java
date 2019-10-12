package seedu.flashcard.flashcardlogic.commands;

import seedu.flashcard.flashcardmodel.FlashcardList;

/**
 * abstract class for all the commands
 */
public abstract class Command {

    public abstract CommandResult execute(FlashcardList flashcardList);

}
