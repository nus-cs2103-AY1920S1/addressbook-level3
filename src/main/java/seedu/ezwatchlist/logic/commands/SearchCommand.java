package seedu.ezwatchlist.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.ezwatchlist.api.exceptions.OnlineConnectionException;
import seedu.ezwatchlist.api.model.ApiInterface;
import seedu.ezwatchlist.api.model.ApiManager;
import seedu.ezwatchlist.commons.core.messages.SearchMessages;
import seedu.ezwatchlist.logic.commands.exceptions.CommandException;
import seedu.ezwatchlist.logic.parser.SearchKey;
import seedu.ezwatchlist.model.Model;
import seedu.ezwatchlist.model.actor.Actor;
import seedu.ezwatchlist.model.show.Genre;
import seedu.ezwatchlist.model.show.Movie;
import seedu.ezwatchlist.model.show.Name;
import seedu.ezwatchlist.model.show.Show;
import seedu.ezwatchlist.model.show.TvShow;
import seedu.ezwatchlist.model.show.Type;

/**
 * Finds and lists all shows in watchlist whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class SearchCommand extends Command {
    public static final String COMMAND_WORD = "search";

    private static final String INPUT_TRUE = "true";
    private static final String INPUT_YES = "yes";
    private static final String INPUT_FALSE = "false";
    private static final String INPUT_NO = "no";

    private ApiInterface onlineSearch;
    private List<String> nameList;
    private List<String> typeList;
    private List<String> actorList;
    private List<String> genreList;
    private List<String> isWatchedList;
    private List<String> fromOnlineList;
    private List<Show> searchResult = new ArrayList<>();

    private boolean isOffline = false;

    public SearchCommand(HashMap<SearchKey, List<String>> searchShowsHashMap) {
        nameList = searchShowsHashMap.get(SearchKey.KEY_NAME);
        typeList = searchShowsHashMap.get(SearchKey.KEY_TYPE);
        actorList = searchShowsHashMap.get(SearchKey.KEY_ACTOR); // unable to search online
        genreList = searchShowsHashMap.get(SearchKey.KEY_GENRE); // unable to search for shows online
        isWatchedList = searchShowsHashMap.get(SearchKey.KEY_IS_WATCHED);
        fromOnlineList = searchShowsHashMap.get(SearchKey.KEY_FROM_ONLINE);
        try {
            onlineSearch = new ApiManager();
        } catch (OnlineConnectionException e) {
            isOffline = true;
        }
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (emptyCompulsoryKeyword()) {
            throw new CommandException("Make sure keyword(s) for n/, a/ or g/ is not empty.\n"
                    + SearchMessages.MESSAGE_USAGE);
        }

        searchByName(model);
        searchByActor(model);
        searchByGenre(model);

        filterOutDuplicatesInSearchResult(model);

        if (isOffline) {
            return new CommandResult(String.format(SearchMessages.MESSAGE_INTERNAL_SHOW_LISTED_OVERVIEW,
                    model.getSearchResultList().size()), false);
        } else {
            return new CommandResult(String.format(SearchMessages.MESSAGE_SHOWS_FOUND_OVERVIEW,
                    model.getSearchResultList().size()), false);
        }
    }

    /**
     * Search for shows by name.
     * @param model Model used.
     * @throws CommandException If command exception occurred.
     */
    private void searchByName(Model model) throws CommandException {
        if (nameList.isEmpty()) {
            return;
        }
        try {
            if (requestedSearchFromInternal()) {
                for (String showName : nameList) {
                    addShowFromWatchListIfSameNameAs(showName, model);
                }
            } else if (requestedSearchFromOnline()) {
                for (String showName : nameList) {
                    addShowFromDatabaseIfSameNameAs(showName, model);
                    addShowFromOnlineIfSameNameAs(showName);
                }
            } else if (!requestedFromOnline()) {
                for (String showName : nameList) {
                    addShowFromWatchListIfSameNameAs(showName, model);
                    addShowFromDatabaseIfSameNameAs(showName, model);
                    addShowFromOnlineIfSameNameAs(showName);
                }
            } else {
                throw new CommandException(SearchMessages.MESSAGE_INVALID_FROM_ONLINE_COMMAND);
            }
        } catch (OnlineConnectionException oce) {
            for (String showName : nameList) {
                addShowFromWatchListIfSameNameAs(showName, model);
                addShowFromDatabaseIfSameNameAs(showName, model);
            }
            isOffline = true;
        }
    }

    /**
     * Search for shows by actor.
     * @param model Model used.
     * @throws CommandException If command exception occurred.
     */
    private void searchByActor(Model model) throws CommandException {
        if (actorList.isEmpty()) {
            return;
        }

        Set<Actor> actorSet = new HashSet<Actor>();
        for (String actorName : actorList) {
            if (actorName.isBlank()) {
                continue;
            }
            Actor actor = new Actor(actorName.trim());
            actorSet.add(actor);
        }

        if (requestedSearchFromInternal()) {
            addShowFromWatchListIfHasActor(actorSet, model);
        } else if (requestedSearchFromOnline()) {
            addShowFromDatabaseIfHasActor(actorSet, model);
        } else if (!requestedFromOnline()) {
            addShowFromWatchListIfHasActor(actorSet, model);
            addShowFromDatabaseIfHasActor(actorSet, model);
        } else {
            throw new CommandException(SearchMessages.MESSAGE_INVALID_FROM_ONLINE_COMMAND);
        }
    }

    /**
     * Search for shows by genre.
     * @param model Model used.
     * @throws CommandException If command exception occurred.
     * @throws OnlineConnectionException when not connected to the internet.
     */
    private void searchByGenre(Model model) throws CommandException {
        if (genreList.isEmpty()) {
            return;
        }

        Set<Genre> genreSet = new HashSet<Genre>();
        for (String genreName : genreList) {
            if (genreName.isBlank()) {
                continue;
            }
            Genre genre = new Genre(genreName.trim());
            genreSet.add(genre);
        }

        try {
            if (requestedSearchFromInternal()) {
                addShowFromWatchListIfIsGenre(genreSet, model);
            } else if (requestedSearchFromOnline()) {
                addShowFromDatabaseIfIsGenre(genreSet, model);
                addMovieFromOnlineIfIsGenre(genreSet);
                //unable to search for online tv; only able to search for tv from database
            } else if (!requestedFromOnline()) {
                addShowFromWatchListIfIsGenre(genreSet, model);
                addShowFromDatabaseIfIsGenre(genreSet, model);
                addMovieFromOnlineIfIsGenre(genreSet);
            } else {
                throw new CommandException(SearchMessages.MESSAGE_INVALID_FROM_ONLINE_COMMAND);
            }
        } catch (OnlineConnectionException oce) {
            addShowFromWatchListIfIsGenre(genreSet, model);
            addShowFromDatabaseIfIsGenre(genreSet, model);
            isOffline = true;
        }
    }

    /**
     * Adds show from list if it has the same name as in {@code showName}.
     * @param showName name of the given show.
     * @param model current model of the program.
     */
    private void addShowFromWatchListIfSameNameAs(String showName, Model model) {
        if (showName.isBlank()) {
            return;
        }
        List<Show> filteredShowList = model.getShowFromWatchlistIfHasName(new Name(showName.trim()));
        addShowToSearchResult(filteredShowList);
    }

    /**
     * Adds show from offline database if it has the same name as in {@code showName}.
     * @param showName name of the given show.
     * @param model current model of the program.
     */
    private void addShowFromDatabaseIfSameNameAs(String showName, Model model) {
        if (showName.isBlank()) {
            return;
        }
        List<Show> filteredShowList = model.getShowFromDatabaseIfHasName(new Name(showName));
        addShowToSearchResult(filteredShowList);
    }

    /**
     * Adds show from list if it has any actor in {@code actorSet}.
     * @param actorSet Set of actors to be searched for.
     * @param model current model of the program.
     */
    private void addShowFromWatchListIfHasActor(Set<Actor> actorSet, Model model) {
        if (actorSet.isEmpty()) {
            return;
        }
        List<Show> filteredShowList = model.getShowFromWatchlistIfHasActor(actorSet);
        addShowToSearchResult(filteredShowList);
    }

    private void addShowFromDatabaseIfHasActor(Set<Actor> actorSet, Model model) {
        if (actorSet.isEmpty()) {
            return;
        }
        List<Show> filteredShowList = model.getShowFromDatabaseIfHasActor(actorSet);
        addShowToSearchResult(filteredShowList);
    }

    /**
     * Adds show from watchlist if it has any genre in {@code genreSet}.
     * @param genreSet set of actors to be searched for.
     * @param model current model of the program.
     */
    private void addShowFromWatchListIfIsGenre(Set<Genre> genreSet, Model model) {
        if (genreSet.isEmpty()) {
            return;
        }
        List<Show> filteredShowList = model.getShowFromWatchlistIfIsGenre(genreSet);
        addShowToSearchResult(filteredShowList);
    }

    /**
     * Adds show from list if it has any genre in {@code genreSet}.
     * @param genreSet set of actors to be searched for.
     * @param model current model of the program.
     */
    private void addShowFromDatabaseIfIsGenre(Set<Genre> genreSet, Model model) {
        if (genreSet.isEmpty()) {
            return;
        }
        List<Show> filteredShowList = model.getShowFromDatabaseIfIsGenre(genreSet);
        addShowToSearchResult(filteredShowList);
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
     * @throws OnlineConnectionException when not connected to the internet.
     * @throws CommandException If command exception occurred.
     */
    private void addShowFromOnlineIfSameNameAs(String showName) throws OnlineConnectionException, CommandException {
        if (!requestedIsWatched() && !showName.isBlank()) {
            if (requestedSearchForMovie()) {
                addOnlineMovieSearchedByNameToResult(showName);
            } else if (requestedSearchForTv()) {
                addOnlineTvSearchedByNameToResult(showName);
            } else if (requestedType()) {
                throw new CommandException(SearchMessages.MESSAGE_INVALID_TYPE_COMMAND);
            } else {
                addOnlineMovieSearchedByNameToResult(showName);
                addOnlineTvSearchedByNameToResult(showName);
            }
        }
    }

    /**
     * Add movies, searched by name from online to the search result list.
     * @param showName Name of the show to be searched.
     * @throws OnlineConnectionException when not connected to the internet.
     */
    private void addOnlineMovieSearchedByNameToResult(String showName) throws OnlineConnectionException {
        if (onlineSearch == null) {
            throw new OnlineConnectionException("User is offline.");
        }
        List<Movie> movies = onlineSearch.getMovieByName(showName.trim());
        for (Movie movie : movies) {
            searchResult.add(movie);
        }
    }

    /**
     * Add tv series, searched by name from online to the search result list.
     * @param showName Name of the show to be searched.
     * @throws OnlineConnectionException when not connected to the internet.
     */
    private void addOnlineTvSearchedByNameToResult(String showName) throws OnlineConnectionException {
        if (onlineSearch == null) {
            throw new OnlineConnectionException("User is offline.");
        }
        List<TvShow> tvShows = onlineSearch.getTvShowByName(showName.trim());
        for (TvShow tvShow : tvShows) {
            searchResult.add(tvShow);
        }
    }

    /**
     * Returns a list of movies from the API search method based on the genre searched.
     * @param genreSet the set of genres that the user wants to search.
     * @throws OnlineConnectionException when not connected to the internet.
     */
    private void addMovieFromOnlineIfIsGenre(Set<Genre> genreSet) throws OnlineConnectionException {
        if (onlineSearch == null) {
            throw new OnlineConnectionException("User is offline.");
        }
        if (genreSet.isEmpty()) {
            return;
        }
        List<Movie> movies = onlineSearch.getMovieByGenre(genreSet);
        for (Movie movie : movies) {
            searchResult.add(movie);
        }
    }

    /**
     * Returns whether there is any compulsory keyword present.
     * One of the following keyword needs to be present: show name, actor or genre.
     * @return True if all compulsory keyword is empty.
     */
    private boolean emptyCompulsoryKeyword() {
        return nameList.isEmpty() && actorList.isEmpty() && genreList.isEmpty();
    }

    /**
     * Returns true if user requests to search for tv series or movies only.
     * @return True if user requests to search for tv series or movies only.
     */
    private boolean requestedType() {
        return !typeList.isEmpty();
    }

    /**
     * Returns true if user requests to search for movies only.
     * @return True if user requests to search for movies only.
     */
    private boolean requestedSearchForMovie() {
        return requestedType() && (typeList.get(0).equals(Type.MOVIE.getType()));
    }

    /**
     * Returns true if user requests to search for tv series only.
     * @return True if user requests to search for tv series only.
     */
    private boolean requestedSearchForTv() {
        return requestedType() && (typeList.get(0).equals(Type.TV_SHOW.getType()));
    }

    /**
     * Returns true if user requests to search from watch or watched list.
     * @return True if user requests to search from watch or watched list.
     */
    private boolean requestedIsWatched() {
        return !isWatchedList.isEmpty();
    }

    /**
     * Returns true if user requests to search from watched list.
     * @return True if user requests to search from watched list.
     */
    private boolean requestedSearchFromWatched() {
        return requestedIsWatched()
                && (isWatchedList.get(0).equals(INPUT_TRUE) || isWatchedList.get(0).equals(INPUT_YES));
    }

    /**
     * Returns true if user requests to search from watch list.
     * @return True if user requests to search from watch list.
     */
    private boolean requestedSearchFromWatchList() {
        return requestedIsWatched()
                && (isWatchedList.get(0).equals(INPUT_FALSE) || isWatchedList.get(0).equals(INPUT_NO));
    }

    /**
     * Returns true if user requests to search from internal or online.
     * @return True if user requests to search from internal or online.
     */
    private boolean requestedFromOnline() throws CommandException {
        for (String input : fromOnlineList) {
            if (!(input.equals(INPUT_FALSE) || input.equals(INPUT_NO) || input.equals(INPUT_TRUE)
                    || input.equals(INPUT_YES))) {
                throw new CommandException(SearchMessages.MESSAGE_INVALID_FROM_ONLINE_COMMAND);
            }
        }
        return !fromOnlineList.isEmpty();
    }

    /**
     * Returns true if user requests to search from internal.
     * @return True if user requests to search from internal.
     */
    private boolean requestedSearchFromInternal() throws CommandException {
        boolean requestedFromOnline = requestedFromOnline();
        for (String input : fromOnlineList) {
            if (requestedFromOnline && (input.equals(INPUT_FALSE) || input.equals(INPUT_NO))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if user requests to search from online.
     * @return True if user requests to search from online.
     */
    private boolean requestedSearchFromOnline() throws CommandException {
        boolean requestedFromOnline = requestedFromOnline();
        for (String input : fromOnlineList) {
            if (requestedFromOnline && (input.equals(INPUT_TRUE) || input.equals(INPUT_YES))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Filters out the duplicates in the search result.
     * @param model current model of the program.
     */
    public void filterOutDuplicatesInSearchResult(Model model) {
        List<Show> result = searchResult.stream().distinct().collect(Collectors.toList());
        model.updateSearchResultList(result);
    }

    /**
     * Returns the list of search results.
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
                && fromOnlineList.equals(((SearchCommand) other).fromOnlineList));
    }
}
