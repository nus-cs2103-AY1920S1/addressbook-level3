package seedu.ezwatchlist.logic.parser;

import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_ACTOR;
import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_GENRE;
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
    private static final String KEY_NAME = "name";
    private static final String KEY_TYPE = "type";
    private static final String KEY_ACTOR = "actor";
    private static final String KEY_GENRE = "genre";
    private static final String KEY_IS_WATCHED = "is_watched";
    private static final String KEY_IS_INTERNAL = "is_internal";


    private HashMap<String, List<String>> searchShowsHashMap = new HashMap<>();

    /**
     * Parses the given {@code String} of arguments in the context of the SearchCommand
     * and returns a SearchCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SearchCommand parse(String args) {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(
                        args, PREFIX_NAME, PREFIX_TYPE, PREFIX_ACTOR, PREFIX_GENRE, PREFIX_IS_WATCHED,
                        PREFIX_IS_INTERNAL);

        List<String> nameList = argMultimap.getAllValues(PREFIX_NAME);
        Optional<String> typeOptional = argMultimap.getValue(PREFIX_TYPE);
        List<String> actorList = argMultimap.getAllValues(PREFIX_ACTOR);
        List<String> genreList = argMultimap.getAllValues(PREFIX_GENRE);
        Optional<String> isWatchedOptional = argMultimap.getValue(PREFIX_IS_WATCHED);
        Optional<String> isInternalOptional = argMultimap.getValue(PREFIX_IS_INTERNAL);

        parseNameToBeSearched(nameList);
        parseTypeToBeSearched(typeOptional);
        parseActorToBeSearched(actorList);
        parseGenreToBeSearched(genreList);
        parseIsWatchedToBeSearched(isWatchedOptional);
        parseIsInternalToBeSearched(isInternalOptional);

        return new SearchCommand(searchShowsHashMap);
    }

    /**
     * Parses the names to be searched.
     * @param nameList List of names to be searched.
     */
    private void parseNameToBeSearched(List<String> nameList) {
        searchShowsHashMap.put(KEY_NAME, nameList);
    }

    /**
     * Parses the type to be searched.
     * @param typeOptional Type to be searched.
     */
    private void parseTypeToBeSearched(Optional<String> typeOptional) {
        ArrayList<String> listOfType = new ArrayList<String>(); // Empty if can be of any type
        if (typeOptional.isPresent()) {
            String type = typeOptional.get().trim().toLowerCase();
            listOfType.add(type);
        } else {
            listOfType.add("movie");
            listOfType.add("tv");
        }
        searchShowsHashMap.put(KEY_TYPE, listOfType);
    }

    /**
     * Parses the actors to be searched.
     * @param actorList List of actors to be searched.
     */
    private void parseActorToBeSearched(List<String> actorList) {
        searchShowsHashMap.put(KEY_ACTOR, actorList);
    }

    /**
     * Parses the genres to be searched.
     * @param genreList List of genres to be searched.
     */
    private void parseGenreToBeSearched(List<String> genreList) {
        searchShowsHashMap.put(KEY_GENRE, genreList);
    }

    /**
     * Parses whether the show is watched.
     * @param isWatchedOptional True/Yes if is watched, else, False/No
     */
    private void parseIsWatchedToBeSearched(Optional<String> isWatchedOptional) {
        ArrayList<String> listOfIsWatched = new ArrayList<String>(); // Empty if can be any
        if (isWatchedOptional.isPresent()) { // true or yes || false or no
            String isWatched = isWatchedOptional.get().trim();
            listOfIsWatched.add(isWatched);
        }
        searchShowsHashMap.put(KEY_IS_WATCHED, listOfIsWatched);
    }

    /**
     * Parses whether the show is internal.
     * @param isInternalOptional True/Yes if is internal, else, False/No
     */
    private void parseIsInternalToBeSearched(Optional<String> isInternalOptional) {
        ArrayList<String> listOfIsInternal = new ArrayList<String>(); // Empty if can be any
        if (isInternalOptional.isPresent()) { // true or yes || false or no
            String isInternal = isInternalOptional.get().trim();
            listOfIsInternal.add(isInternal);
        }
        searchShowsHashMap.put(KEY_IS_INTERNAL, listOfIsInternal);
    }

    /**
     * Return the hash map of the shows to be watched based on the different category.
     * @return Hash map of the shows to be watched based on the different category.
     */
    public HashMap<String, List<String>> getSearchShowsHashMap() {
        return searchShowsHashMap;
    }
}
