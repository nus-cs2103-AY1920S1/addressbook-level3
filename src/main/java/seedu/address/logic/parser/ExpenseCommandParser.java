package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPENSE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARTICIPANT;

import java.util.stream.Stream;

import seedu.address.logic.commands.ExpenseCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ExpenseCommand object
 */
public class ExpenseCommandParser implements Parser<ExpenseCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ExpenseCommand
     * and returns an ExpenseCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ExpenseCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PARTICIPANT, PREFIX_EXPENSE);
        if (!arePrefixesPresent(argMultimap, PREFIX_PARTICIPANT, PREFIX_EXPENSE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExpenseCommand.MESSAGE_USAGE));
        }
        return new ExpenseCommand();
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
