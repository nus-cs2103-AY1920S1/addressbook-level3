package seedu.address.logic.commands.expense;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Command that enters the list view of expense manager page.
 */
public class EnterListViewCommand extends Command {

    public static final String COMMAND_WORD = "showlist";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Enters the list view of expense manager.";

    public static final String MESSAGE_SUCCESS = "Entered the list view of expense manager.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult(MESSAGE_SUCCESS, true, COMMAND_WORD);
    }

}
