package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SHARE;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import seedu.address.logic.commands.SplitCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.Description;
import seedu.address.model.transaction.Split;
import seedu.address.model.util.Date;

/**
 * Parses input arguments and creates a new SplitCommand object
 */
public class SplitCommandParser implements Parser<SplitCommand> {

    @Override
    public SplitCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_AMOUNT, PREFIX_SHARE,
                PREFIX_DATE, PREFIX_DESC);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_AMOUNT, PREFIX_DESC)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SplitCommand.MESSAGE_USAGE));
        }

        List<Name> names = ParserUtil.parseNames(argMultimap.getAllValues(PREFIX_NAME));
        UniquePersonList people = new UniquePersonList();
        for (Name name : names) {
            people.add(new Person(name));
        }
        Description description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESC).get());
        Amount amount = ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get());
        Date date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).orElse(Date.now().toString()));
        List<Integer> shares = ParserUtil.parseShares(argMultimap.getAllValues(PREFIX_SHARE));

        if (shares.size() == 0) {
            shares = IntStream.rangeClosed(0, people.size()).map(i -> 1).boxed().collect(Collectors.toList());
        }

        boolean validSharesLength = shares.size() == people.size() || shares.size() == people.size() + 1;
        if (!validSharesLength) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SplitCommand.SHARES_FORMAT));
        }

        if (shares.size() == people.size()) {
            shares.add(0, 0);
        }

        Split transaction = new Split(amount, date, description, shares, people);
        return new SplitCommand(transaction);

    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values
     * in the given {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
