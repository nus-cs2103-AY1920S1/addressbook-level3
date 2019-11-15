package seedu.address.itinerary.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.itinerary.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.itinerary.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.itinerary.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.itinerary.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.itinerary.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.itinerary.logic.parser.CliSyntax.PREFIX_TITLE;

import seedu.address.itinerary.logic.commands.SearchCommand;
import seedu.address.itinerary.ui.TagDropdown;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SearchCommand object
 */
public class SearchCommandParser implements Parser<SearchCommand> {

    @Override
    public SearchCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TITLE, PREFIX_DATE, PREFIX_TIME,
                        PREFIX_LOCATION, PREFIX_DESCRIPTION, PREFIX_TAG);

        SearchCommand.SearchEventDescriptor searchEventDescriptor = new SearchCommand.SearchEventDescriptor();

        if (argMultimap.getValue(PREFIX_TITLE).isPresent()) {
            searchEventDescriptor.setTitle(ParserUtil.parseTitle(argMultimap.getValue(PREFIX_TITLE).get()));
        }

        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            searchEventDescriptor.setDate(ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get()));
        }

        if (argMultimap.getValue(PREFIX_TIME).isPresent()) {
            searchEventDescriptor.setTime(ParserUtil.parseTime(argMultimap.getValue(PREFIX_TIME).get()));
        }

        if (argMultimap.getValue(PREFIX_LOCATION).isPresent()) {
            searchEventDescriptor.setLocation(ParserUtil.parseLocation(argMultimap.getValue(PREFIX_LOCATION).get()));
        }

        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            searchEventDescriptor.setDescription(ParserUtil.parseDescription(argMultimap
                    .getValue(PREFIX_DESCRIPTION).get()));
        }

        if (argMultimap.getValue(PREFIX_TAG).isPresent()) {
            searchEventDescriptor.setTag(ParserUtil.parseTag(TagDropdown.getDropdownText()));
        }

        if (!searchEventDescriptor.isAnyFieldEdited()) {
            throw new ParseException(SearchCommand.MESSAGE_USAGE);
        }

        return new SearchCommand(searchEventDescriptor);
    }
}
