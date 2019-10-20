package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.ModeSwitchException;
import seedu.address.model.Model;
import seedu.address.logic.util.ModeEnum;

/**
 * Represents a settings command
 */
public abstract class SettingsCommand extends Command {

    @Override
    public boolean check(Model model) throws CommandException {
        return true;
    }

    public ModeEnum getNewMode(ModeEnum old) throws ModeSwitchException {
        return ModeEnum.SETTINGS;
    }
}
