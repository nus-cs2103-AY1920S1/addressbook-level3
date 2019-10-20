package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.ModeSwitchException;
import seedu.address.model.Model;
import seedu.address.logic.util.ModeEnum;

/**
 * Represents an app command
 */
public abstract class SwitchCommand extends Command {
    public abstract CommandResult execute(Model model) throws CommandException;

    public boolean check(Model model) throws CommandException {
        return true;
    }

    public abstract ModeEnum getNewMode(ModeEnum old) throws ModeSwitchException;
}
