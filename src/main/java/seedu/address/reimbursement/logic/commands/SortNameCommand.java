package seedu.address.reimbursement.logic.commands;

import static seedu.address.reimbursement.ui.ReimbursementMessages.SORT_BY_NAME;

import java.util.logging.Logger;

import seedu.address.person.commons.core.LogsCenter;
import seedu.address.reimbursement.model.Model;
import seedu.address.util.CommandResult;

/**
 * Represents the command to sort by name
 */
public class SortNameCommand extends SortCommand {
    private final Logger logger = LogsCenter.getLogger(getClass());

    @Override
    public CommandResult execute(Model model, seedu.address.person.model.Model personModel) {
        model.sortListByName();
        logger.info("sort by name");
        return new CommandResult(SORT_BY_NAME);
    }
}
