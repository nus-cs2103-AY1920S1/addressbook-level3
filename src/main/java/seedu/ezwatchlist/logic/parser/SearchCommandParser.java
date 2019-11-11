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
import seedu.ezwatchlist.logic.parser.exceptions.ParseException;
import seedu.ezwatchlist.model.show.Type;

/**
 * Parses input arguments and creates a new SearchCommand object.
 */
public class SearchCommandParser implements Parser<SearchCommand> {
    private static final String INPUT_TRUE = "true";
    private static final String INPUT_YES = "yes";
    private static final String INPUT_FALSE = "false";
    private static final String INPUT_NO = "no";

    private HashMap<SearchKey, List<String>> searchShowsHashMap = new HashMap<>();

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

        if (emptyCompulsoryKeyword(nameList, actorList, genreList)) {
            throw new ParseException("Make sure keyword(s) for n/, a/ or g/ is not empty.\n"
                    + SearchMessages.MESSAGE_USAGE);
        }

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
     * Returns whether there is any compulsory keyword present.
     * One of the following keyword needs to be present: show name, actor or genre.
     * @return True if all compulsory keyword is empty.
     */
    private boolean emptyCompulsoryKeyword(List<String> nameList, List<String> actorList, List<String> genreList) {
        if (nameList.isEmpty() && actorList.isEmpty() && genreList.isEmpty()) {
            return true;
        }
        for (String name : nameList) {
            if (name.isBlank()) {
                return true;
            }
        }
        for (String actor : actorList) {
            if (actor.isBlank()) {
                return true;
            }
        }
        for (String genre : genreList) {
            if (genre.isBlank()) {
                return true;
            }
        }
        return false;
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
    private void parseTypeToBeSearched(Optional<String> typeOptional) throws ParseException {
        ArrayList<String> listOfType = new ArrayList<String>();
        if (typeOptional.isPresent()) {
            String type = typeOptional.get().toLowerCase();
            if (type.isBlank()) {
                throw new ParseException("Make sure keyword for t/ is not empty."
                        + SearchMessages.MESSAGE_INVALID_TYPE_COMMAND);
            }
            String typeTrimmed = type.trim();
            if (!(typeTrimmed.equals(Type.MOVIE.getType()) || typeTrimmed.equals(Type.TV_SHOW.getType()))) {
                throw new ParseException(SearchMessages.MESSAGE_INVALID_TYPE_COMMAND);
            }
            listOfType.add(typeTrimmed);
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
            String isWatched = isWatchedOptional.get().toLowerCase();
            if (isWatched.isBlank()) {
                throw new ParseException("Make sure keyword for w/ is not empty."
                        + SearchMessages.MESSAGE_INVALID_IS_WATCHED_COMMAND);
            }
            String isWatchedTrimmed = isWatched.trim();
            if (!(isWatchedTrimmed.equals(INPUT_FALSE) || isWatchedTrimmed.equals(INPUT_NO)
                    || isWatchedTrimmed.equals(INPUT_TRUE) || isWatchedTrimmed.equals(INPUT_YES))) {
                throw new ParseException(SearchMessages.MESSAGE_INVALID_IS_WATCHED_COMMAND);
            }
            listOfIsWatched.add(isWatchedTrimmed);
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
            String fromOnline = fromOnlineOptional.get().toLowerCase();
            if (fromOnline.isBlank()) {
                throw new ParseException("Make sure keyword for o/ is not empty."
                        + SearchMessages.MESSAGE_INVALID_FROM_ONLINE_COMMAND);
            }
            String fromOnlineTrimmed = fromOnline.trim();
            if (!(fromOnlineTrimmed.equals(INPUT_FALSE) || fromOnlineTrimmed.equals(INPUT_NO)
                    || fromOnlineTrimmed.equals(INPUT_TRUE) || fromOnlineTrimmed.equals(INPUT_YES))) {
                throw new ParseException(SearchMessages.MESSAGE_INVALID_FROM_ONLINE_COMMAND);
            }
            listOfFromOnline.add(fromOnlineTrimmed);
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
