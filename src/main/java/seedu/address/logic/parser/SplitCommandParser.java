package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SHARE;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Stream;

import seedu.address.logic.commands.SplitCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.SplitTransaction;
import seedu.address.model.util.Date;

/**
 * Parses input arguments and creates a new SplitCommand object
 */
public class SplitCommandParser implements Parser<SplitCommand> {

    @Override
    public SplitCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_AMOUNT, PREFIX_SHARE,
                PREFIX_DATE);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_AMOUNT, PREFIX_SHARE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SplitCommand.MESSAGE_USAGE));
        }

        List<Name> names = ParserUtil.parseNames(argMultimap.getAllValues(PREFIX_NAME));
        UniquePersonList people = new UniquePersonList();
        for (Name name : names) {
            people.add(new Person(name));
        }
        Amount amount = ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get());
        Date date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).orElse(timeNowString()));
        List<Integer> shares = ParserUtil.parseShares(argMultimap.getAllValues(PREFIX_SHARE));

        SplitTransaction transaction = new SplitTransaction(amount, date, shares, people);
        return new SplitCommand(transaction);

    }

    private String timeNowString() {
        return DateTimeFormatter.ofPattern("ddMMyyyy").format(LocalDate.now());
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values
     * in the given {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
