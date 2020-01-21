package seedu.algobase.logic.parser.findrule;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.logging.Logger;

import seedu.algobase.commons.core.LogsCenter;
import seedu.algobase.commons.core.index.Index;
import seedu.algobase.logic.commands.findrule.DeleteFindRuleCommand;
import seedu.algobase.logic.parser.Parser;
import seedu.algobase.logic.parser.ParserUtil;
import seedu.algobase.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteFindRuleCommand object
 */
public class DeleteFindRuleParser implements Parser<DeleteFindRuleCommand> {

    private static final Logger logger = LogsCenter.getLogger(DeleteFindRuleParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteFindRuleCommand
     * and returns a DeleteFindRuleCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteFindRuleCommand parse(String args) throws ParseException {
        requireNonNull(args);

        logger.info("Parsing delete find rule command with input: " + args);

        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteFindRuleCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteFindRuleCommand.MESSAGE_USAGE), pe);
        }
    }

}
