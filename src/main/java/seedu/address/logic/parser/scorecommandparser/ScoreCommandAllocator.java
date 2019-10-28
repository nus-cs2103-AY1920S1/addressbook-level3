package seedu.address.logic.parser.scorecommandparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.scorecommand.ScoreCommand;
import seedu.address.logic.parser.AlfredParser;
import seedu.address.logic.parser.AlfredParserUtil;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.logic.parser.CommandAllocator;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Allocates the user's score command input to the correct parser in order to
 * call the appropriate score command parser depending on the scenario.
 */
public class ScoreCommandAllocator implements CommandAllocator<ScoreCommand> {

    private final Logger logger = LogsCenter.getLogger(AlfredParser.class);

    @Override
    public ScoreCommand allocate(String userInput) throws ParseException {
        String type;
        try {
            type = AlfredParserUtil.getSpecifierFromCommand(userInput);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScoreCommand.MESSAGE_USAGE));
        }

        String args = AlfredParserUtil.getArgumentsFromCommand(userInput);
        logger.info("Allocating Score command to appropriate parser...");

        switch (type) {
        case CliSyntax.SCORE_ADD:
            return new AddScoreCommandParser().parse(args);

        case CliSyntax.SCORE_UPDATE:
            return new SetScoreCommandParser().parse(args);

        case CliSyntax.SCORE_SUBTRACT:
            return new SubtractScoreCommandParser().parse(args);

        case CliSyntax.SCORE_RESET:
            return new ResetScoreCommandParser().parse(args);

        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        ScoreCommand.MESSAGE_USAGE));
        }
    }
}
