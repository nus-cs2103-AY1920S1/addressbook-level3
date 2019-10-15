package seedu.address.reimbursement.commands;

import seedu.address.reimbursement.ui.ReimbursementMessages;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {
    public static final String COMMAND_WORD = "exit";

    @Override
    public CommandResult execute(seedu.address.reimbursement.model.Model model,
                                 seedu.address.person.model.Model personModel) {
        return new CommandResult(ReimbursementMessages.MESSAGE_EXIT_ACKNOWLEDGEMENT, true);
    }
}
