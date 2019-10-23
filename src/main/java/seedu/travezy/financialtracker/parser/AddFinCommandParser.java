package seedu.travezy.financialtracker.parser;

import java.util.stream.Stream;

import seedu.travezy.financialtracker.commands.AddFinCommand;
import seedu.travezy.financialtracker.model.expense.Amount;
import seedu.travezy.financialtracker.model.expense.Country;
import seedu.travezy.financialtracker.model.expense.Description;
import seedu.travezy.financialtracker.model.expense.Expense;
import seedu.travezy.logic.parser.Prefix;
import seedu.travezy.logic.parser.exceptions.ParseException;
import seedu.travezy.commons.core.Messages;

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
                ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_AMOUNT, CliSyntax.PREFIX_DESCRIPTION, CliSyntax.PREFIX_COUNTRY);

        if (!arePrefixesPresent(argMultimap, CliSyntax.PREFIX_AMOUNT, CliSyntax.PREFIX_DESCRIPTION, CliSyntax.PREFIX_COUNTRY)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddFinCommand.MESSAGE_USAGE));
        }

        Amount amount = ParserUtil.parseAmount(argMultimap.getValue(CliSyntax.PREFIX_AMOUNT).get());
        Description description = ParserUtil.parseDescription(argMultimap.getValue(CliSyntax.PREFIX_DESCRIPTION).get());
        Country country = ParserUtil.parseCountry(argMultimap.getValue(CliSyntax.PREFIX_COUNTRY).get());

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
