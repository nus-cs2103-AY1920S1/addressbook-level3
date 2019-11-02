package seedu.address.logic.parser.scorecommandparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_TEAM_DISPLAYED_INDEX;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.scorecommand.ScoreCommand;
import seedu.address.logic.commands.scorecommand.SetScoreCommand;
import seedu.address.logic.parser.AlfredParser;
import seedu.address.logic.parser.AlfredParserUtil;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.exceptions.ParseIdException;
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

        if (args.equals("")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SetScoreCommand.RESET_MESSAGE_USAGE));
        }

        try {
            teamId = AlfredParserUtil.parseIndex(args, PrefixType.T);
        } catch (ParseIdException pe) {
            logger.severe("Team ID for Reset Score Command is Invalid.");
            throw new ParseIdException(MESSAGE_INVALID_TEAM_DISPLAYED_INDEX);
        }

        Score teamScore = new Score(0);
        return new SetScoreCommand(teamId, teamScore);
    }
}
