package thrift.logic.parser;

import java.util.Set;

import thrift.commons.core.Messages;
import thrift.logic.commands.AddIncomeCommand;
import thrift.logic.parser.exceptions.ParseException;
import thrift.model.tag.Tag;
import thrift.model.transaction.Description;
import thrift.model.transaction.Income;
import thrift.model.transaction.TransactionDate;
import thrift.model.transaction.Value;

/**
 * Parses input arguments and creates a new AddIncomeCommand object.
 */
public class AddIncomeCommandParser extends AddTransactionCommandParser implements Parser<AddIncomeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddIncomeCommand
     * and returns an AddIncomeCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public AddIncomeCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_NAME, CliSyntax.PREFIX_COST, CliSyntax.PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, CliSyntax.PREFIX_NAME, CliSyntax.PREFIX_COST)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    AddIncomeCommand.MESSAGE_USAGE));
        }

        Description description = parseTransactionDescription(argMultimap);
        Value value = parseTransactionValue(argMultimap);
        TransactionDate date = parseTransactionDate();
        Set<Tag> tagList = parseTransactionTags(argMultimap);

        Income income = new Income(description, value, date, tagList);

        return new AddIncomeCommand(income);
    }
}
