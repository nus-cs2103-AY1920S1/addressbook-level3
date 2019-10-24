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
    private static final String KEY_NAME = "name";
    private static final String KEY_TYPE = "type";
    private static final String KEY_ACTOR = "actor";
    private static final String KEY_IS_WATCHED = "is_watched";
    private static final String KEY_IS_INTERNAL = "is_internal";

    /**
     * Parses the given {@code String} of arguments in the context of the SearchCommand
     * and returns a SearchCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SearchCommand parse(String args) {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_TYPE, PREFIX_IS_WATCHED, PREFIX_ACTOR, PREFIX_IS_INTERNAL);

        List<String> nameList = argMultimap.getAllValues(PREFIX_NAME); // allow multiple values // allow 1 for now
        Optional<String> typeOptional = argMultimap.getValue(PREFIX_TYPE); // at most one value
        List<String> actorList = argMultimap.getAllValues(PREFIX_ACTOR); // allow multiple values
        Optional<String> isWatchedOptional = argMultimap.getValue(PREFIX_IS_WATCHED); // true or false
        Optional<String> isInternalOptional = argMultimap.getValue(PREFIX_IS_INTERNAL); // true or false

        parseNameToBeSearched(nameList);
        parseTypeToBeSearched(typeOptional);
        parseActorToBeSearched(actorList);
        parseIsWatchedToBeSearched(isWatchedOptional);
        parseIsInternalToBeSearched(isInternalOptional);

        return new SearchCommand(searchShowsHashMap);
    }

    private void parseNameToBeSearched(List<String> nameList) {
        searchShowsHashMap.put(KEY_NAME, nameList);
    }

    private void parseTypeToBeSearched(Optional<String> typeOptional) {
        ArrayList<String> listOfType = new ArrayList<String>(); // Empty if can be of any type
        if (typeOptional.isPresent()) {
            String type = typeOptional.get().trim().toLowerCase();
            listOfType.add(type);
        }
        searchShowsHashMap.put(KEY_TYPE, listOfType);
    }

    private void parseActorToBeSearched(List<String> actorList) {
        searchShowsHashMap.put(KEY_ACTOR, actorList);
    }

    private void parseIsWatchedToBeSearched(Optional<String> isWatchedOptional) {
        ArrayList<String> listOfIsWatched = new ArrayList<String>(); // Empty if can be any
        if (isWatchedOptional.isPresent()) { // true or yes || false or no
            String is_watched = isWatchedOptional.get().trim();
            listOfIsWatched.add(is_watched);
        }
        searchShowsHashMap.put(KEY_IS_WATCHED, listOfIsWatched);
    }

    private void parseIsInternalToBeSearched(Optional<String> isInternalOptional) {
        ArrayList<String> listOfIsInternal = new ArrayList<String>(); // Empty if can be any
        if (isInternalOptional.isPresent()) { // true or yes || false or no
            String is_internal = isInternalOptional.get().trim();
            listOfIsInternal.add(is_internal);
        }
        searchShowsHashMap.put(KEY_IS_INTERNAL, listOfIsInternal);
    }

}
