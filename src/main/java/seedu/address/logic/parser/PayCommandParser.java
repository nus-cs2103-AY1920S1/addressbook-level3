package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOLLAR;

import java.util.Optional;

import seedu.address.commons.util.FineUtil;
import seedu.address.logic.commands.PayCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new PayCommand object.
 */
public class PayCommandParser implements Parser<PayCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the PayCommand
     * and returns a PayCommand object for execution.
     *
     * @param userInput User input.
     * @return PayCommand object for execution.
     * @throws ParseException if {@code userInput} does not conform the expected format.
     */
    @Override
    public PayCommand parse(String userInput) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(userInput, PREFIX_DOLLAR);

        Optional<String> optionalDollarAmount = argMultimap.getValue(PREFIX_DOLLAR);
        if (optionalDollarAmount.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    PayCommand.MESSAGE_USAGE));
        }

        double dollarAmount = ParserUtil.parseDollar(optionalDollarAmount.get());
        int centAmount = FineUtil.dollarsToCents(dollarAmount);
        return new PayCommand(centAmount);
    }
}
