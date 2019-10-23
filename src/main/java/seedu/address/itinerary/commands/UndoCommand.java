package seedu.address.itinerary.commands;

import seedu.address.itinerary.model.Model;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Revert to the previous change in the event list.
 */
public class UndoCommand extends Command {

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return null;
    }
}
