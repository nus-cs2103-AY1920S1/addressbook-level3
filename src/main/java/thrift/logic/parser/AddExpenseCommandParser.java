package thrift.logic.parser;

import java.util.Set;

import thrift.commons.core.Messages;
import thrift.logic.commands.AddExpenseCommand;
import thrift.logic.parser.exceptions.ParseException;
import thrift.model.tag.Tag;
import thrift.model.transaction.Description;
import thrift.model.transaction.Expense;
import thrift.model.transaction.Remark;
import thrift.model.transaction.TransactionDate;
import thrift.model.transaction.Value;

/**
 * Parses input arguments and creates a new AddExpenseCommand object.
 */
public class AddExpenseCommandParser extends AddTransactionCommandParser implements Parser<AddExpenseCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddExpenseCommand
     * and returns an AddExpenseCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public AddExpenseCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_NAME, CliSyntax.PREFIX_COST,
                        CliSyntax.PREFIX_REMARK, CliSyntax.PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, CliSyntax.PREFIX_NAME, CliSyntax.PREFIX_COST)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    AddExpenseCommand.MESSAGE_USAGE));
        }

        Description description = parseTransactionDescription(argMultimap);
        Value value = parseTransactionValue(argMultimap);
        Remark remark = parseTransactionRemark(argMultimap);
        TransactionDate date = parseTransactionDate();
        Set<Tag> tagList = parseTransactionTags(argMultimap);

        Expense expense = new Expense(description, value, remark, date, tagList);

        return new AddExpenseCommand(expense);
    }
}
