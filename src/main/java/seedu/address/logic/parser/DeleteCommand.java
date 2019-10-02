package seedu.address.logic.parser;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Abstract parent class for DeleteBySerialNumberCommand and DeleteByIndexCommand.
 */
public abstract class DeleteCommand extends Command {

    public abstract CommandResult execute(Model model) throws CommandException;

}
