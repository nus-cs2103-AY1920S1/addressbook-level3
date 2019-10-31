package seedu.address.logic.parser.assigncommandparser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.assigncommand.AssignParticipantCommand;
import seedu.address.logic.commands.editcommand.EditParticipantCommand;
import seedu.address.logic.parser.AlfredParserUtil;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.PrefixType;

/**
 * Parses input arguments and creates a new {@link EditParticipantCommand} object.
 */
public class AssignParticipantCommandParser implements Parser<AssignParticipantCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code EditParticipantCommand}
     * and returns an {@code EditParticipantCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public AssignParticipantCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args);

        String[] individualIds = argMultimap.getPreamble().trim().split("\\s+");

        Id participantId;
        Id teamId;

        try {
            participantId = AlfredParserUtil.parseIndex(individualIds[0], PrefixType.P);
            teamId = AlfredParserUtil.parseIndex(individualIds[1], PrefixType.T);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AssignParticipantCommand.MESSAGE_USAGE), pe);
        }
        return new AssignParticipantCommand(participantId, teamId);
    }
}
