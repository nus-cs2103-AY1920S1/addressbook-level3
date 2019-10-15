package seedu.deliverymans.logic.parser.restaurant;

import java.util.Set;
import java.util.stream.Stream;

import static seedu.deliverymans.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.deliverymans.logic.commands.restaurant.AddCommand;
import seedu.deliverymans.logic.parser.ArgumentMultimap;
import seedu.deliverymans.logic.parser.ArgumentTokenizer;
import seedu.deliverymans.logic.parser.Parser;
import seedu.deliverymans.logic.parser.ParserUtil;
import seedu.deliverymans.logic.parser.Prefix;
import seedu.deliverymans.logic.parser.exceptions.ParseException;
import seedu.deliverymans.model.Name;
import seedu.deliverymans.model.location.Location;
import seedu.deliverymans.model.restaurant.Restaurant;
import seedu.deliverymans.model.Tag;

public class AddCommandParser implements Parser<AddCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_LOCATION, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_LOCATION)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Location location = ParserUtil.parseLocation(argMultimap.getValue(PREFIX_LOCATION).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Restaurant restaurant = new Restaurant(name, location, tagList);

        return new AddCommand(restaurant);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
