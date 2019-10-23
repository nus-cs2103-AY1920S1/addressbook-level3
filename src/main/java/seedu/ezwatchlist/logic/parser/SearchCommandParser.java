package seedu.ezwatchlist.logic.parser;

import java.util.*;

import seedu.ezwatchlist.logic.commands.SearchCommand;
import seedu.ezwatchlist.logic.parser.exceptions.ParseException;
import seedu.ezwatchlist.commons.core.Messages;

import seedu.ezwatchlist.model.show.*;
import seedu.ezwatchlist.model.actor.Actor;

import static seedu.ezwatchlist.logic.parser.CliSyntax.*;

/**
 * Parses input arguments and creates a new SearchCommand object
 */
public class SearchCommandParser implements Parser<SearchCommand> {
    HashMap<String, List<String>> searchShowsHashMap = new HashMap<>();

    /**
     * Parses the given {@code String} of arguments in the context of the SearchCommand
     * and returns a SearchCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SearchCommand parse(String args) {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_TYPE, PREFIX_IS_WATCHED, PREFIX_ACTOR, PREFIX_IS_INTERNAL);

        List<String> name_optional = argMultimap.getAllValues(PREFIX_NAME); // allow multiple values // allow 1 for now
        Optional<String> type_optional = argMultimap.getValue(PREFIX_TYPE); // at most one value
        List<String> actor_optional = argMultimap.getAllValues(PREFIX_ACTOR); // allow multiple values
        Optional<String> is_watched_optional = argMultimap.getValue(PREFIX_IS_WATCHED); // true or false
        Optional<String> is_internal_optional = argMultimap.getValue(PREFIX_IS_INTERNAL); // true or false

        parseNameToBeSearched(name_optional);
        parseTypeToBeSearched(type_optional);
        parseActorToBeSearched(actor_optional);
        parseIsWatchedToBeSearched(is_watched_optional);
        parseIsInternalToBeSearched(is_internal_optional);

        return new SearchCommand(searchShowsHashMap);
    }

    private void parseNameToBeSearched(List<String> name_optional) {
        searchShowsHashMap.put("name", name_optional);
    }

    private void parseTypeToBeSearched(Optional<String> type_optional) {
        ArrayList<String> listOfType = new ArrayList<String>();
        if (type_optional.isPresent()) {
            String type = type_optional.get().trim();
            listOfType.add(type);
        } else {
            listOfType.add("movie");
            listOfType.add("tv");
        }
        searchShowsHashMap.put("type", listOfType);
    }

    private void parseActorToBeSearched(List<String> actor_optional) {
        searchShowsHashMap.put("actor", actor_optional);
    }

    private void parseIsWatchedToBeSearched(Optional<String> is_watched_optional) {
        ArrayList<String> listOfIsWatched = new ArrayList<String>();
        if (is_watched_optional.isPresent()) {
            String is_watched = is_watched_optional.get().trim();
            listOfIsWatched.add(is_watched);
        } else {
            listOfIsWatched.add("false");
            listOfIsWatched.add("true");
        }
        searchShowsHashMap.put("is_watched", listOfIsWatched);
    }

    private void parseIsInternalToBeSearched(Optional<String> is_internal_optional) {
        ArrayList<String> listOfIsInternal = new ArrayList<String>();
        if (is_internal_optional.isPresent()) {
            String is_internal = is_internal_optional.get().trim(); // true or yes || false or no
            listOfIsInternal.add(is_internal);
        }
        searchShowsHashMap.put("is_internal", listOfIsInternal);
    }

}
