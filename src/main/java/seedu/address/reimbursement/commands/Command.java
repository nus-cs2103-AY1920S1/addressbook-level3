package seedu.address.reimbursement.commands;

import seedu.address.reimbursement.model.Model;

public abstract class Command {
    public abstract CommandResult execute(Model model, seedu.address.person.model.Model personModel) throws Exception;
}
