package seedu.address.logic.parser.editcommandparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.editcommand.EditCommand;
import seedu.address.logic.parser.AlfredParserUtil;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.logic.parser.CommandAllocator;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Allocates the user's edit command input to the correct parser in order to
 * call the appropriate entity's edit parser.
 */
public class EditCommandAllocator implements CommandAllocator<EditCommand> {

    @Override
    public EditCommand allocate(String userInput) throws ParseException {

        String entity = AlfredParserUtil.getEntityFromCommand(userInput, EditCommand.MESSAGE_USAGE);
        String args = AlfredParserUtil.getArgumentsFromCommand(userInput, EditCommand.MESSAGE_USAGE);

        switch (entity) {

        case CliSyntax.ENTITY_PARTICIPANT:
            return new EditParticipantCommandParser().parse(args);

        case CliSyntax.ENTITY_MENTOR:
            return new EditMentorCommandParser().parse(args);

        case CliSyntax.ENTITY_TEAM:
            return new EditTeamCommandParser().parse(args);

        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }
    }
}
