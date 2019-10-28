package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPENSE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;


import java.math.BigDecimal;
import java.util.Date;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddSpendingCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.finance.Spending;


/**
 * Parses input arguments and creates a new AddSpendingCommand object
 */
public class AddSpendingCommandParser implements Parser<AddSpendingCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddBudgetCommand
     * and returns an AddSpendingCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddSpendingCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DESCRIPTION, PREFIX_EXPENSE, PREFIX_TIME);

        if (!arePrefixesPresent(argMultimap, PREFIX_DESCRIPTION, PREFIX_EXPENSE, PREFIX_TIME)
                || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddSpendingCommand.MESSAGE_USAGE));
        }

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddSpendingCommand.MESSAGE_USAGE), pe);
        }

        Date date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_TIME).get());
        BigDecimal spending = ParserUtil.parseSpending(argMultimap.getValue(PREFIX_EXPENSE).get());
        String description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get()).toString();
        Spending expense = new Spending(spending, date, description);

        return new AddSpendingCommand(index, expense);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
