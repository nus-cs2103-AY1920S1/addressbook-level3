package seedu.address.logic.parser.viewcommandparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.viewcommand.ViewCommand;
import seedu.address.logic.parser.AlfredParserUtil;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.logic.parser.CommandAllocator;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Allocates the user's view command input to the correct parser in order to
 * call the appropriate entity's view parser.
 */
public class ViewCommandAllocator implements CommandAllocator<ViewCommand> {

    @Override
    public ViewCommand allocate(String userInput) throws ParseException {
        String entity;
        String args;

        try {
            entity = AlfredParserUtil.getSpecifierFromCommand(userInput);
            args = AlfredParserUtil.getArgumentsFromCommand(userInput);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
        }

        switch (entity) {
        case CliSyntax.ENTITY_MENTOR:
            return new ViewMentorCommandParser().parse(args);

        case CliSyntax.ENTITY_PARTICIPANT:
            return new ViewParticipantCommandParser().parse(args);

        case CliSyntax.ENTITY_TEAM:
            return new ViewTeamCommandParser().parse(args);

        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
        }

    }

}
