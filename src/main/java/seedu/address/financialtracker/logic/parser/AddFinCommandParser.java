package seedu.address.financialtracker.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.financialtracker.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.financialtracker.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.financialtracker.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.financialtracker.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.financialtracker.logic.parser.CliSyntax.PREFIX_TYPE;

import java.util.stream.Stream;

import seedu.address.financialtracker.logic.commands.AddFinCommand;
import seedu.address.financialtracker.model.expense.Amount;
import seedu.address.financialtracker.model.expense.Date;
import seedu.address.financialtracker.model.expense.Description;
import seedu.address.financialtracker.model.expense.Expense;
import seedu.address.financialtracker.model.expense.Time;
import seedu.address.financialtracker.model.expense.Type;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddFinCommand.
 */
public class AddFinCommandParser implements Parser<AddFinCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * AddFinCommand and returns an AddFinCommand object for execution.
     * 
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddFinCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_AMOUNT, PREFIX_DESCRIPTION, PREFIX_TYPE,
                PREFIX_DATE, PREFIX_TIME);

        if (!arePrefixesPresent(argMultimap, PREFIX_AMOUNT, PREFIX_DESCRIPTION, PREFIX_TYPE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddFinCommand.MESSAGE_USAGE));
        }

        Amount amount = ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get());
        Description description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
        Type type = ParserUtil.parseType(argMultimap.getValue(PREFIX_TYPE).get());

        Date date;
        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
        } else {
            date = Date.getCurrentDate();
        }

        Time time;
        if (argMultimap.getValue(PREFIX_TIME).isPresent()) {
            time = ParserUtil.parseTime(argMultimap.getValue(PREFIX_TIME).get());
        } else {
            time = Time.getCurrentTime();
        }

        Expense expense = new Expense(date, time, amount, description, type);

        return new AddFinCommand(expense);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values
     * in the given {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
