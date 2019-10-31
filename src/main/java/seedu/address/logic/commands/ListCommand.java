package seedu.address.logic.commands;

import seedu.address.logic.commands.util.HelpExplanation;

/**
 * Represents ListActivityCommand, ListContactCommand and ListAccommodationCommand.
 */
public abstract class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final HelpExplanation MESSAGE_USAGE = new HelpExplanation(
            COMMAND_WORD + " " + ListAccommodationCommand.SECOND_COMMAND_WORD + "/"
                    + ListActivityCommand.SECOND_COMMAND_WORD + "/"
                    + ListContactCommand.SECOND_COMMAND_WORD,
            "Lists activity/day/person.",
            COMMAND_WORD + " ("
                    + ListAccommodationCommand.SECOND_COMMAND_WORD + " || "
                    + ListActivityCommand.SECOND_COMMAND_WORD + " || "
                    + ListContactCommand.SECOND_COMMAND_WORD
                    + ")",
            COMMAND_WORD + " " + ListActivityCommand.SECOND_COMMAND_WORD
    );
}
