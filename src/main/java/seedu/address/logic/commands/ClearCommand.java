package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "The Task / Driver / Customer List has been cleared!";
    public static final String CONFIRMATION_WORD = "CONFIRM";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes all data from Customer, "
            + "Driver and Task list."
            + "Once data are deleted, they CANNOT be retrieved back. \n"
            + "Parameters: [CONFIRM] *CASE-SENSITIVE \n"
            + "Example: " + COMMAND_WORD + " " + CONFIRMATION_WORD;

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.resetCentralManager();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
