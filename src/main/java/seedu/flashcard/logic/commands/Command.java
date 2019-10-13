package seedu.flashcard.logic.commands;

import seedu.flashcard.model.FlashcardList;

/**
 * abstract class for all the commands
 */
public abstract class Command {

    public abstract CommandResult execute(FlashcardList flashcardList);

}
