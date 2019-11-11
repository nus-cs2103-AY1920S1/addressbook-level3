package seedu.address.calendar.logic.parser;

import seedu.address.calendar.logic.commands.ShowCommand;
import seedu.address.calendar.model.date.MonthOfYear;
import seedu.address.calendar.model.date.Year;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.exceptions.ParseException;

import java.util.Optional;

public class ShowCommandParser {
    private static final String MESSAGE_INVALID_COMMAND_FORMAT = "Incorrect month/year format. %s";

    ShowCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_MONTH, CliSyntax.PREFIX_YEAR);

        if (!ParserUtil.arePrefixesPresent(argMultimap, CliSyntax.PREFIX_MONTH)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowCommand.MESSAGE_USAGE));
        } else if (ParserUtil.hasMultiplePrefixes(argMultimap, CliSyntax.PREFIX_MONTH, CliSyntax.PREFIX_YEAR)) {
            throw new ParseException(ParserUtil.MESSAGE_ARG_DUPLICATED);
        }

        Optional<MonthOfYear> monthOfYear = new MonthParser().parse(argMultimap.getValue(CliSyntax.PREFIX_MONTH));

        assert monthOfYear.isEmpty() : "Month of year cannot be empty for a show command";
        MonthOfYear monthOfYearVal = monthOfYear.get();

        Optional<Year> year = new YearParser().parse(argMultimap.getValue(CliSyntax.PREFIX_YEAR));

        return new ShowCommand(monthOfYearVal, year);
    }
}
