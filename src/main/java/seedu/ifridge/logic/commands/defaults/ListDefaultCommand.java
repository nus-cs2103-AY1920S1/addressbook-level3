package seedu.ifridge.logic.commands.defaults;

import static seedu.ifridge.logic.parser.CliSyntax.PREFIX_LIST;

import seedu.ifridge.commons.core.IFridgeSettings;
import seedu.ifridge.logic.commands.Command;
import seedu.ifridge.logic.commands.CommandResult;
import seedu.ifridge.logic.commands.exceptions.CommandException;
import seedu.ifridge.model.Model;

/**
 * Changes default list display settings.
 */
public class ListDefaultCommand extends Command {
    public static final String COMMAND_WORD = "listDefault";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Change default list display setting to merge or unmerge " + "Parameters: "
            + PREFIX_LIST + "TYPE \n" + "Example: " + COMMAND_WORD + " " + PREFIX_LIST + "merge";

    public static final String MESSAGE_SUCCESS = "Default list display: %1$s";

    private String listDefault;

    public ListDefaultCommand(String listDefault) {
        this.listDefault = listDefault;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        IFridgeSettings prev = model.getIFridgeSettings();
        IFridgeSettings curr = new IFridgeSettings(prev.getNumberOfDays(), prev.getSortMethod(), listDefault);
        model.setIFridgeSettings(curr);
        return new CommandResult(
                String.format(MESSAGE_SUCCESS, listDefault));
    }
}
