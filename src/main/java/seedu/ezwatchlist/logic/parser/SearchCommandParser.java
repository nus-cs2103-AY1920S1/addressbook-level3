package seedu.ezwatchlist.logic.parser;

import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_ACTOR;
import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_IS_INTERNAL;
import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_IS_WATCHED;
import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_TYPE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import seedu.ezwatchlist.logic.commands.SearchCommand;
import seedu.ezwatchlist.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SearchCommand object
 */
public class SearchCommandParser implements Parser<SearchCommand> {
    private HashMap<String, List<String>> searchShowsHashMap = new HashMap<>();

    /**
     * Parses the given {@code String} of arguments in the context of the SearchCommand
     * and returns a SearchCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SearchCommand parse(String args) {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(
                        args, PREFIX_NAME, PREFIX_TYPE, PREFIX_IS_WATCHED, PREFIX_ACTOR, PREFIX_IS_INTERNAL);

        List<String> nameOptional = argMultimap.getAllValues(PREFIX_NAME); // allow multiple values // allow 1 for now
        Optional<String> typeOptional = argMultimap.getValue(PREFIX_TYPE); // at most one value
        List<String> actorOptional = argMultimap.getAllValues(PREFIX_ACTOR); // allow multiple values
        Optional<String> isWatchedOptional = argMultimap.getValue(PREFIX_IS_WATCHED); // true or false
        Optional<String> isInternalOptional = argMultimap.getValue(PREFIX_IS_INTERNAL); // true or false

        parseNameToBeSearched(nameOptional);
        parseTypeToBeSearched(typeOptional);
        parseActorToBeSearched(actorOptional);
        parseIsWatchedToBeSearched(isWatchedOptional);
        parseIsInternalToBeSearched(isInternalOptional);

        return new SearchCommand(searchShowsHashMap);
    }

    private void parseNameToBeSearched(List<String> nameOptional) {
        searchShowsHashMap.put("name", nameOptional);
    }

    /**
     * Parses the type of show to be watched.
     * @param typeOptional type of the show to be watched.
     */
    private void parseTypeToBeSearched(Optional<String> typeOptional) {
        ArrayList<String> listOfType = new ArrayList<String>();
        if (typeOptional.isPresent()) {
            String type = typeOptional.get().trim();
            listOfType.add(type);
        } else {
            listOfType.add("movie");
            listOfType.add("tv");
        }
        searchShowsHashMap.put("type", listOfType);
    }

    /**
     * Parses the actor to be searched.
     * @param actorOptional Optional actor field to be searched.
     */
    private void parseActorToBeSearched(List<String> actorOptional) {
        searchShowsHashMap.put("actor", actorOptional);
    }

    /**
     * Parses the watched status of the show.
     * @param isWatchedOptional the watched status of the show to be searched.
     */
    private void parseIsWatchedToBeSearched(Optional<String> isWatchedOptional) {
        ArrayList<String> listOfIsWatched = new ArrayList<String>();
        if (isWatchedOptional.isPresent()) {
            String isWatched = isWatchedOptional.get().trim();
            listOfIsWatched.add(isWatched);
        } else {
            listOfIsWatched.add("false");
            listOfIsWatched.add("true");
        }
        searchShowsHashMap.put("is_watched", listOfIsWatched);
    }

    /**
     * Parses the database to access the search function form.
     * @param isInternalOptional the user defined whether the search is internal or external.
     */
    private void parseIsInternalToBeSearched(Optional<String> isInternalOptional) {
        ArrayList<String> listOfIsInternal = new ArrayList<String>();
        if (isInternalOptional.isPresent()) {
            String isInternal = isInternalOptional.get().trim(); // true or yes || false or no
            listOfIsInternal.add(isInternal);
        }
        searchShowsHashMap.put("is_internal", listOfIsInternal);
    }
}
