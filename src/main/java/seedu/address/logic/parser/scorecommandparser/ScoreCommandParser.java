package seedu.address.logic.parser.scorecommandparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_TEAM_DISPLAYED_INDEX;

import java.util.logging.Logger;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.scorecommand.AddScoreCommand;
import seedu.address.logic.commands.scorecommand.ScoreCommand;
import seedu.address.logic.commands.scorecommand.SetScoreCommand;
import seedu.address.logic.commands.scorecommand.SubtractScoreCommand;
import seedu.address.logic.parser.AlfredParser;
import seedu.address.logic.parser.AlfredParserUtil;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.exceptions.ParseIdException;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.PrefixType;
import seedu.address.model.entity.Score;

/**
 * Allocates the user's score command input to the correct parser in order to
 * call the appropriate score command parser depending on the scenario.
 */
public class ScoreCommandParser implements Parser<ScoreCommand> {

    private static Logger logger = LogsCenter.getLogger(AlfredParser.class);
    private static final Score RESET_SCORE = new Score(0);
    private static final Pattern VALIDATE_METHOD = Pattern.compile("\\b(add|reset|sub|set)\\b");

    @Override
    public ScoreCommand parse(String userInput) throws ParseException {
        String type;
        try {
            type = AlfredParserUtil.getSpecifierFromCommand(userInput);
            validateCommandType(type);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScoreCommand.MESSAGE_USAGE));
        }

        String args = AlfredParserUtil.getArgumentsFromCommand(userInput);

        Score score = getScoreFromArguments(type, args);
        Id id = getIdFromArguments(type, args);

        switch (type) {
        case CliSyntax.SCORE_ADD:
            return new AddScoreCommand(id, score);

        case CliSyntax.SCORE_UPDATE:
            return new SetScoreCommand(id, score);

        case CliSyntax.SCORE_SUBTRACT:
            return new SubtractScoreCommand(id, score);

        case CliSyntax.SCORE_RESET:
            return new SetScoreCommand(id, RESET_SCORE);

        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        ScoreCommand.MESSAGE_USAGE));
        }
    }

    private static void validateCommandType(String type) throws ParseException {
        if (!Pattern.matches(VALIDATE_METHOD.pattern(), type)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScoreCommand.MESSAGE_USAGE));
        }
    }

    private static Id getIdFromArguments(String specifier, String args) throws ParseException {
        try {
            String id = AlfredParserUtil.getSpecifierFromCommand(args);
            Id teamId = AlfredParserUtil.parseIndex(id, PrefixType.T);
            return teamId;
        } catch (ParseIdException p) {
            logger.severe("Team ID for Score Command is Invalid.");
            throw new ParseIdException(MESSAGE_INVALID_TEAM_DISPLAYED_INDEX);
        } catch (ParseException pe) {
            logger.severe("Score Command is in an invalid format.");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    getAppropriateUsageMessage(specifier)));
        }
    }

    private static Score getScoreFromArguments(String specifier, String args) throws ParseException {
        String score;
        if (specifier.equals(CliSyntax.SCORE_RESET)) {
            return RESET_SCORE;
        }
        try {
            score = AlfredParserUtil.getNonEmptyArgumentFromCommand(args);
        } catch (ParseException pe) {
            logger.severe("Score Command is in an invalid format.");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    getAppropriateUsageMessage(specifier)));
        }
        return AlfredParserUtil.parseScore(score);
    }

    private static String getAppropriateUsageMessage(String specifier) throws ParseException {
        specifier = specifier.trim();
        switch (specifier) {
        case CliSyntax.SCORE_ADD:
            return AddScoreCommand.MESSAGE_USAGE;
        case CliSyntax.SCORE_UPDATE:
            return SetScoreCommand.MESSAGE_USAGE;
        case CliSyntax.SCORE_SUBTRACT:
            return SubtractScoreCommand.MESSAGE_USAGE;
        case CliSyntax.SCORE_RESET:
            return SetScoreCommand.RESET_MESSAGE_USAGE;
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScoreCommand.MESSAGE_USAGE));
        }
    }
}
