package budgetbuddy.logic.parser.commandparsers.transactioncommandparsers;

import static budgetbuddy.logic.parser.CliSyntax.PREFIX_AMOUNT_FROM;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_AMOUNT_UNTIL;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_FROM;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_SORT;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_UNTIL;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import budgetbuddy.logic.commands.transactioncommands.TransactionListCommand;
import budgetbuddy.logic.parser.ArgumentMultimap;
import budgetbuddy.logic.parser.ArgumentTokenizer;
import budgetbuddy.logic.parser.CommandParser;
import budgetbuddy.logic.parser.CommandParserUtil;
import budgetbuddy.logic.parser.exceptions.ParseException;
import budgetbuddy.model.attributes.Category;
import budgetbuddy.model.attributes.Description;
import budgetbuddy.model.transaction.Amount;
import budgetbuddy.model.transaction.Transaction;
import budgetbuddy.model.transaction.TransactionMatchesConditionsPredicate;

/**
 * parses the <code>transaction list</code> command.
 */
public class TransactionListCommandParser implements CommandParser {
    @Override
    public String name() {
        return TransactionListCommand.COMMAND_WORD;
    }

    /**
     * Parses the given {@code String} of arguments in the context of the TransactionListCommand
     * and returns a TransactionListCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public TransactionListCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CATEGORY, PREFIX_FROM, PREFIX_UNTIL,
                        PREFIX_AMOUNT_FROM, PREFIX_AMOUNT_UNTIL, PREFIX_DESCRIPTION, PREFIX_SORT);

        //if arguments are not present, return empty Optionals
        List<String> categoriesList = argMultimap.getAllValues(PREFIX_CATEGORY);
        List<Category> categoryList = new ArrayList<>();
        for (String c : categoriesList) {
            categoryList.add(CommandParserUtil.parseCategory(c));
        }

        Optional<String> optionalFromString = argMultimap.getValue(PREFIX_FROM);
        Optional<LocalDate> optionalFrom =
                argMultimap.getValue(PREFIX_FROM).isPresent()
                        ? Optional.of(CommandParserUtil.parseDate(optionalFromString.get()))
                        : Optional.empty();

        Optional<String> optionalUntilString = argMultimap.getValue(PREFIX_UNTIL);
        Optional<LocalDate> optionalUntil =
                argMultimap.getValue(PREFIX_UNTIL).isPresent()
                        ? Optional.of(CommandParserUtil.parseDate(optionalUntilString.get()))
                        : Optional.empty();

        Optional<String> optionalFromAmountString = argMultimap.getValue(PREFIX_AMOUNT_FROM);
        Optional<Amount> optionalFromAmount =
                argMultimap.getValue(PREFIX_AMOUNT_FROM).isPresent()
                        ? Optional.of(CommandParserUtil.parseAmount(optionalFromAmountString.get()))
                        : Optional.empty();

        Optional<String> optionalUntilAmountString = argMultimap.getValue(PREFIX_AMOUNT_UNTIL);
        Optional<Amount> optionalUntilAmount =
                argMultimap.getValue(PREFIX_AMOUNT_UNTIL).isPresent()
                        ? Optional.of(CommandParserUtil.parseAmount(optionalUntilAmountString.get()))
                        : Optional.empty();

        Optional<String> optionalDescriptionString = argMultimap.getValue(PREFIX_DESCRIPTION);
        Optional<Description> optionalDescription =
                argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()
                        ? Optional.of(CommandParserUtil.parseDescription(optionalDescriptionString.get()))
                        : Optional.empty();

        Optional<String> optionalComparatorString = argMultimap.getValue(PREFIX_SORT);
        Optional<Comparator<Transaction>> optionalTransactionComparator =
                argMultimap.getValue(PREFIX_SORT).isPresent()
                        ? Optional.of(CommandParserUtil.parseTransactionComparator(optionalComparatorString.get()))
                        : Optional.empty();

        if(optionalFrom.isPresent() && optionalUntil.isPresent()) {
            if (optionalFrom.get().isAfter(optionalUntil.get())) {
                throw new ParseException("The start date of the range cannot be after the end date.");
            }
        }

        return new TransactionListCommand(new TransactionMatchesConditionsPredicate(categoryList,
                optionalFrom, optionalUntil, optionalFromAmount, optionalUntilAmount, optionalDescription),
                optionalTransactionComparator);
    }
}
