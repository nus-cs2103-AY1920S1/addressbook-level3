package seedu.flashcard.logic.commands;

import seedu.flashcard.model.Model;

/**
 * abstract class for all the commands
 */
public abstract class Command {

    public abstract CommandResult execute(Model model);

}
