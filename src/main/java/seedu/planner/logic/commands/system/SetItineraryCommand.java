package seedu.planner.logic.commands.system;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import seedu.planner.commons.core.LogsCenter;
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
    private final Logger logger = LogsCenter.getLogger(SetItineraryCommand.class);

    public SetItineraryCommand(ReadOnlyItinerary itinerary) {
        this.itinerary = itinerary;
    }

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        logger.info(String.format("----------------[Itinerary reset!]", this));
        model.setItinerary(itinerary);
        return new CommandResult(
                String.format(MESSAGE_SUCCESS),
                new UiFocus[] {UiFocus.AGENDA}
        );
    }
}
