package seedu.address.reimbursement.commands;

import java.util.logging.Logger;

import seedu.address.person.commons.core.LogsCenter;
import seedu.address.reimbursement.model.Model;
import seedu.address.reimbursement.ui.ReimbursementMessages;

/**
 * Represents a command to sort by deadline.
 */
public class SortDeadlineCommand extends Command {
    public static final String COMMAND_WORD = "sortdeadline";
    private final Logger logger = LogsCenter.getLogger(getClass());

    @Override
    public CommandResult execute(Model model, seedu.address.person.model.Model personModel) {
        ReimbursementMessages reimbursementMessages = new ReimbursementMessages();
        model.sortListByDeadline();
        logger.info("sort by deadline");
        return new CommandResult(reimbursementMessages.SORT_BY_DEADLINE);
    }
}
