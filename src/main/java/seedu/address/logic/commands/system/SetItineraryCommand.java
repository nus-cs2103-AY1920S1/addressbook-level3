package seedu.address.logic.commands.system;

import seedu.address.logic.commands.UndoableCommand;
import seedu.address.logic.commands.result.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyItinerary;

/**
 * A command that is not usable by a User, only exist to assist in undoing the effects of AutoSchedule and
 * OptimiseBudget commands.
 */
public class SetItineraryCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "setitinerary";
    public static final String MESSAGE_SUCCESS = "Command successfully undone.";
    private final ReadOnlyItinerary itinerary;

    public SetItineraryCommand(ReadOnlyItinerary itinerary) {
        this.itinerary = itinerary;
    }

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }

    @Override
    public CommandResult execute(Model model) {
        model.setItinerary(itinerary);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
