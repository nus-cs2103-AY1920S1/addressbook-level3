package seedu.address.reimbursement.logic.commands;

import static seedu.address.reimbursement.ui.ReimbursementMessages.MESSAGE_EXIT_ACKNOWLEDGEMENT;

import seedu.address.util.CommandResult;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {
    public static final String COMMAND_WORD = "exit";

    @Override
    public CommandResult execute(seedu.address.reimbursement.model.Model model,
                                 seedu.address.person.model.Model personModel) {
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, true);
    }
}
