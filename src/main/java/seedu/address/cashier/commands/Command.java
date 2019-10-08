package seedu.address.cashier.commands;

import seedu.address.cashier.model.Model;


public abstract class Command {
    public abstract CommandResult execute(Model model, seedu.address.person.model.Model personModel) throws Exception;
}