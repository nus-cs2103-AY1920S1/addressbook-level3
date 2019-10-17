package seedu.address.logic.parser.editcommandparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.editcommand.EditCommand;
import seedu.address.logic.parser.AlfredParserUtil;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Allocates the user's edit command input to the correct parser in order to
 * call the appropriate entity's edit parser.
 */
public class EditCommandAllocator {

    /**
     * Parses the ID specified by the user in the edit command input to determine
     * which entity is being edited, and accordingly calls the appropriate parser to parse
     * the user input and returns the respective EditCommand.
     * @param args the user input.
     * @return the appropriate EditCommand based on which entity is being edited.
     * @throws ParseException if the format of the edit command is incorrect.
     */
    public EditCommand getEditCommand(String args) throws ParseException {
        String idPrefix = AlfredParserUtil.getIdPrefix(args);

        switch (idPrefix) {

        case CliSyntax.PREFIX_ENTITY_PARTICIPANT:
            return new EditParticipantCommandParser().parse(args);

        case CliSyntax.PREFIX_ENTITY_MENTOR:
            return new EditMentorCommandParser().parse(args);

        case CliSyntax.PREFIX_ENTITY_TEAM:
            return new EditTeamCommandParser().parse(args);

        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    seedu.address.logic.commands.EditCommand.MESSAGE_USAGE));
        }
    }
}
