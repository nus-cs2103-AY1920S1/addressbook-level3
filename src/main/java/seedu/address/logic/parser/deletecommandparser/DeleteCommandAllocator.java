package seedu.address.logic.parser.deletecommandparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.deletecommand.DeleteCommand;
import seedu.address.logic.parser.AlfredParserUtil;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.logic.parser.CommandAllocator;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Allocates the user's delete command input to the correct parser in order to
 * call the appropriate entity's delete parser.
 */
public class DeleteCommandAllocator implements CommandAllocator<DeleteCommand> {

    @Override
    public DeleteCommand allocate(String userInput) throws ParseException {
        String entity;
        String args;

        try {
            entity = AlfredParserUtil.getSpecifierFromCommand(userInput);
            args = AlfredParserUtil.getArgumentsFromCommand(userInput);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        switch (entity) {

        case CliSyntax.ENTITY_PARTICIPANT:
            return new DeleteParticipantCommandParser().parse(args);

        case CliSyntax.ENTITY_MENTOR:
            return new DeleteMentorCommandParser().parse(args);

        case CliSyntax.ENTITY_TEAM:
            return new DeleteTeamCommandParser().parse(args);

        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }
    }
}
