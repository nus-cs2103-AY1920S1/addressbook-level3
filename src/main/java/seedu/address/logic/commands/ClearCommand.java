package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult("");
    }
}
