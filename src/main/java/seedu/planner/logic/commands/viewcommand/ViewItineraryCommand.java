package seedu.planner.logic.commands.viewcommand;

import static java.util.Objects.requireNonNull;

import seedu.planner.logic.autocomplete.CommandInformation;
import seedu.planner.logic.commands.exceptions.CommandException;
import seedu.planner.logic.commands.result.CommandResult;
import seedu.planner.logic.commands.result.UiFocus;
import seedu.planner.logic.commands.util.HelpExplanation;
import seedu.planner.model.Model;

/**
 * Views the itinerary tab.
 * @author 1nefootstep
 */
public class ViewItineraryCommand extends ViewCommand {

    public static final String SECOND_COMMAND_WORD = "itinerary";

    public static final HelpExplanation MESSAGE_USAGE = new HelpExplanation(
            COMMAND_WORD + " " + SECOND_COMMAND_WORD,
            "Opens the itinerary tab.",
            COMMAND_WORD + " " + SECOND_COMMAND_WORD,
            COMMAND_WORD + " " + SECOND_COMMAND_WORD
    );

    public static final CommandInformation COMMAND_INFORMATION = new CommandInformation(
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
