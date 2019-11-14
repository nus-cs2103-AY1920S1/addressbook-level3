package seedu.address.logic.parser.deletecommandparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.deletecommand.DeleteCommand;
import seedu.address.logic.commands.deletecommand.DeleteParticipantCommand;
import seedu.address.logic.parser.AlfredParserUtil;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.PrefixType;

/**
 * Parses input arguments and creates a new {@link DeleteParticipantCommand} object.
 */
public class DeleteParticipantCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code DeleteParticipantCommand}
     * and returns a {@code DeleteParticipantCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public DeleteParticipantCommand parse(String args) throws ParseException {
        if (args.equals("")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteParticipantCommand.MESSAGE_USAGE));
        }

        try {
            Id id = AlfredParserUtil.parseIndex(args, PrefixType.P);
            return new DeleteParticipantCommand(id);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(DeleteParticipantCommand.MESSAGE_INVALID_PARTICIPANT_DISPLAYED_INDEX), pe);
        }
    }

}
