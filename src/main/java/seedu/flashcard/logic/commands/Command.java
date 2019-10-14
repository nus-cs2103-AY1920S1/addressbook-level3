package seedu.flashcard.logic.commands;

import seedu.flashcard.logic.commands.exceptions.CommandException;
import seedu.flashcard.model.FlashcardList;

/**
 * abstract class for all the commands related to flashcard list.
 */
public abstract class Command {

    public abstract CommandResult execute(FlashcardList flashcardList) throws CommandException;

}
