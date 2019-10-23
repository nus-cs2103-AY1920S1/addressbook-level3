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
    private List<String> name_list;
    private List<String> type_list;
    private List<String> actor_list;
    private List<String> is_watched_list;
    private List<String> is_internal_list;

    List<Show> searchResult = new ArrayList<>();
    public static final String MESSAGE_INVALID_IS_INTERNAL_COMMAND =
            "Invalid input. i/[Option] where option is either true, yes or false, no.";

    public SearchCommand(HashMap<String, List<String>> searchShowsHashMap) {
        name_list = searchShowsHashMap.get("name");
        type_list = searchShowsHashMap.get("type");
        actor_list = searchShowsHashMap.get("actor");
        is_watched_list = searchShowsHashMap.get("is_watched");
        is_internal_list = searchShowsHashMap.get("is_internal");
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        try {
            if (!is_internal_list.isEmpty()) { // set to be must internal (if true or yes)
                if (is_internal_list.get(0).equals("true") || is_internal_list.get(0).equals("yes")) {
                    for (String showName : name_list) {
                        addShowFromWatchListIfSameNameAs(showName, model);
                    }
                } else if (is_internal_list.get(0).equals("false") || is_internal_list.get(0).equals("no")) {
                    for (String showName : name_list) {
                        addShowFromOnlineIfSameNameAs(showName);
                    }
                } else {
                    throw new CommandException(MESSAGE_INVALID_IS_INTERNAL_COMMAND);
                }
            } else {
                for (String showName : name_list) {
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

    private void addShowFromWatchListIfSameNameAs(String showName, Model model) {
        if (!showName.equals(EMPTY_STRING) /*&&  (model.hasShowName(name))*/) {
            List<Show> filteredShowList = model.getShowIfSameNameAs(new Name(showName));
            for (Show show : filteredShowList) {
                searchResult.add(show);
            }
        }
    }

    private void addShowFromOnlineIfSameNameAs(String showName) throws OnlineConnectionException {
        if (!showName.equals("")) {
            List<Movie> movies = new ApiMain().getMovieByName(showName);
            List<TvShow> tvShows = new ApiMain().getTvShowByName(showName);
            for (Movie movie : movies) {
                searchResult.add(movie);
            }
            for(TvShow tvShow : tvShows) {
                searchResult.add(tvShow);
            }
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SearchCommand // instanceof handles nulls
                && name_list.equals(((SearchCommand) other).name_list)
                && type_list.equals(((SearchCommand) other).type_list)
                && actor_list.equals(((SearchCommand) other).actor_list)
                && is_watched_list.equals(((SearchCommand) other).is_watched_list)
                && is_internal_list.equals(((SearchCommand) other).is_internal_list));
    }
}
