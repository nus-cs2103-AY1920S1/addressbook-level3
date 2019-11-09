package seedu.address.logic.parser.removecommandparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.assigncommand.AssignCommand;
import seedu.address.logic.commands.removecommand.RemoveCommand;
import seedu.address.logic.parser.AlfredParserUtil;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.logic.parser.CommandAllocator;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Allocates the user's assign command input to the correct parser in order to
 * call the appropriate entity's assign parser.
 */
public class RemoveCommandAllocator implements CommandAllocator<RemoveCommand> {
    private static final Logger logger = LogsCenter.getLogger(RemoveCommandAllocator.class);

    @Override
    public RemoveCommand allocate(String userInput) throws ParseException {
        String entity;
        String args;

        try {
            entity = AlfredParserUtil.getSpecifierFromCommand(userInput);
            args = AlfredParserUtil.getArgumentsFromCommand(userInput);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignCommand.MESSAGE_USAGE));
        }

        switch (entity) {

        case CliSyntax.ENTITY_PARTICIPANT:
            return new RemoveParticipantCommandParser().parse(args);

        case CliSyntax.ENTITY_MENTOR:
            return new RemoveMentorCommandParser().parse(args);

        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignCommand.MESSAGE_USAGE));
        }
    }
}
