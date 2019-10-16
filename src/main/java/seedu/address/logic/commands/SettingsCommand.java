package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Represents a settings command
 */
public abstract class SettingsCommand extends Command {

    @Override
    public ModeEnum check(Model model, ModeEnum mode) throws CommandException {
        if (mode != ModeEnum.SETTINGS) {
            throw new CommandException("You're not in Settings mode!");
        }
        return ModeEnum.SETTINGS;
    }
}
