package seedu.address.financialtracker.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.financialtracker.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.financialtracker.parser.CliSyntax.PREFIX_COUNTRY;
import static seedu.address.financialtracker.parser.CliSyntax.PREFIX_DESCRIPTION;

import java.util.stream.Stream;

import seedu.address.financialtracker.commands.AddFinCommand;
import seedu.address.financialtracker.model.expense.Amount;
import seedu.address.financialtracker.model.expense.Country;
import seedu.address.financialtracker.model.expense.Description;
import seedu.address.financialtracker.model.expense.Expense;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddFinCommand.
 */
public class AddFinCommandParser implements Parser<AddFinCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddFinCommand
     * and returns an AddFinCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddFinCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_AMOUNT, PREFIX_DESCRIPTION, PREFIX_COUNTRY);

        if (!arePrefixesPresent(argMultimap, PREFIX_AMOUNT, PREFIX_DESCRIPTION, PREFIX_COUNTRY)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddFinCommand.MESSAGE_USAGE));
        }

        Amount amount = ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get());
        Description description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
        Country country = ParserUtil.parseCountry(argMultimap.getValue(PREFIX_COUNTRY).get());

        Expense expense = new Expense(amount, description, country);

        return new AddFinCommand(expense);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
