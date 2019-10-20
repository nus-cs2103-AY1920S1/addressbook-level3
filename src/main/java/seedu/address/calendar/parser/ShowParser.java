package seedu.address.calendar.parser;

import seedu.address.calendar.commands.ShowCommand;
import seedu.address.calendar.model.MonthOfYear;
import seedu.address.calendar.model.Year;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;

import java.util.stream.Stream;

public class ShowParser {
    private static final String MESSAGE_INVALID_COMMAND_FORMAT = "Incorrect month/year format.";

    ShowCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_MONTH, CliSyntax.PREFIX_YEAR);

        if (!arePrefixesPresent(argMultimap, CliSyntax.PREFIX_MONTH)
                || hasMultiplePrefixes(argMultimap, CliSyntax.PREFIX_MONTH, CliSyntax.PREFIX_YEAR)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowCommand.MESSAGE_USAGE));
        }

        MonthOfYear monthOfYear = new MonthParser().parse(argMultimap.getValue(CliSyntax.PREFIX_MONTH).get());

        if (argMultimap.getValue(CliSyntax.PREFIX_YEAR).isEmpty()) {
            return new ShowCommand(monthOfYear);
        } else {
            Year year = new YearParser().parse(argMultimap.getValue(CliSyntax.PREFIX_YEAR).get());
            return new ShowCommand(monthOfYear, year);
        }
    }

    private boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix prefix) {
        return argumentMultimap.getValue(prefix).isPresent();
    }

    private boolean hasMultiplePrefixes(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes)
                .anyMatch(prefix -> argumentMultimap.getAllValues(prefix).size() > 1);
    }
}
