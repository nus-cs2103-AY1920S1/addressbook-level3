package budgetbuddy.logic.parser.commandparsers.transactioncommandparsers;

import static budgetbuddy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_ACCOUNT;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_DATE;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_DIRECTION;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import budgetbuddy.logic.commands.transactioncommands.TransactionAddCommand;
import budgetbuddy.logic.parser.ArgumentMultimap;
import budgetbuddy.logic.parser.ArgumentTokenizer;
import budgetbuddy.logic.parser.CommandParser;
import budgetbuddy.logic.parser.CommandParserUtil;
import budgetbuddy.logic.parser.Prefix;
import budgetbuddy.logic.parser.exceptions.ParseException;
import budgetbuddy.model.attributes.Amount;
import budgetbuddy.model.attributes.Category;
import budgetbuddy.model.attributes.Description;
import budgetbuddy.model.attributes.Direction;
import budgetbuddy.model.attributes.Name;
import budgetbuddy.model.transaction.Transaction;

/**
 * Parses input and creates a new TransactionAddCommand
 */
public class TransactionAddCommandParser implements CommandParser<TransactionAddCommand> {
    @Override
    public String name() {
        return TransactionAddCommand.COMMAND_WORD;
    }

    /**
     * Parses the given {@code String} of arguments in the context of the TransactionAddCommand
     * and returns an TransactionAddCommand object for execution.
     * @throws ParseException If the user input does not conform to the expected format.
     */
    @Override
    public TransactionAddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultiMap =
                ArgumentTokenizer.tokenize(args, PREFIX_DIRECTION, PREFIX_AMOUNT, PREFIX_DESCRIPTION, PREFIX_ACCOUNT,
                        PREFIX_CATEGORY, PREFIX_DATE);

        if (!arePrefixesPresent(argMultiMap, PREFIX_DIRECTION, PREFIX_AMOUNT, PREFIX_DESCRIPTION)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    TransactionAddCommand.MESSAGE_USAGE));
        }

        Direction direction = CommandParserUtil.parseDirection(argMultiMap.getValue(PREFIX_DIRECTION).get());

        Amount amount = CommandParserUtil.parseAmount(argMultiMap.getValue(PREFIX_AMOUNT).get());

        Description description = CommandParserUtil.parseDescription(argMultiMap.getValue(PREFIX_DESCRIPTION).get());

        Optional<String> optionalAccountName = argMultiMap.getValue(PREFIX_ACCOUNT);
        Name accountName = optionalAccountName.isPresent()
                ? CommandParserUtil.parseAccountName(optionalAccountName.get())
                : null;

        List<String> categoriesList = argMultiMap.getAllValues(PREFIX_CATEGORY);
        Set<Category> categoryList = new HashSet<>();
        for (String c : categoriesList) {
            categoryList.add(CommandParserUtil.parseCategory(c));
        }

        /*
         * Return current date when optionalDate is not present
         */
        Optional<String> optionalDate = argMultiMap.getValue(PREFIX_DATE);
        LocalDate date = optionalDate.isPresent()
                ? CommandParserUtil.parseDate(optionalDate.get())
                : LocalDate.now();

        Transaction transaction = new Transaction(date, amount, direction, description, categoryList);

        return new TransactionAddCommand(transaction, accountName);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
