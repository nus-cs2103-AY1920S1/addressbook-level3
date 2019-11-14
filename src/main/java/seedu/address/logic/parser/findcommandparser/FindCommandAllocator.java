package seedu.address.logic.parser.findcommandparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.findcommand.FindCommand;
import seedu.address.logic.parser.AlfredParser;
import seedu.address.logic.parser.AlfredParserUtil;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.logic.parser.CommandAllocator;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Allocates the user's find command input top the correct parser.
 */
public class FindCommandAllocator implements CommandAllocator<FindCommand> {
    private final Logger logger = LogsCenter.getLogger(AlfredParser.class);

    @Override
    public FindCommand allocate(String userInput) throws ParseException {
        String entity;
        String args;
        try {
            entity = AlfredParserUtil.getSpecifierFromCommand(userInput);
            args = AlfredParserUtil.getArgumentsFromCommand(userInput);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindCommand.MESSAGE_USAGE));
        }

        switch (entity) {
        case CliSyntax.ENTITY_MENTOR:
            return new FindMentorCommandParser().parse(args);

        case CliSyntax.ENTITY_PARTICIPANT:
            return new FindParticipantCommandParser().parse(args);

        case CliSyntax.ENTITY_TEAM:
            return new FindTeamCommandParser().parse(args);

        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindCommand.MESSAGE_USAGE));
        }
    }
}
