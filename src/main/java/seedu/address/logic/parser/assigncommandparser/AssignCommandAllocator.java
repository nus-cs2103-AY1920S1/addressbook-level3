package seedu.address.logic.parser.assigncommandparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.assigncommand.AssignCommand;
import seedu.address.logic.parser.AlfredParserUtil;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.logic.parser.CommandAllocator;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Allocates the user's assign command input to the correct parser in order to
 * call the appropriate entity's assign parser.
 */
public class AssignCommandAllocator implements CommandAllocator<AssignCommand> {
    private static final Logger logger = LogsCenter.getLogger(AssignCommandAllocator.class);

    @Override
    public AssignCommand allocate(String userInput) throws ParseException {
        String entity;
        String args;

        try {
            entity = AlfredParserUtil.getSpecifierFromCommand(userInput);
            args = AlfredParserUtil.getArgumentsFromCommand(userInput);
            logger.info("Arguments of AssignCommand is: " + args);
        } catch (ParseException pe) {
            logger.severe("Parse exception is thrown as entity and args cannot pre parsed out from input");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignCommand.MESSAGE_USAGE));
        }

        switch (entity) {

        case CliSyntax.ENTITY_PARTICIPANT:
            return new AssignParticipantCommandParser().parse(args);

        case CliSyntax.ENTITY_MENTOR:
            return new AssignMentorCommandParser().parse(args);

        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignCommand.MESSAGE_USAGE));
        }
    }
}
