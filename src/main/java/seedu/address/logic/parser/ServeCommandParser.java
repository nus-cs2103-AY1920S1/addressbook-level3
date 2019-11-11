package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BORROWER_ID;

import seedu.address.logic.commands.ServeCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.borrower.BorrowerId;

/**
 * Parses input arguments and creates a new ServeCommand object
 */
public class ServeCommandParser implements Parser<ServeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ServeCommand
     * and returns a ServeCommand object for execution.
     *
     * @return ServeCommand object.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ServeCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_BORROWER_ID);

        if (!argMultimap.getValue(PREFIX_BORROWER_ID).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ServeCommand.MESSAGE_USAGE));
        }

        BorrowerId borrowerId = ParserUtil.parseBorrowerId(argMultimap.getValue(PREFIX_BORROWER_ID).get());
        return new ServeCommand(borrowerId);
    }
}
