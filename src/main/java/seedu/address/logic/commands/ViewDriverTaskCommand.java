package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * List completed delivery task allocated to the particular driver.
 */
public class ViewDriverTaskCommand extends Command {

    public static final String COMMAND_WORD = "viewD";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Views the delivered tasks of the specified driver "
            + "and displays them as a list with index numbers."
            + "\n"
            + "Parameters: " + "DRIVER_ID (must be a positive integer)"
            + "\n"
            + "Example: " + COMMAND_WORD + " "
            + "1";

    public static final String MESSAGE_SUCCESS = "Listed %s completed tasks assigned to the Driver ID #%s";

    private int driverId;

    public ViewDriverTaskCommand(int driverId) {
        this.driverId = driverId;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasDriver(driverId)) {
            throw new CommandException(Messages.MESSAGE_INVALID_DRIVER_DISPLAYED_INDEX);
        }

        model.viewDriverTask(driverId);
        int listSize = model.getCurrentCompletedTaskList().size();
        return new CommandResult(String.format(MESSAGE_SUCCESS, listSize, driverId));
    }

}
