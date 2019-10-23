package seedu.ezwatchlist.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.ezwatchlist.api.ApiMain;
import seedu.ezwatchlist.api.exceptions.OnlineConnectionException;
import seedu.ezwatchlist.commons.core.Messages;
import seedu.ezwatchlist.logic.commands.exceptions.CommandException;
import seedu.ezwatchlist.model.Model;
import seedu.ezwatchlist.model.show.*;
import seedu.ezwatchlist.model.show.Movie;
import seedu.ezwatchlist.model.show.Show;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    private final String EMPTY_STRING = "";
    private List<String> nameList;
    private List<String> typeList;
    private List<String> actorList;
    private List<String> isWatchedList;
    private List<String> isInternalList;

    List<Show> searchResult = new ArrayList<>();
    public static final String MESSAGE_INVALID_IS_INTERNAL_COMMAND =
            "Invalid input. i/[Option] where option is either true, yes or false, no.";

    public SearchCommand(HashMap<String, List<String>> searchShowsHashMap) {
        nameList = searchShowsHashMap.get("name"); // done
        typeList = searchShowsHashMap.get("type");
        actorList = searchShowsHashMap.get("actor");
        isWatchedList = searchShowsHashMap.get("is_watched");
        isInternalList = searchShowsHashMap.get("is_internal"); // done
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        try {
            if (requestedIsInternal()) {
                if (requestedSearchFromInternal()) {
                    for (String showName : nameList) {
                        addShowFromWatchListIfSameNameAs(showName, model);
                    }
                } else if (requestedSearchFromOnline()) {
                    for (String showName : nameList) {
                        addShowFromOnlineIfSameNameAs(showName);
                    }
                } else {
                    throw new CommandException(MESSAGE_INVALID_IS_INTERNAL_COMMAND);
                }
            } else { // there's no restriction on where to search from
                for (String showName : nameList) {
                    addShowFromWatchListIfSameNameAs(showName, model);
                    addShowFromOnlineIfSameNameAs(showName);
                }
            }

            model.updateSearchResultList(searchResult);

            return new CommandResult(String.format(Messages.MESSAGE_SHOWS_LISTED_OVERVIEW, model.getSearchResultList().size()));
        } catch (OnlineConnectionException e) {
            return null;
            //to be added
        }
    }

    private boolean requestedIsInternal() {
        return !isInternalList.isEmpty();
    }

    private boolean requestedSearchFromInternal() {
        return isInternalList.get(0).equals("true") || isInternalList.get(0).equals("yes");
    }

    private boolean requestedSearchFromOnline() {
        return isInternalList.get(0).equals("false") || isInternalList.get(0).equals("no");
    }

    private boolean requestedIsWatched() {
        return !isWatchedList.isEmpty();
    }

    private boolean requestedSearchFromWatched() {
        return isWatchedList.get(0).equals("true") || isWatchedList.get(0).equals("yes");
    }

    private boolean requestedSearchFromWatchList() {
        return isInternalList.get(0).equals("false") || isInternalList.get(0).equals("no");
    }

    private void addShowFromWatchListIfSameNameAs(String showName, Model model) {
        if (!showName.equals(EMPTY_STRING)) {
            List<Show> filteredShowList = model.getShowIfHasName(new Name(showName));
            for (Show show : filteredShowList) {
                if (requestedIsWatched()) {
                    if (requestedSearchFromWatched() && !show.isWatched().getIsWatchedBoolean()) {
                        continue; // skip if request to be watched but show is not watched
                    } else if (requestedSearchFromWatchList() && show.isWatched().getIsWatchedBoolean()) {
                        continue; //skip if requested to be in watchlist but show is watched
                    }
                }
                searchResult.add(show);
            }
        }
    }

    private void addShowFromOnlineIfSameNameAs(String showName) throws OnlineConnectionException {
        if (!showName.equals("")) {
            List<Movie> movies = new ApiMain().getMovieByName(showName);
            List<TvShow> tvShows = new ApiMain().getTvShowByName(showName);
            for (Movie movie : movies) {
                // check if need to check if it is isWatched
                searchResult.add(movie);
            }
            for(TvShow tvShow : tvShows) {
                // check if need to check if it is isWatched
                searchResult.add(tvShow);
            }
        }
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
