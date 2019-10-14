package seedu.address.logic.commands.visit;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Mark the current on-going visit as finished and put it aside.
 */
public class FinishCurrentVisitCommand extends Command {

    public static final String COMMAND_WORD = "visit-now-finish";
    public static final String MESSAGE_SUCCESS = "Finished current Visit";


    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException("FUNCTION NOT IMPLEMENTED YET");
    }
}
