package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Lists the available policies in the address book.
 */
public class ListPolicyCommand extends Command {

    public static final String COMMAND_WORD = "listpolicy";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows a list of all current policies.";

    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "listpolicy command not implemented yet";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException(MESSAGE_NOT_IMPLEMENTED_YET);
    }
}
