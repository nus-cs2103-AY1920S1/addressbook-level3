package seedu.ezwatchlist.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.ezwatchlist.api.exceptions.OnlineConnectionException;
import seedu.ezwatchlist.api.model.ApiMain;
import seedu.ezwatchlist.commons.core.Messages;
import seedu.ezwatchlist.logic.commands.exceptions.CommandException;
import seedu.ezwatchlist.model.Model;
import seedu.ezwatchlist.model.actor.Actor;
import seedu.ezwatchlist.model.show.Movie;
import seedu.ezwatchlist.model.show.Name;
import seedu.ezwatchlist.model.show.Show;
import seedu.ezwatchlist.model.show.TvShow;

/**
 * Finds and lists all shows in watchlist whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class SearchCommand extends Command {
    public static final String COMMAND_WORD = "search";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Searches for shows online whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " Joker";
    private static final String MESSAGE_INVALID_IS_INTERNAL_COMMAND =
            "Invalid input. i/[OPTION] where OPTION is either true/yes or false/no";
    private static final String MESSAGE_INVALID_TYPE_COMMAND =
            "Invalid input. t/[TYPE] where TYPE is either movie or tv";

    private static final String EMPTY_STRING = "";
    private static final String INPUT_TRUE = "true";
    private static final String INPUT_YES = "yes";
    private static final String INPUT_FALSE = "false";
    private static final String INPUT_NO = "no";
    private static final String INPUT_MOVIE = "movie";
    private static final String INPUT_TV = "tv";
    private static final String KEY_NAME = "name";
    private static final String KEY_TYPE = "type";
    private static final String KEY_ACTOR = "actor";
    private static final String KEY_IS_WATCHED = "is_watched";
    private static final String KEY_IS_INTERNAL = "is_internal";

    private List<String> nameList;
    private List<String> typeList;
    private List<String> actorList;
    private List<String> isWatchedList;
    private List<String> isInternalList;

    private List<Show> searchResult = new ArrayList<>();

    public SearchCommand(HashMap<String, List<String>> searchShowsHashMap) {
        nameList = searchShowsHashMap.get(KEY_NAME);
        typeList = searchShowsHashMap.get(KEY_TYPE);
        actorList = searchShowsHashMap.get(KEY_ACTOR); // unable to search online
        isWatchedList = searchShowsHashMap.get(KEY_IS_WATCHED);
        isInternalList = searchShowsHashMap.get(KEY_IS_INTERNAL);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        try {
            if (!nameList.isEmpty() && !actorList.isEmpty()) {
                searchByName(model);
                searchByActor(model);
                // add shows with the show name and also shows with the actors involved
                // ensure no duplicates, currently might have duplicate
                // for now, do not allow users to search for both name and actor at the same time
            } else if (!nameList.isEmpty()) {
                searchByName(model);
            } else if (!actorList.isEmpty()) {
                searchByActor(model);
            } else { // if has no name and actor to be searched
                //searchByType();
                //searchByIsWatched();
                //searchByIsInternal();
            }
            model.updateSearchResultList(searchResult);
            return new CommandResult(String.format(Messages.MESSAGE_SHOWS_LISTED_OVERVIEW,
                    model.getSearchResultList().size()));
        } catch (OnlineConnectionException e) {
            return null;
        }
    }

    /**
     * Search for shows by name.
     * @param model Model used.
     * @throws CommandException If command exception occurred.
     * @throws OnlineConnectionException If online exception occurred.
     */
    private void searchByName(Model model) throws CommandException, OnlineConnectionException {
        if (requestedSearchFromInternal()) {
            for (String showName : nameList) {
                addShowFromWatchListIfSameNameAs(showName, model);
            }
        } else if (requestedSearchFromOnline()) {
            for (String showName : nameList) {
                addShowFromOnlineIfSameNameAs(showName);
            }
        } else if (requestedIsInternal()) {
            throw new CommandException(MESSAGE_INVALID_IS_INTERNAL_COMMAND);
        } else { // there's no restriction on where to search from
            for (String showName : nameList) {
                addShowFromWatchListIfSameNameAs(showName, model);
                addShowFromOnlineIfSameNameAs(showName);
            }
        }
    }

    /**
     * Search for shows by actor.
     * @param model Model used.
     * @throws CommandException If command exception occurred.
     * @throws OnlineConnectionException If online exception occurred.
     */
    private void searchByActor(Model model) throws CommandException, OnlineConnectionException {
        Set<Actor> actorSet = new HashSet<Actor>();
        for (String actorName : actorList) {
            Actor actor = new Actor(actorName);
            actorSet.add(actor);
        }

        if (requestedSearchFromInternal()) {
            addShowFromWatchListIfHasActor(actorSet, model);
        } else if (requestedSearchFromOnline()) {
            addShowFromOnlineIfHasActor(actorSet); //unable to online for now
        } else if (requestedIsInternal()) {
            throw new CommandException(MESSAGE_INVALID_IS_INTERNAL_COMMAND);
        } else { // there's no restriction on where to search from
            addShowFromWatchListIfHasActor(actorSet, model);
            addShowFromOnlineIfHasActor(actorSet); //unable to online for now
        }
    }

    /**
     * Adds show from list if it has the same name as the given show.
     * @param showName name of the given show.
     * @param model current model of the program.
     */
    private void addShowFromWatchListIfSameNameAs(String showName, Model model) {
        if (!showName.equals(EMPTY_STRING)) {
            List<Show> filteredShowList = model.getShowIfHasName(new Name(showName));
            addShowToSearchResult(filteredShowList);
        }
    }

    /**
     * Adds show from list if it has any actor in actorSet.
     * @param actorSet Set of actors to be searched for.
     * @param model Model used.
     */
    private void addShowFromWatchListIfHasActor(Set<Actor> actorSet, Model model) {
        if (!actorSet.isEmpty()) {
            List<Show> filteredShowList = model.getShowIfHasActor(actorSet);
            addShowToSearchResult(filteredShowList);
        }
    }

    /**
     * Add show to search result.
     * @param showList List of shows to be added.
     */
    private void addShowToSearchResult(List<Show> showList) {
        for (Show show : showList) {
            if (requestedSearchFromWatched() && !show.isWatched().getIsWatchedBoolean()) {
                continue; // skip if request to be watched but show is not watched
            } else if (requestedSearchFromWatchList() && show.isWatched().getIsWatchedBoolean()) {
                continue; // skip if requested to be in watchlist but show is watched
            } else if (requestedSearchForMovie() && !show.getType().equals("Movie")) {
                continue; // skip if requested search for movie but show is tv
            } else if (requestedSearchForTv() && !show.getType().equals("Tv Show")) {
                continue; // skip if requested search for tv but show is movie
            }
            searchResult.add(show);
        }
    }

    /**
     * Add shows, both movies and tv series, searched by name from online to the search result list.
     * @param showName Name of the show to be searched.
     * @throws OnlineConnectionException If online exception occurred.
     * @throws CommandException If command exception occurred.
     */
    private void addShowFromOnlineIfSameNameAs(String showName) throws OnlineConnectionException, CommandException {
        if (!requestedIsWatched() && !showName.equals(EMPTY_STRING)) {
            if (requestedSearchForMovie()) {
                addOnlineMovieSearchedByNameToResult(showName);
            } else if (requestedSearchForTv()) {
                addOnlineTvSearchedByNameToResult(showName);
            } else if (requestedType()) {
                throw new CommandException(MESSAGE_INVALID_TYPE_COMMAND);
            } else {
                addOnlineMovieSearchedByNameToResult(showName);
                addOnlineTvSearchedByNameToResult(showName);
            }
        }
    }

    /**
     * Add movies, searched by name from online to the search result list.
     * @param showName Name of the show to be searched.
     * @throws OnlineConnectionException If online exception occurred.
     */
    private void addOnlineMovieSearchedByNameToResult(String showName) throws OnlineConnectionException {
        List<Movie> movies = new ApiMain().getMovieByName(showName);
        for (Movie movie : movies) {
            searchResult.add(movie);
        }
    }

    /**
     * Add tv series, searched by name from online to the search result list.
     * @param showName Name of the show to be searched.
     * @throws OnlineConnectionException If online exception occurred.
     */
    private void addOnlineTvSearchedByNameToResult(String showName) throws OnlineConnectionException {
        List<TvShow> tvShows = new ApiMain().getTvShowByName(showName);
        for (TvShow tvShow : tvShows) {
            searchResult.add(tvShow);
        }
    }

    // TO EDIT
    /**
     * Add show from online if has actors, taking into account if user makes any other requests.
     * @param actorSet The set of actors to be searched.
     * @throws OnlineConnectionException If online exception occurred.
     * @throws CommandException If command exception occurred.
     */
    private void addShowFromOnlineIfHasActor(Set<Actor> actorSet) throws OnlineConnectionException, CommandException {
        if (!actorSet.isEmpty() && !requestedIsWatched()) { // would not check online if requested to be is watched
            if (requestedSearchForMovie()) {
                //addOnlineMovieSearchedByNameToResult(showName);
            } else if (requestedSearchForTv()) {
                //addOnlineTvSearchedByNameToResult(showName);
            } else if (requestedType()) {
                throw new CommandException(MESSAGE_INVALID_TYPE_COMMAND);
            } else {
                //addOnlineMovieSearchedByNameToResult(showName);
                //addOnlineTvSearchedByNameToResult(showName);
            }
        }
    }

    /**
     * Return true if user requests to search for tv series or movies only.
     * @return True if user requests to search for tv series or movies only.
     */
    private boolean requestedType() {
        return typeList.size() != 2;
    }

    /**
     * Return true if user requests to search for movies only.
     * @return True if user requests to search for movies only.
     */
    private boolean requestedSearchForMovie() {
        return requestedType() && (typeList.get(0).equals(INPUT_MOVIE));
    }

    /**
     * Return true if user requests to search for tv series only.
     * @return True if user requests to search for tv series only.
     */
    private boolean requestedSearchForTv() {
        return requestedType() && (typeList.get(0).equals(INPUT_TV));
    }

    /**
     * Return true if user requests to search from watch or watched list.
     * @return True if user requests to search from watch or watched list.
     */
    private boolean requestedIsWatched() {
        return !isWatchedList.isEmpty();
    }

    /**
     * Return true if user requests to search from watched list.
     * @return True if user requests to search from watched list.
     */
    private boolean requestedSearchFromWatched() {
        return requestedIsWatched()
                && (isWatchedList.get(0).equals(INPUT_TRUE) || isWatchedList.get(0).equals(INPUT_YES));
    }

    /**
     * Return true if user requests to search from watch list.
     * @return True if user requests to search from watch list.
     */
    private boolean requestedSearchFromWatchList() {
        return requestedIsWatched()
                && (isWatchedList.get(0).equals(INPUT_FALSE) || isWatchedList.get(0).equals(INPUT_NO));
    }

    /**
     * Return true if user requests to search from internal or online.
     * @return True if user requests to search from internal or online.
     */
    private boolean requestedIsInternal() {
        return !isInternalList.isEmpty();
    }

    /**
     * Return true if user requests to search from internal.
     * @return True if user requests to search from internal.
     */
    private boolean requestedSearchFromInternal() {
        return requestedIsInternal()
                && (isInternalList.get(0).equals(INPUT_TRUE) || isInternalList.get(0).equals(INPUT_YES));
    }

    /**
     * Return true if user requests to search from online.
     * @return True if user requests to search from online.
     */
    private boolean requestedSearchFromOnline() {
        return requestedIsInternal()
                && (isInternalList.get(0).equals(INPUT_FALSE) || isInternalList.get(0).equals(INPUT_NO));
    }

    /**
     * Return the list of search results.
     * @return List of search results.
     */
    public List<Show> getSearchResult() {
        return searchResult;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SearchCommand // instanceof handles nulls
                && nameList.equals(((SearchCommand) other).nameList)
                && typeList.equals(((SearchCommand) other).typeList)
                && actorList.equals(((SearchCommand) other).actorList)
                && isWatchedList.equals(((SearchCommand) other).isWatchedList)
                && isInternalList.equals(((SearchCommand) other).isInternalList));
    }
}
