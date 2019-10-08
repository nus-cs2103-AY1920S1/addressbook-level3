package seedu.address.inventory.commands;

import seedu.address.transaction.model.Model;

public abstract class Command {
    public abstract CommandResult execute(Model model, seedu.address.person.model.Model personModel) throws Exception;
}
