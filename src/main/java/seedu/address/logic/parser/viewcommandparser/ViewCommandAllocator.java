package seedu.address.logic.parser.viewcommandparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.viewcommand.ViewCommand;
import seedu.address.logic.parser.AlfredParserUtil;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Allocates the user's view command input to the correct parser in order to
 * call the appropriate entity's view parser.
 */
public class ViewCommandAllocator {

    /**
     * Parses the ID specified by the user in the view command input to determine
     * which entity is being viewed, and accordingly calls the appropriate parser to parse
     * the user input and returns the respective ViewCommand.
     * @param arg the user input.
     * @return the appropriate ViewCommand based on which entity is being viewed.
     * @throws ParseException if the format of the View command is incorrect.
     */
    public ViewCommand getViewCommand(String arg) throws ParseException {

        String prefix = AlfredParserUtil.getIdPrefix(arg);

        switch (prefix) {
        case CliSyntax.PREFIX_ENTITY_MENTOR:
            return new ViewMentorCommandParser().parse(arg);

        case CliSyntax.PREFIX_ENTITY_PARTICIPANT:
            return new ViewParticipantCommandParser().parse(arg);

        case CliSyntax.PREFIX_ENTITY_TEAM:
            return new ViewTeamCommandParser().parse(arg);

        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ViewCommand.MESSAGE_USAGE));
        }

    }

}
