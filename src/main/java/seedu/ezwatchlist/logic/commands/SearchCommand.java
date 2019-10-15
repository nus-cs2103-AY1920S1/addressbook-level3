package seedu.ezwatchlist.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.ezwatchlist.commons.core.Messages;
import seedu.ezwatchlist.model.Model;
import seedu.ezwatchlist.model.show.NameContainsKeywordsPredicate;
import seedu.ezwatchlist.api.ApiMain;
import seedu.ezwatchlist.model.show.Movie;
import seedu.ezwatchlist.model.show.Show;

import java.util.ArrayList;
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

    private final String searchString;

    public SearchCommand(String searchString) {
        this.searchString = searchString;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        List<Movie> movies = new ApiMain().getMovieByName(searchString);
        //List<TvShow> tvShows = new ApiMain().getTvShowByName(searchString);
        List<Show> searchResult = new ArrayList<>();

        for (Movie movie : movies) {
            searchResult.add(movie);
        }

        //for(TvShow tvShow : tvShows) {
        //     searchResult.addShow(tvShow);
        //}

        model.updateSearchResultList(searchResult);
        return new CommandResult(
                String.format(Messages.MESSAGE_SHOWS_LISTED_OVERVIEW, model.getFilteredShowList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SearchCommand // instanceof handles nulls
                && searchString.equals(((SearchCommand) other).searchString)); // state check
    }
}
