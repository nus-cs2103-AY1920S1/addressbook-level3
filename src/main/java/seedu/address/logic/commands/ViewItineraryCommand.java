package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.result.CommandResult;
import seedu.address.logic.commands.result.UiFocus;
import seedu.address.logic.commands.util.HelpExplanation;
import seedu.address.model.Model;

/**
 * Views the itinerary tab.
 */
public class ViewItineraryCommand extends ViewCommand {

    public static final String SECOND_COMMAND_WORD = "itinerary";

    public static final HelpExplanation MESSAGE_USAGE = new HelpExplanation(
            COMMAND_WORD + " " + SECOND_COMMAND_WORD,
            "Opens the itinerary tab.",
            COMMAND_WORD + " " + SECOND_COMMAND_WORD,
            COMMAND_WORD + " " + SECOND_COMMAND_WORD
    );

    public static final String MESSAGE_SUCCESS = "Opened the itinerary!";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        return new CommandResult(MESSAGE_SUCCESS, new UiFocus[] {UiFocus.AGENDA});
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ViewItineraryCommand);
    }
}
