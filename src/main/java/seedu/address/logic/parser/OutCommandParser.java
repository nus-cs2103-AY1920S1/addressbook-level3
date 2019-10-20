package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.OutCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import seedu.address.model.Date;
import seedu.address.model.person.Name;
import seedu.address.model.tag.Tag;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.OutTransaction;
import seedu.address.model.transaction.Transaction;

/**
 * Parses input arguments and creates a new OutCommand object
 */
public class OutCommandParser implements Parser<OutCommand> {

    @Override
    public OutCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_AMOUNT, PREFIX_DATE);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_AMOUNT, PREFIX_DATE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, OutCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Amount amount = ParserUtil.parseAmount("-" + argMultimap.getValue(PREFIX_AMOUNT).get());
        Date date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());

        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Transaction transaction = new OutTransaction(amount, date);

        return new OutCommand(transaction);

    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
