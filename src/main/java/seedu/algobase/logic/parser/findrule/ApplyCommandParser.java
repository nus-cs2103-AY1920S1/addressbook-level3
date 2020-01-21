package seedu.algobase.logic.parser.findrule;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.logging.Logger;

import seedu.algobase.commons.core.LogsCenter;
import seedu.algobase.commons.core.index.Index;
import seedu.algobase.logic.commands.findrule.ApplyCommand;
import seedu.algobase.logic.parser.Parser;
import seedu.algobase.logic.parser.ParserUtil;
import seedu.algobase.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ApplyCommand object
 */
public class ApplyCommandParser implements Parser<ApplyCommand> {

    private static final Logger logger = LogsCenter.getLogger(ApplyCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the ApplyCommand
     * and returns a ApplyCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ApplyCommand parse(String args) throws ParseException {
        requireNonNull(args);

        logger.info("Parsing apply command with input: " + args);

        try {
            Index index = ParserUtil.parseIndex(args);
            return new ApplyCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ApplyCommand.MESSAGE_USAGE), pe);
        }
    }
}
