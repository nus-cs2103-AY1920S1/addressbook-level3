package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SERIAL_NUMBER;

import java.util.Optional;

import seedu.address.logic.commands.LoanCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.book.SerialNumber;

/**
 * Parses input arguments and creates a new LoanCommand object
 */
public class LoanCommandParser implements Parser<LoanCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the LoanCommand
     * and returns a LoanCommand object for execution.
     *
     * @param args User input.
     * @return LoanCommand object for execution.
     * @throws ParseException if {@code userInput} does not conform the expected format.
     */
    @Override
    public LoanCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_SERIAL_NUMBER);

        Optional<String> optionalSnString = argMultimap.getValue(PREFIX_SERIAL_NUMBER);
        if (optionalSnString.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    LoanCommand.MESSAGE_USAGE));
        }

        SerialNumber bookSn = ParserUtil.parseSerialNumber(optionalSnString.get());

        return new LoanCommand(bookSn);
    }
}
