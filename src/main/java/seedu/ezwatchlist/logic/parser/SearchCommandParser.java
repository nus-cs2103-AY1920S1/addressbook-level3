package seedu.ezwatchlist.logic.parser;

import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_ACTOR;
import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_FROM_ONLINE;
import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_GENRE;
import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_IS_WATCHED;
import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_TYPE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import seedu.ezwatchlist.commons.core.messages.SearchMessages;
import seedu.ezwatchlist.logic.commands.SearchCommand;
import seedu.ezwatchlist.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SearchCommand object
 */
public class SearchCommandParser implements Parser<SearchCommand> {
    private HashMap<SearchKey, List<String>> searchShowsHashMap = new HashMap<>();
    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses the given {@code String} of arguments in the context of the SearchCommand
     * and returns a SearchCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    public SearchCommand parse(String args, String currentPanel) throws ParseException {
        // checkSyntaxError(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args, PREFIX_NAME, PREFIX_TYPE, PREFIX_ACTOR, PREFIX_GENRE, PREFIX_IS_WATCHED, PREFIX_FROM_ONLINE);

        List<String> nameList = argMultimap.getAllValues(PREFIX_NAME);
        Optional<String> typeOptional = argMultimap.getValue(PREFIX_TYPE);
        List<String> actorList = argMultimap.getAllValues(PREFIX_ACTOR);
        List<String> genreList = argMultimap.getAllValues(PREFIX_GENRE);
        Optional<String> isWatchedOptional = argMultimap.getValue(PREFIX_IS_WATCHED);
        Optional<String> fromOnlineOptional = argMultimap.getValue(PREFIX_FROM_ONLINE);

        parseNameToBeSearched(nameList);
        parseTypeToBeSearched(typeOptional);
        parseActorToBeSearched(actorList);
        parseGenreToBeSearched(genreList);
        parseIsWatchedToBeSearched(isWatchedOptional);
        parseIsInternalToBeSearched(fromOnlineOptional);

        return new SearchCommand(searchShowsHashMap);
    }

    /**
     * Checks if the user input the command correctly with the correct syntax.
     * @param args User input to be checked for the correct syntax
     * @throws ParseException if the user input does not conform the expected format
     */
    private void checkSyntaxError(String args) throws ParseException {
        String[] keywordsArray = args.split(" ");
        int length = keywordsArray.length;
        for (int i = 1; i < length; i++) {
            String s = keywordsArray[i].trim();
            if (s.length() > 2 && (!s.substring(0, 2).equals("n/") && !s.substring(0, 2).equals("a/")
                    && !s.substring(0, 2).equals("g/") && !s.substring(0, 2).equals("t/")
                    && !s.substring(0, 2).equals("w/") && !s.substring(0, 2).equals("o/"))) {
                throw new ParseException("Invalid syntax.\n" + SearchMessages.MESSAGE_USAGE);
            }
        }
    }

    /**
     * Parses the names to be searched.
     * @param nameList List of names to be searched.
     */
    private void parseNameToBeSearched(List<String> nameList) {
        searchShowsHashMap.put(SearchKey.KEY_NAME, nameList);
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
        }
        searchShowsHashMap.put(SearchKey.KEY_TYPE, listOfType);
    }

    /**
     * Parses the actors to be searched.
     * @param actorList List of actors to be searched.
     */
    private void parseActorToBeSearched(List<String> actorList) {
        searchShowsHashMap.put(SearchKey.KEY_ACTOR, actorList);
    }

    /**
     * Parses the genres to be searched.
     * @param genreList List of genres to be searched.
     */
    private void parseGenreToBeSearched(List<String> genreList) {
        searchShowsHashMap.put(SearchKey.KEY_GENRE, genreList);
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
        searchShowsHashMap.put(SearchKey.KEY_IS_WATCHED, listOfIsWatched);
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
        searchShowsHashMap.put(SearchKey.KEY_FROM_ONLINE, listOfIsInternal);
    }

    /**
     * Return the hash map of the shows to be watched based on the different category.
     * @return Hash map of the shows to be watched based on the different category.
     */
    public HashMap<SearchKey, List<String>> getSearchShowsHashMap() {
        return searchShowsHashMap;
    }
}
