package seedu.billboard.logic.commands;

import seedu.billboard.logic.commands.exceptions.CommandException;
import seedu.billboard.model.Model;

/**
 *  Represents a Tag command with hidden internal logic and the ability to be executed.
 */
public abstract class TagCommand extends Command {
    public static final String COMMAND_WORD = "tag";

    public abstract CommandResult execute(Model model) throws CommandException;
}
