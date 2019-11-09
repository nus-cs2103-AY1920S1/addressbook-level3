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
            + ": Change default reminder setting to food expiring within n days "
            + "(n must be an integer more than or equals to 0). "
            + "Parameters: " + PREFIX_REMINDER + "NUMBER_OF_DAYS \n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_REMINDER + "3";

    public static final String MESSAGE_SUCCESS = "Default number of days for reminder: %1$s";

    private String remDefault;

    public ReminderDefaultCommand(String remDefault) {
        this.remDefault = remDefault;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        IFridgeSettings curr = new IFridgeSettings(remDefault);
        model.setIFridgeSettings(curr);
        CommandResult commandResult = new CommandResult(String.format(MESSAGE_SUCCESS, remDefault));
        commandResult.setWasteListCommand();
        commandResult.setGroceryListCommand();
        return commandResult;
    }
    @Override

    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReminderDefaultCommand // instanceof handles nulls
                && remDefault.equals(((ReminderDefaultCommand) other).remDefault)); // state check
    }
}
