package seedu.address.logic.finance.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_DAY;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_TO;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_TRANSACTION_METHOD;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.finance.commands.LendCommand;
import seedu.address.logic.finance.parser.exceptions.ParseException;
import seedu.address.model.finance.attributes.Amount;
import seedu.address.model.finance.attributes.Category;
import seedu.address.model.finance.attributes.Description;
import seedu.address.model.finance.attributes.Person;
import seedu.address.model.finance.attributes.TransactionDate;
import seedu.address.model.finance.attributes.TransactionMethod;
import seedu.address.model.finance.logentry.LendLogEntry;

/**
 * Parses input arguments and creates a new LendCommand object
 */
public class LendCommandParser implements Parser<LendCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the LendCommand
     * and returns an LendCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public LendCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_AMOUNT, PREFIX_DAY, PREFIX_DESCRIPTION,
                        PREFIX_TRANSACTION_METHOD, PREFIX_CATEGORY, PREFIX_TO);

        // If compulsory fields are empty
        if (!arePrefixesPresent(argMultimap, PREFIX_AMOUNT, PREFIX_DESCRIPTION,
                PREFIX_TRANSACTION_METHOD, PREFIX_TO)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LendCommand.MESSAGE_USAGE));
        }

        Amount amount = ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get());
        TransactionDate tDate;
        if (!argMultimap.getValue(PREFIX_DAY).isPresent()) {
            tDate = new TransactionDate();
        } else {
            tDate = ParserUtil.parseTransactionDate(argMultimap.getValue(PREFIX_DAY).get());
        }
        Description description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
        TransactionMethod tMethod = ParserUtil.parseTransactionMethod(
                argMultimap.getValue(PREFIX_TRANSACTION_METHOD).get());
        Set<Category> categoryList = ParserUtil.parseCategories(argMultimap.getAllValues(PREFIX_CATEGORY));
        Person to = ParserUtil.parsePerson(argMultimap.getValue(PREFIX_TO).get());

        LendLogEntry logEntry = new LendLogEntry(amount, tDate, description, tMethod,
                categoryList, to);

        return new LendCommand(logEntry);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
