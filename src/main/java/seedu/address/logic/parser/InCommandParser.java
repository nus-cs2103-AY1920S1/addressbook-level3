package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.InCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import seedu.address.model.category.Category;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.BankAccountOperation;
import seedu.address.model.transaction.Description;
import seedu.address.model.transaction.InTransaction;
import seedu.address.model.util.Date;

/**
 * Parses input arguments and creates a new InCommand object.
 */
public class InCommandParser implements Parser<InCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the InCommand
     * and checks for valid conditions of arguments
     *
     * @param args user input after command word 'in'
     * @return InCommand of an InTransaction if all checks passes
     * @throws ParseException if amount is negative, 0 or larger than 1 million.
     * Exception is also thrown if compulsory fields Description, Amount or date is not entered correctly or missing.
     */
    @Override
    public InCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_AMOUNT, PREFIX_DATE, PREFIX_CATEGORY);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_AMOUNT, PREFIX_DATE)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, InCommand.MESSAGE_USAGE));
        }

        Description description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_NAME).get());

        Amount amount = ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get());

        Date date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());

        Set<Category> categoryList = ParserUtil.parseCategories(argMultimap.getAllValues(PREFIX_CATEGORY));

        if (categoryList.isEmpty()) {
            categoryList.add(Category.GENERAL);
        }

        BankAccountOperation transaction = new InTransaction(amount, date, description, categoryList);

        return new InCommand(transaction);

    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
