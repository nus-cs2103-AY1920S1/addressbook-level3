package seedu.address.transaction.commands;

import seedu.address.transaction.model.Model;

public abstract class Command {
    public abstract CommandResult execute(Model model) throws Exception;
}
