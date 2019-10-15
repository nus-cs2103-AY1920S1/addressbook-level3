package seedu.address.logic.commands.visit;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Show the current on-going visit.
 */
public class ShowCurrentVisitCommand extends Command {

    public static final String COMMAND_WORD = "visit-now";
    public static final String MESSAGE_SUCCESS = "Showed current visit";


    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException("FUNCTION NOT IMPLEMENTED YET");
    }
}
