package seedu.address.logic.parser.scorecommandparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.scorecommand.SetScoreCommand;
import seedu.address.logic.parser.AlfredParser;
import seedu.address.logic.parser.AlfredParserUtil;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.PrefixType;
import seedu.address.model.entity.Score;

/**
 * Parses input arguments and creates a new {@link SetScoreCommand} object.
 */
public class SetScoreCommandParser implements Parser<SetScoreCommand> {

    private final Logger logger = LogsCenter.getLogger(AlfredParser.class);

    @Override
    public SetScoreCommand parse(String args) throws ParseException {
        String score;
        String id;
        Id teamId;
        Score teamScore;

        try {
            id = AlfredParserUtil.getSpecifierFromCommand(args);
            score = AlfredParserUtil.getArgumentsFromCommand(args);
        } catch (ParseException pe) {
            logger.severe("Command is in an invalid format.");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetScoreCommand.MESSAGE_USAGE));
        }

        teamId = AlfredParserUtil.parseIndex(id, PrefixType.T);
        teamScore = AlfredParserUtil.parseScore(score);
        return new SetScoreCommand(teamId, teamScore);
    }
}
