package seedu.address.logic.commands.defaults;

import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT;

import seedu.address.commons.core.IFridgeSettings;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Changes default sort method settings.
 */
public class SortDefaultCommand extends Command {
    public static final String COMMAND_WORD = "sortDefault";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Change default sort setting "
            + "Parameters: " + PREFIX_SORT + "TYPE \n" + "Example: " + COMMAND_WORD + " " + PREFIX_SORT + "expiry";

    public static final String MESSAGE_SUCCESS = "Default sort method: %1$s";

    private String sortDefault;

    public SortDefaultCommand(String sortDefault) {
        this.sortDefault = sortDefault;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        IFridgeSettings prev = model.getIFridgeSettings();
        model.setIFridgeSettings(new IFridgeSettings(prev.getNumberOfDays(), sortDefault, prev.getListDisplay()));
        return new CommandResult(
                String.format(MESSAGE_SUCCESS, sortDefault));
    }
}
