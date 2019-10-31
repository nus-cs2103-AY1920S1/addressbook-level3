package seedu.address.logic.commands;

import seedu.address.logic.commands.util.HelpExplanation;

/**
 * Represents ViewActivityCommand, ViewContactCommand, ViewAccommodationCommand, ViewItineraryCommand.
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
