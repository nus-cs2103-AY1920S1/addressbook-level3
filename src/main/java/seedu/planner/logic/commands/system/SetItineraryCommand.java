package seedu.planner.logic.commands.system;

import seedu.planner.logic.commands.UndoableCommand;
import seedu.planner.logic.commands.result.CommandResult;
import seedu.planner.logic.commands.result.UiFocus;
import seedu.planner.model.Model;
import seedu.planner.model.ReadOnlyItinerary;
//@@author OneArmyj
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
        return new CommandResult(
                String.format(MESSAGE_SUCCESS),
                new UiFocus[] {UiFocus.AGENDA}
        );
    }
}
