package seedu.planner.logic.commands.viewcommand;

import seedu.planner.logic.commands.Command;
import seedu.planner.logic.commands.util.HelpExplanation;

/**
 * Represents ViewActivityCommand, ViewContactCommand, ViewAccommodationCommand, ViewItineraryCommand.
 * @author 1nefootstep
 */
public abstract class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final HelpExplanation MESSAGE_USAGE = new HelpExplanation(
            COMMAND_WORD + " " + ViewItineraryCommand.SECOND_COMMAND_WORD + "/"
            + ViewHelpCommand.SECOND_COMMAND_WORD + "/"
            + ViewInfoCommand.SECOND_COMMAND_WORD,
            "View a certain tab or list.",
            COMMAND_WORD + " ("
                    + ViewItineraryCommand.SECOND_COMMAND_WORD + " || "
                    + ViewHelpCommand.SECOND_COMMAND_WORD + " || "
                    + ViewInfoCommand.SECOND_COMMAND_WORD
                    + ")",
            COMMAND_WORD + " " + ViewInfoCommand.SECOND_COMMAND_WORD
    );

    public static final String MESSAGE_SUCCESS = "Added!";
}
