package seedu.ifridge.logic.commands.defaults;

import static seedu.ifridge.logic.parser.CliSyntax.PREFIX_REMINDER;

import seedu.ifridge.commons.core.IFridgeSettings;
import seedu.ifridge.logic.commands.Command;
import seedu.ifridge.logic.commands.CommandResult;
import seedu.ifridge.logic.commands.exceptions.CommandException;
import seedu.ifridge.model.Model;

/**
 * Changes default reminder settings.
 */
public class ReminderDefaultCommand extends Command {

    public static final String COMMAND_WORD = "remDefault";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Change default reminder setting to food expiring within r days " + "Parameters: "
            + PREFIX_REMINDER + "REMINDER \n" + "Example: " + COMMAND_WORD + " " + PREFIX_REMINDER + "3";

    public static final String MESSAGE_SUCCESS = "Default number of days for reminder: %1$s";

    private String remDefault;

    public ReminderDefaultCommand(String remDefault) {
        this.remDefault = remDefault;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        IFridgeSettings prev = model.getIFridgeSettings();
        IFridgeSettings curr = new IFridgeSettings(remDefault, prev.getSortMethod(), prev.getListDisplay());
        model.setIFridgeSettings(curr);
        return new CommandResult(
                String.format(MESSAGE_SUCCESS, remDefault));
    }
}
