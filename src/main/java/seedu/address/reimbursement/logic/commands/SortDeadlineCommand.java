package seedu.address.reimbursement.logic.commands;

import static seedu.address.reimbursement.ui.ReimbursementMessages.SORT_BY_DEADLINE;

import java.util.logging.Logger;

import seedu.address.person.commons.core.LogsCenter;
import seedu.address.reimbursement.model.Model;
import seedu.address.util.CommandResult;

/**
 * Represents a command to sort by deadline.
 */
public class SortDeadlineCommand extends SortCommand {
    private final Logger logger = LogsCenter.getLogger(getClass());

    @Override
    public CommandResult execute(Model model, seedu.address.person.model.Model personModel) {
        model.sortListByDeadline();
        logger.info("sort by deadline");
        return new CommandResult(SORT_BY_DEADLINE);
    }
}
