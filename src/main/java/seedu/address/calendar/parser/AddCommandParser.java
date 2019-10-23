package seedu.address.calendar.parser;

import seedu.address.calendar.commands.AddCommitmentCommand;
import seedu.address.calendar.model.*;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;

import java.util.Optional;

class AddCommandParser {
    private static final String MESSAGE_INVALID_COMMAND_FORMAT = "Incorrect add command format. %s";
    private static final Prefix[] prefixes = { CliSyntax.PREFIX_DAY, CliSyntax.PREFIX_MONTH,
            CliSyntax.PREFIX_YEAR, CliSyntax.PREFIX_NAME, CliSyntax.PREFIX_INFO };

    AddCommitmentCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, prefixes);

        if (!ParserUtil.arePrefixesPresent(argMultimap, CliSyntax.PREFIX_DAY, CliSyntax.PREFIX_NAME)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommitmentCommand.MESSAGE_USAGE));
        } else if (ParserUtil.hasMultiplePrefixes(argMultimap, prefixes)) {
            throw new ParseException(ParserUtil.MESSAGE_DUPLICATED_ARG);
        }

        Optional<MonthOfYear> month = new MonthParser().parse(argMultimap.getValue(CliSyntax.PREFIX_MONTH));
        Optional<Year> year = new YearParser().parse(argMultimap.getValue(CliSyntax.PREFIX_YEAR));
        Optional<Day> day = new DayParser().parse(argMultimap.getValue(CliSyntax.PREFIX_DAY), month, year);

        Date date = new Date(day, month, year);

        Name name = new NameParser().parse(argMultimap.getValue(CliSyntax.PREFIX_NAME)).get();
        Optional<Info> info = new InfoParser().parse(argMultimap.getValue(CliSyntax.PREFIX_INFO));

        Commitment commitment = new Commitment(name, date, info);

        return new AddCommitmentCommand(commitment);
    }
}
