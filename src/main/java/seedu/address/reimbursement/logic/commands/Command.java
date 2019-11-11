package seedu.address.reimbursement.logic.commands;

import seedu.address.reimbursement.model.Model;
import seedu.address.reimbursement.model.exception.InvalidDeadlineException;
import seedu.address.reimbursement.model.exception.NoSuchPersonReimbursementException;

/**
 * Interface representing the methods for each command.
 */
public abstract class Command {
    public abstract CommandResult execute(Model model, seedu.address.person.model.Model personModel)
            throws NoSuchPersonReimbursementException, InvalidDeadlineException;
}
