package seedu.address.logic.finance.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.finance.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.finance.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.finance.parser.CliSyntax.PREFIX_ITEM;
import static seedu.address.logic.finance.parser.CliSyntax.PREFIX_PLACE;
import static seedu.address.logic.finance.parser.CliSyntax.PREFIX_TRANSACTION_METHOD;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.finance.commands.SpendCommand;
import seedu.address.logic.finance.parser.exceptions.ParseException;
import seedu.address.model.finance.logentry.Address;
import seedu.address.model.finance.logentry.Amount;
import seedu.address.model.finance.logentry.Email;
import seedu.address.model.finance.logentry.LogEntry;
import seedu.address.model.finance.logentry.Phone;
import seedu.address.model.finance.attributes.Tag;


/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<SpendCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SpendCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DAY, PREFIX_ITEM, PREFIX_CATEGORY, PREFIX_PLACE, PREFIX_TRANSACTION_METHOD);

        if (!arePrefixesPresent(argMultimap, PREFIX_DAY, PREFIX_PLACE, PREFIX_ITEM, PREFIX_CATEGORY)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SpendCommand.MESSAGE_USAGE));
        }

        Amount amount = ParserUtil.parseName(argMultimap.getValue(PREFIX_DAY).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_ITEM).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_CATEGORY).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_PLACE).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TRANSACTION_METHOD));

        LogEntry logEntry = new LogEntry(amount, phone, email, address, tagList);

        return new SpendCommand(logEntry);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
