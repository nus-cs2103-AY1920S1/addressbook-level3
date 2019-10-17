package seedu.address.logic.parser.deletecommandparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.deletecommand.DeleteCommand;
import seedu.address.logic.parser.AlfredParserUtil;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Allocates the user's delete command input to the correct parser in order to
 * call the appropriate entity's delete parser.
 */
public class DeleteCommandAllocator {

    /**
     * Parses the ID specified by the user in the delete command input to determine
     * which entity is being deleted, and accordingly calls the appropriate parser to parse
     * the user input and returns the respective DeleteCommand.
     * @param args the user input.
     * @return the appropriate EditCommand based on which entity is being edited.
     * @throws ParseException if the format of the edit command is incorrect.
     */
    public DeleteCommand getDeleteCommand(String args) throws ParseException {
        String idPrefix = AlfredParserUtil.getIdPrefix(args);

        switch (idPrefix) {

        case CliSyntax.PREFIX_ENTITY_PARTICIPANT:
            return new DeleteParticipantCommandParser().parse(args);

        case CliSyntax.PREFIX_ENTITY_MENTOR:
            return new DeleteMentorCommandParser().parse(args);

        case CliSyntax.PREFIX_ENTITY_TEAM:
            return new DeleteTeamCommandParser().parse(args);

        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    seedu.address.logic.commands.DeleteCommand.MESSAGE_USAGE));
        }
    }
}
