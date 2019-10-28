package seedu.address.logic.parser.scorecommandparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.scorecommand.ScoreCommand;
import seedu.address.logic.commands.scorecommand.SetScoreCommand;
import seedu.address.logic.parser.AlfredParser;
import seedu.address.logic.parser.AlfredParserUtil;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.PrefixType;
import seedu.address.model.entity.Score;

/**
 * Parses input arguments and creates a new {@link SetScoreCommand} object to set the score to 0.
 */
public class ResetScoreCommandParser implements Parser<ScoreCommand> {

    private final Logger logger = LogsCenter.getLogger(AlfredParser.class);

    @Override
    public SetScoreCommand parse(String args) throws ParseException {
        Id teamId;
        Score teamScore = new Score(0);

        try {
            teamId = AlfredParserUtil.parseIndex(args, PrefixType.T);
        } catch (ParseException e) {
            logger.severe("User inputted team index is invalid.");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScoreCommand.RESET_MESSAGE_USAGE));
        }

        return new SetScoreCommand(teamId, teamScore);
    }


}
