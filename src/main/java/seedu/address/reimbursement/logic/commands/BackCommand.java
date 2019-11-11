package seedu.address.reimbursement.logic.commands;

import static seedu.address.reimbursement.ui.ReimbursementMessages.BACK_COMMAND;

import java.util.logging.Logger;

import seedu.address.person.commons.core.LogsCenter;
import seedu.address.reimbursement.model.Model;
import seedu.address.util.CommandResult;


/**
 * Represents a command to list all reimbursements.
 */
public class BackCommand extends Command {
    public static final String COMMAND_WORD = "back";
    private final Logger logger = LogsCenter.getLogger(getClass());

    @Override
    public CommandResult execute(Model model, seedu.address.person.model.Model personModel) {
        model.listReimbursement();
        return new CommandResult(BACK_COMMAND);
    }
}
