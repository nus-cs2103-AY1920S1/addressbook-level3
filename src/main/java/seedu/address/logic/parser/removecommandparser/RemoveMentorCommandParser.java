package seedu.address.logic.parser.removecommandparser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.assigncommand.AssignMentorCommand;
import seedu.address.logic.commands.removecommand.RemoveMentorCommand;
import seedu.address.logic.parser.AlfredParserUtil;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.PrefixType;

/**
 * Parses input arguments and creates a new {@link AssignMentorCommand} object.
 */
public class RemoveMentorCommandParser implements Parser<RemoveMentorCommand> {
    private static final Logger logger = LogsCenter.getLogger(AssignMentorCommand.class);

    /**
     * Parses the given {@code String} of arguments in the context of the {@code EditParticipantCommand}
     * and returns an {@code EditParticipantCommand} object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format.
     */
    public RemoveMentorCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args);
        String[] individualIds = argMultimap.getPreamble().trim().split("\\s+");
        logger.info("size of String[] individualIds: " + individualIds.length);

        Id mentorId;
        Id teamId;

        try {
            mentorId = AlfredParserUtil.parseIndex(individualIds[0], PrefixType.M);
            teamId = AlfredParserUtil.parseIndex(individualIds[1], PrefixType.T);
        } catch (ParseException pe) {
            logger.info("Parse exception is thrown due to inability to parse out mentorId and teamId from user input");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RemoveMentorCommand.MESSAGE_USAGE), pe);
        }
        return new RemoveMentorCommand(mentorId, teamId);
    }
}
