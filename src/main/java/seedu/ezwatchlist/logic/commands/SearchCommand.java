package seedu.ezwatchlist.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import seedu.ezwatchlist.api.ApiMain;
import seedu.ezwatchlist.api.exceptions.OnlineConnectionException;
import seedu.ezwatchlist.commons.core.Messages;
import seedu.ezwatchlist.logic.commands.exceptions.CommandException;
import seedu.ezwatchlist.model.Model;
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
    public static final String MESSAGE_INVALID_IS_INTERNAL_COMMAND =
            "Invalid input. i/[Option] where option is either true, yes or false, no.";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Searches for shows online whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " Joker";

    private final String emptyString = "";
    private List<String> nameList;
    private List<String> stringList;
    private List<String> actorList;
    private List<String> isWatchedList;
    private List<String> isInternalList;

    private List<Show> searchResult = new ArrayList<>();

    public SearchCommand(HashMap<String, List<String>> searchShowsHashMap) {
        nameList = searchShowsHashMap.get("name");
        stringList = searchShowsHashMap.get("type");
        actorList = searchShowsHashMap.get("actor");
        isWatchedList = searchShowsHashMap.get("is_watched");
        isInternalList = searchShowsHashMap.get("is_internal");
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        try {
            if (!isInternalList.isEmpty()) { // set to be must internal (if true or yes)
                if (isInternalList.get(0).equals("true") || isInternalList.get(0).equals("yes")) {
                    for (String showName : nameList) {
                        addShowFromWatchListIfSameNameAs(showName, model);
                    }
                } else if (isInternalList.get(0).equals("false") || isInternalList.get(0).equals("no")) {
                    for (String showName : nameList) {
                        addShowFromOnlineIfSameNameAs(showName);
                    }
                } else {
                    throw new CommandException(MESSAGE_INVALID_IS_INTERNAL_COMMAND);
                }
            } else {
                for (String showName : nameList) {
                    addShowFromWatchListIfSameNameAs(showName, model);
                    addShowFromOnlineIfSameNameAs(showName);
                }
            }

            model.updateSearchResultList(searchResult);

            return new CommandResult(String.format(
                    Messages.MESSAGE_SHOWS_LISTED_OVERVIEW, model.getSearchResultList().size()));
        } catch (OnlineConnectionException e) {
            return new CommandResult("Not connected to internet"); //to be added
        }
    }

    /**
     * Adds show from list if it has the same name as the given show.
     * @param showName name of the given show.
     * @param model current model of the program.
     */
    private void addShowFromWatchListIfSameNameAs(String showName, Model model) {
        if (!showName.equals(emptyString) /*&&  (model.hasShowName(name))*/) {
            List<Show> filteredShowList = model.getShowIfSameNameAs(new Name(showName));
            for (Show show : filteredShowList) {
                searchResult.add(show);
            }
        }
    }

    /**
     * adds show from online list if it has the same name as the given show.
     * @param showName name of the given show.
     * @throws OnlineConnectionException if the user is not connected.
     */
    private void addShowFromOnlineIfSameNameAs(String showName) throws OnlineConnectionException {
        if (!showName.equals("")) {
            List<Movie> movies = new ApiMain().getMovieByName(showName);
            List<TvShow> tvShows = new ApiMain().getTvShowByName(showName);
            for (Movie movie : movies) {
                searchResult.add(movie);
            }
            for (TvShow tvShow : tvShows) {
                searchResult.add(tvShow);
            }
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SearchCommand // instanceof handles nulls
                && nameList.equals(((SearchCommand) other).nameList)
                && stringList.equals(((SearchCommand) other).stringList)
                && actorList.equals(((SearchCommand) other).actorList)
                && isWatchedList.equals(((SearchCommand) other).isWatchedList)
                && isInternalList.equals(((SearchCommand) other).isInternalList));
    }
}
