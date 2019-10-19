package seedu.billboard.logic.commands;

import seedu.billboard.logic.commands.exceptions.CommandException;
import seedu.billboard.model.Model;

/**
 *  Represents an Archive command with hidden internal logic and the ability to be executed.
 */
public abstract class ArchiveCommand extends Command {
    public static final String COMMAND_WORD = "archive";

    public abstract CommandResult execute(Model model) throws CommandException;
}
