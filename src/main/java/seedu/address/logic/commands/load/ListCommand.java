package seedu.address.logic.commands.load;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.LoadCommand;
import seedu.address.model.Model;

/**
 * Terminates the program.
 */
public class ListCommand extends LoadCommand {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_LIST_ACKNOWLEDGEMENT = "Displaying available wordbanks\n Choose 1";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_LIST_ACKNOWLEDGEMENT, false, false);
    }

}
