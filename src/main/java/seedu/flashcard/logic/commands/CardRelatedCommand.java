package seedu.flashcard.logic.commands;

import seedu.flashcard.logic.commands.exceptions.CommandException;
import seedu.flashcard.model.flashcard.Flashcard;

/**
 * abstract class for all commands related to individual flashcards.
 */
public abstract class CardRelatedCommand {

    public abstract CommandResult execute(Flashcard flashcard) throws CommandException;
}
