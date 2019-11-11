package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Opens a window displaying driver details.
 */
public class ViewDriverWindowCommand extends Command {

    public static final String COMMAND_WORD = "viewDW";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows window of driver details.\n"
            + "Parameters: "
            + "[DRIVER_ID]\n"
            + "Example: " + COMMAND_WORD + " "
            + "3";

    public static final String SHOWING_VIEW_WINDOW = "Opened view driver window.";

    private int id;

    public ViewDriverWindowCommand(int id) {
        this.id = id;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (!model.hasDriver(this.id)) {
            throw new CommandException(Messages.MESSAGE_INVALID_DRIVER_DISPLAYED_INDEX);
        }
        return new CommandResult(SHOWING_VIEW_WINDOW, true, false, id);
    }
}
