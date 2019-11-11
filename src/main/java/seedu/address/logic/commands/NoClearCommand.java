package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Represents the command entered by the user when he does not want to clear the finsec
 */
public class NoClearCommand extends Command {

    public static final String COMMAND_WORD = "N";
    public static final String SHOWING_NO_CLEAR = "You have decided not to clear the FinSec"
            + " anymore. You may now continue with your tasks :-)";

    /**
     * Executes a noclear command
     * @param model {@code Model} which the command should operate on.
     * @return
     */
    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOWING_NO_CLEAR,
                false, false, false, false, false, false);
    }
}
