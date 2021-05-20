package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Clears FinSec of all data. Warning! It is not reversible!"
            + " Would you still like to delete? \n"
            + "If yes, enter \"Y\" \n"
            + "Alternatively, enter \"N\"` to continue. ";


    /**
     * Executes a clearcommand
     * @param model {@code Model} which the command should operate on.
     * @return
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        return new CommandResult(MESSAGE_USAGE, false, false, false, false, false, true);
    }
}
