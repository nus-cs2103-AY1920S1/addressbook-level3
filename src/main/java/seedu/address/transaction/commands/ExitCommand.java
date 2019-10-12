package seedu.address.transaction.commands;

import seedu.address.transaction.ui.TransactionMessages;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";

    @Override
    public CommandResult execute(seedu.address.transaction.model.Model model,
                                 seedu.address.person.model.Model personModel) {
        return new CommandResult(TransactionMessages.MESSAGE_EXIT_ACKNOWLEDGEMENT, true);
    }

}
