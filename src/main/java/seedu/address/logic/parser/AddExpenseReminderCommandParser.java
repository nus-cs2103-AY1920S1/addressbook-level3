package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddExpenseReminderCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Amount;
import seedu.address.model.person.Description;
import seedu.address.model.person.ExpenseContainsTagPredicate;
import seedu.address.model.person.ExpenseReminder;
import seedu.address.model.person.ExpenseTracker;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddExpenseReminderCommandParser implements Parser<AddExpenseReminderCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddExpenseReminderCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DESC, PREFIX_AMOUNT, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_DESC, PREFIX_AMOUNT, PREFIX_TAG)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddExpenseReminderCommand.MESSAGE_USAGE));
        }

        Description desc = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESC).get());
        Amount amt = ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        ExpenseContainsTagPredicate predicate = new ExpenseContainsTagPredicate(tagList);
        ExpenseTracker tracker = new ExpenseTracker(predicate);
        ExpenseReminder newreminder = new ExpenseReminder(desc.toString(), (long) amt.value, tracker);

        return new AddExpenseReminderCommand(newreminder);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
