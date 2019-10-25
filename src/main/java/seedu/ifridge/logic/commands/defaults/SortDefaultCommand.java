package seedu.ifridge.logic.commands.defaults;

import static seedu.ifridge.logic.parser.CliSyntax.PREFIX_SORT;

import seedu.ifridge.commons.core.IFridgeSettings;
import seedu.ifridge.logic.commands.Command;
import seedu.ifridge.logic.commands.CommandResult;
import seedu.ifridge.logic.commands.exceptions.CommandException;
import seedu.ifridge.model.Model;

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
        IFridgeSettings curr = new IFridgeSettings(prev.getNumberOfDays(), sortDefault, prev.getListDisplay());
        model.setIFridgeSettings(curr);
        return new CommandResult(
                String.format(MESSAGE_SUCCESS, sortDefault));
    }
}
