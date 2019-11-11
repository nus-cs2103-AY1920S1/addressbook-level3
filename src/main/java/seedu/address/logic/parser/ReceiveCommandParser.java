package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.stream.Stream;

import seedu.address.logic.commands.ReceiveCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.Description;
import seedu.address.model.transaction.ReceiveMoney;
import seedu.address.model.util.Date;

/**
 * Parses input arguments and creates a new InCommand object.
 */
public class ReceiveCommandParser implements Parser<ReceiveCommand> {

    @Override
    public ReceiveCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_AMOUNT, PREFIX_DATE, PREFIX_DESC);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_AMOUNT)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReceiveCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Person person = new Person(name);

        Amount amount = ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get());
        Description description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESC).orElse("receive"));

        Date date;
        if (argMultimap.getValue(PREFIX_DATE).isEmpty()) {
            date = Date.now();
        } else {
            date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
        }

        ReceiveMoney transaction = new ReceiveMoney(person, amount, date, description);

        return new ReceiveCommand(transaction);

    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
