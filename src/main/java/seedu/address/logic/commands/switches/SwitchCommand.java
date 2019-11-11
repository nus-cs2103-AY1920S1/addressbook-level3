// @@author sreesubbash
package seedu.address.logic.commands.switches;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.ModeSwitchException;
import seedu.address.logic.util.ModeEnum;
import seedu.address.model.Model;

/**
 * Represents an app command
 */
public abstract class SwitchCommand extends Command {
    public abstract CommandResult execute(Model model) throws CommandException;

    public abstract ModeEnum getNewMode(ModeEnum old) throws ModeSwitchException;
}
