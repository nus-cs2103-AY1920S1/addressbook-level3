package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLASSID;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddEarningsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.earnings.Amount;
import seedu.address.model.earnings.Date;
import seedu.address.model.earnings.Earnings;
import seedu.address.model.classid.ClassId;

/**
 * Parses input arguments and creates a new AddEarningsCommand object
 */
public class AddEarningsCommandParser implements Parser<AddEarningsCommand> {

    /**
     * Parses the given {@code String} of arguments
     * in the context of the AddEarningsCommand
     * and returns an AddEarningsCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddEarningsCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_DATE, PREFIX_CLASSID, PREFIX_AMOUNT);

        if (!arePrefixesPresent(argMultimap, PREFIX_DATE, PREFIX_CLASSID, PREFIX_AMOUNT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEarningsCommand.MESSAGE_USAGE));
        }

        Date date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
        ClassId classId = ParserUtil.parseClassId(argMultimap.getValue(PREFIX_CLASSID).get());
        Amount amount = ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get());

        Earnings earnings = new Earnings(date, classId, amount);

        return new AddEarningsCommand(earnings);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
