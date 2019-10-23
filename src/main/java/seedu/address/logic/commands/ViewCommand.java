package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Views the different kind of modes in PalPay.
 */
public class ViewCommand extends Command {
    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = ViewCommand.COMMAND_WORD + ": View the bank account.\n"
        + "Parameter: SIMI\n"
        + "Example: " + ViewCommand.COMMAND_WORD + " transactions";

    public static final String MESSAGE_SUCCESS = "Tab switched!";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_SUCCESS, false, false, true);
    }
}
