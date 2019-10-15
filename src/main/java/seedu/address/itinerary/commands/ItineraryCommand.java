package seedu.address.itinerary.commands;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

/**
 * Shows itinerary.
 */
public class ItineraryCommand extends Command {
    public static final String COMMAND_WORD = "itinerary";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows you your itinerary.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_ITINERARY_MESSAGE = "itinerary";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOWING_ITINERARY_MESSAGE, false, false, true);
    }
}
