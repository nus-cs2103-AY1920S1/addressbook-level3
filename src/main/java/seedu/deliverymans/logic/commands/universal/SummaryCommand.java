package seedu.deliverymans.logic.commands.universal;

import seedu.address.model.Model;
import seedu.deliverymans.logic.commands.Command;
import seedu.deliverymans.logic.commands.CommandResult;
import seedu.deliverymans.logic.commands.exceptions.CommandException;

/**
 * (to be added)
 */
public class SummaryCommand extends Command {
    public static final String COMMAND_WORD = "summary";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult("");
    }
}
