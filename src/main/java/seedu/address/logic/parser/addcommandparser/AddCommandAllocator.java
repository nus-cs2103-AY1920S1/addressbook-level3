package seedu.address.logic.parser.addcommandparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.addcommand.AddCommand;
import seedu.address.logic.parser.AlfredParser;
import seedu.address.logic.parser.AlfredParserUtil;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.logic.parser.CommandAllocator;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Allocates the user's add command input to the correct parser in order to
 * call the appropriate entity's add parser.
 */
public class AddCommandAllocator implements CommandAllocator<AddCommand> {

    private final Logger logger = LogsCenter.getLogger(AlfredParser.class);

    @Override
    public AddCommand allocate(String userInput) throws ParseException {
        String entity;
        String args;
        try {
            entity = AlfredParserUtil.getSpecifierFromCommand(userInput);
            args = AlfredParserUtil.getArgumentsFromCommand(userInput);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        switch (entity) {

        case CliSyntax.ENTITY_MENTOR:
            //logger.info("Adding a new Mentor...");
            return new AddMentorCommandParser().parse(args);

        case CliSyntax.ENTITY_PARTICIPANT:
            //logger.info("Adding a new Participant...");
            return new AddParticipantCommandParser().parse(args);

        case CliSyntax.ENTITY_TEAM:
            //logger.info("Adding a new Team...");
            return new AddTeamCommandParser().parse(args);

        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        }
    }

}
