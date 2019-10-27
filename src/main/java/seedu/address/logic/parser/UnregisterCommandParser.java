package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BORROWER_ID;

import seedu.address.logic.commands.UnregisterCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.borrower.BorrowerId;

/**
 * Parses input arguments and creates a new UnregisterCommand object
 */
public class UnregisterCommandParser implements Parser<UnregisterCommand> {
    /**
     * Parses {@code userInput} into a command and returns it.
     *
     * @param userInput
     * @throws ParseException if {@code userInput} does not conform the expected format
     */
    @Override
    public UnregisterCommand parse(String userInput) throws ParseException, CommandException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(userInput, PREFIX_BORROWER_ID);

        if (!argMultimap.getValue(PREFIX_BORROWER_ID).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnregisterCommand.MESSAGE_USAGE));
        }

        BorrowerId id = ParserUtil.parseBorrowerId(argMultimap.getValue(PREFIX_BORROWER_ID).get());
        return new UnregisterCommand(id);
    }
}
