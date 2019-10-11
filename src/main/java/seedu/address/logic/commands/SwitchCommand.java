package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Represents a app command //todo give a more descriptive comment
 */
public abstract class SwitchCommand extends Command {
    public abstract CommandResult execute(Model model) throws CommandException;

    public abstract ModeEnum getNewMode();
}
