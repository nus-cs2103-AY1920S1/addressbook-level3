package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.SetCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.category.Category;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.Budget;
import seedu.address.model.util.Date;

/**
 * Parses input arguments and creates a new SetCommand object.
 */
public class SetCommandParser implements Parser<SetCommand> {

    @Override
    public SetCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_AMOUNT, PREFIX_DATE, PREFIX_CATEGORY);

        if (!arePrefixesPresent(argMultimap, PREFIX_AMOUNT, PREFIX_DATE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetCommand.MESSAGE_USAGE));
        }

        /* handles negative amount */
        if (argMultimap.getValue(PREFIX_AMOUNT).get().toCharArray()[0] == (NEGATIVE_AMOUNT_SIGN)) {
            throw new ParseException(String.format(SetCommand.MESSAGE_AMOUNT_NEGATIVE));
        }

        /* handles 0 value */
        if (argMultimap.getValue(PREFIX_AMOUNT).get().toCharArray()[0] == (ZERO_AMOUNT)
                && argMultimap.getValue(PREFIX_AMOUNT).get().toCharArray().length == 1) {
            throw new ParseException(String.format(SetCommand.MESSAGE_AMOUNT_ZERO));

        }

        /* handles overflow value */
        if (Double.parseDouble(argMultimap.getValue(PREFIX_AMOUNT).get()) >= 1000000) {
            throw new ParseException(String.format(SetCommand.MESSAGE_AMOUNT_OVERFLOW));
        }

        Amount budget = ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get());

        Date date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());

        if (date.isPast()) {
            throw new ParseException(String.format(SetCommand.MESSAGE_DATE_PAST));
        }

        Set<Category> categoryList = ParserUtil.parseCategories(argMultimap.getAllValues(PREFIX_CATEGORY));

        Budget newBudget = new Budget(budget, date, categoryList);

        return new SetCommand(newBudget);

    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
