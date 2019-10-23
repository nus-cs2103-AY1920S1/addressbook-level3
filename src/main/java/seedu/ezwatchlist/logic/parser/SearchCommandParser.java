package seedu.ezwatchlist.logic.parser;

import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_ACTOR;
import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_IS_WATCHED;
import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_TYPE;

import java.util.Optional;

import seedu.ezwatchlist.logic.commands.SearchCommand;
import seedu.ezwatchlist.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SearchCommand object
 */
public class SearchCommandParser implements Parser<SearchCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SearchCommand
     * and returns a SearchCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SearchCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_TYPE, PREFIX_IS_WATCHED, PREFIX_ACTOR);

        Optional<String> name = argMultimap.getValue(PREFIX_NAME);
        /*Optional<String> type = argMultimap.getValue(PREFIX_TYPE);
        Optional<String> isWatched = argMultimap.getValue(PREFIX_IS_WATCHED);
        List<String> actorList = argMultimap.getAllValues(PREFIX_ACTOR);*/

        return new SearchCommand(name/*, type, isWatched, actorList*/);
        /*if (anyPrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_TYPE, PREFIX_DATE_OF_RELEASE, PREFIX_IS_WATCHED)) {
            return new SearchCommand(name, type, isWatched, actorList);
        } else {
            return new SearchCommand();
        }*/
    }

    /**
     * Returns true if all of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    /*private static boolean anyPrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }*/
}
