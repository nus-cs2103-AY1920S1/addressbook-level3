package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEEK;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

/**
 * Parses arguments and creates a SelectCommand object.
 */
public class SelectCommandParser implements Parser<SelectCommand> {
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    private static boolean areMultiplePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getAllValues(prefix).size() > 1);
    }

    @Override
    public SelectCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_WEEK, PREFIX_DAY, PREFIX_ID);

        if (!arePrefixesPresent(argMultimap, PREFIX_ID)
                || areMultiplePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_WEEK, PREFIX_DAY, PREFIX_ID)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
        }

        Name name;
        int week;
        int day;
        int id;

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        } else {
            name = Name.emptyName();
        }

        if (argMultimap.getValue(PREFIX_WEEK).isPresent()) {
            week = ParserUtil.parseWeek(argMultimap.getValue(PREFIX_WEEK).get());
        } else {
            week = 0;
        }

        if (argMultimap.getValue(PREFIX_DAY).isPresent()) {
            day = ParserUtil.parseDay(argMultimap.getValue(PREFIX_DAY).get());
            int currentDay = LocalDateTime.now().getDayOfWeek().getValue();
            day = ((day + currentDay - 1 - 1) % 7) + 1;

        } else {
            day = LocalDateTime.now().getDayOfWeek().getValue();
        }

        id = ParserUtil.parseId(argMultimap.getValue(PREFIX_ID).get());

        return new SelectCommand(week, name, day, id);
    }
}
