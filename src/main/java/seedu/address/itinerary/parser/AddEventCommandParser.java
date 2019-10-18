package seedu.address.itinerary.parser;

import seedu.address.itinerary.commands.AddEventCommand;
import seedu.address.itinerary.model.Event.*;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;

import java.util.stream.Stream;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.itinerary.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.itinerary.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.itinerary.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.itinerary.parser.CliSyntax.PREFIX_TITLE;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddEventCommandParser implements Parser<AddEventCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddEventCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TITLE, PREFIX_TIME, PREFIX_LOCATION, PREFIX_DESCRIPTION);

        if (!arePrefixesPresent(argMultimap, PREFIX_TITLE, PREFIX_TIME, PREFIX_LOCATION, PREFIX_DESCRIPTION)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));
        }

        Title title = ParserUtil.parseTitle(argMultimap.getValue(PREFIX_TITLE).get());
        Description description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
        Time time = ParserUtil.parseTime(argMultimap.getValue(PREFIX_TIME).get());
        Location location = ParserUtil.parseLocation(argMultimap.getValue(PREFIX_LOCATION).get());

        Event event = new Event(title, location, description, time);

        return new AddEventCommand(event);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}