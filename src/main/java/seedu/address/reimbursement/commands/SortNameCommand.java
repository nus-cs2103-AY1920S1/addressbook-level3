package seedu.address.reimbursement.commands;

import java.util.logging.Logger;

import seedu.address.person.commons.core.LogsCenter;
import seedu.address.reimbursement.model.Model;
import seedu.address.reimbursement.ui.ReimbursementMessages;

/**
 * Represents the command to sort by name
 */
public class SortNameCommand extends Command {
    public static final String COMMAND_WORD = "sortname";
    private final Logger logger = LogsCenter.getLogger(getClass());

    @Override
    public CommandResult execute(Model model, seedu.address.person.model.Model personModel) {
        ReimbursementMessages reimbursementMessages = new ReimbursementMessages();
        model.sortListByName();
        logger.info("sort by name");
        return new CommandResult(reimbursementMessages.SORT_BY_NAME);
    }
}
