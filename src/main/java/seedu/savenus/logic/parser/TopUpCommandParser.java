package seedu.savenus.logic.parser;

import static seedu.savenus.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.math.BigDecimal;

import seedu.savenus.logic.commands.TopUpCommand;
import seedu.savenus.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new BuyCommand object
 */
public class TopUpCommandParser implements Parser<TopUpCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the BuyCommand
     * and returns an BuyCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public TopUpCommand parse(String args) throws ParseException {
        try {
            BigDecimal topUpAmount = ParserUtil.parseTopUpAmount(args);
            return new TopUpCommand(topUpAmount);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, TopUpCommand.MESSAGE_USAGE), pe);
        }
    }
}
