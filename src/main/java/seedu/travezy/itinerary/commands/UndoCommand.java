package seedu.travezy.itinerary.commands;

import seedu.travezy.itinerary.model.Model;
import seedu.travezy.logic.commands.CommandResult;
import seedu.travezy.logic.commands.exceptions.CommandException;

/**
 * Revert to the previous change in the event list.
 */
public class UndoCommand extends Command {

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return null;
    }
}
