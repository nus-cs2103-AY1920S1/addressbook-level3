package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Opens a windows with customer details.
 */
public class ViewCustomerWindowCommand extends Command {

    public static final String COMMAND_WORD = "viewCW";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows window of customer details.\n"
            + "Parameters: "
            + "[CUSTOMER_ID]\n"
            + "Example: " + COMMAND_WORD + " "
            + "3";

    public static final String SHOWING_VIEW_WINDOW = "Opened view customer window.";

    private int id;

    public ViewCustomerWindowCommand(int id) {
        this.id = id;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (!model.hasCustomer(this.id)) {
            throw new CommandException(Messages.MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX);
        }
        return new CommandResult(SHOWING_VIEW_WINDOW, false, true, id);
    }

}
