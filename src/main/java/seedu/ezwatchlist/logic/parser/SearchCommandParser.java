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

import seedu.ezwatchlist.commons.core.messages.Messages;
import seedu.ezwatchlist.commons.core.messages.SearchMessages;
import seedu.ezwatchlist.logic.commands.SearchCommand;
import seedu.ezwatchlist.logic.commands.exceptions.CommandException;
import seedu.ezwatchlist.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SearchCommand object.
 */
public class SearchCommandParser implements Parser<SearchCommand> {
    private HashMap<SearchKey, List<String>> searchShowsHashMap = new HashMap<>();
    private static final String INPUT_TRUE = "true";
    private static final String INPUT_YES = "yes";
    private static final String INPUT_FALSE = "false";
    private static final String INPUT_NO = "no";

    /**
     * Parses the given {@code String} of arguments in the context of the SearchCommand.
     * and returns a SearchCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public SearchCommand parse(String args, String currentPanel) throws ParseException {
        checkNoOtherPrefixPresent(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args, PREFIX_NAME, PREFIX_TYPE, PREFIX_ACTOR, PREFIX_GENRE, PREFIX_IS_WATCHED, PREFIX_FROM_ONLINE);

        checkPrefixPresent(argMultimap);

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
        parseFromOnlineToBeSearched(fromOnlineOptional);

        return new SearchCommand(searchShowsHashMap);
    }

    /**
     * Returns true if any of the prefixes for name, genre or actor is present
     * in the given {@code ArgumentMultimap}.
     * @throws ParseException if the user input does not conform the expected format.
     */
    private void checkPrefixPresent(ArgumentMultimap argMultimap) throws ParseException {
        if (!anyPrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_GENRE, PREFIX_ACTOR)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    SearchMessages.MESSAGE_USAGE));
        }
    }

    /**
     * Returns true if any of the prefixes does not contain empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean anyPrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Checks if the user input the command correctly with the correct syntax.
     * @param args User input to be checked for the correct syntax.
     * @throws ParseException if the user input does not conform the expected format.
     */
    private void checkNoOtherPrefixPresent(String args) throws ParseException {
        String[] keywordsArray = args.split("/");
        int arrayLength = keywordsArray.length;
        for (int i = 0; i < arrayLength - 1; i++) {
            String s = keywordsArray[i];
            String[] sArray = s.split(" ");
            int sLength = sArray.length;
            String prefix = sArray[sLength - 1];
            if (!prefix.equals("n") && !prefix.equals("a") && !prefix.equals("g") && !prefix.equals("t")
                    && !prefix.equals("o") && !prefix.equals("w")) {
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
        ArrayList<String> listOfType = new ArrayList<String>();
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
    private void parseIsWatchedToBeSearched(Optional<String> isWatchedOptional) throws ParseException {
        ArrayList<String> listOfIsWatched = new ArrayList<String>();
        if (isWatchedOptional.isPresent()) {
            String isWatched = isWatchedOptional.get().trim();
            if (!(isWatched.equals(INPUT_FALSE) || isWatched.equals(INPUT_NO) || isWatched.equals(INPUT_TRUE)
                    || isWatched.equals(INPUT_YES))) {
                throw new ParseException(SearchMessages.MESSAGE_INVALID_FROM_ONLINE_COMMAND);
            }
            listOfIsWatched.add(isWatched);
        }
        searchShowsHashMap.put(SearchKey.KEY_IS_WATCHED, listOfIsWatched);
    }

    /**
     * Parses whether the show searched should be from online.
     * @param fromOnlineOptional True/Yes if is from online, else, False/No.
     */
    private void parseFromOnlineToBeSearched(Optional<String> fromOnlineOptional) throws ParseException {
        ArrayList<String> listOfFromOnline = new ArrayList<String>();
        if (fromOnlineOptional.isPresent()) {
            String fromOnline = fromOnlineOptional.get().trim();
            if (!(fromOnline.equals(INPUT_FALSE) || fromOnline.equals(INPUT_NO) || fromOnline.equals(INPUT_TRUE)
                    || fromOnline.equals(INPUT_YES))) {
                throw new ParseException(SearchMessages.MESSAGE_INVALID_FROM_ONLINE_COMMAND);
            }
            listOfFromOnline.add(fromOnline);
        }
        searchShowsHashMap.put(SearchKey.KEY_FROM_ONLINE, listOfFromOnline);
    }

    /**
     * Return the hash map of the shows to be watched based on the different category.
     * @return Hash map of the shows to be watched based on the different category.
     */
    public HashMap<SearchKey, List<String>> getSearchShowsHashMap() {
        return searchShowsHashMap;
    }
}
