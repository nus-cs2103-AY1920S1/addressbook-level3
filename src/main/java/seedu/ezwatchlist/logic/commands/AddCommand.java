package seedu.ezwatchlist.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_ACTOR;
import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_DATE_OF_RELEASE;
import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_IS_WATCHED;
import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_RUNNING_TIME;
import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_TYPE;

import java.util.List;

import seedu.ezwatchlist.logic.commands.exceptions.CommandException;
import seedu.ezwatchlist.model.Model;
import seedu.ezwatchlist.model.show.Show;

/**
 * Adds a show to the watchlist.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a show to the watchlist. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_TYPE + "TYPE ('movie' or 'tv') "
            + "[" + PREFIX_DATE_OF_RELEASE + "DATE OF RELEASE] "
            + "[" + PREFIX_IS_WATCHED + "WATCHED ('true' or 'false')] "
            + "[" + PREFIX_RUNNING_TIME + "RUNNING TIME] "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] "
            + "[" + PREFIX_ACTOR + "ACTOR]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Joker "
            + PREFIX_TYPE + "movie "
            + PREFIX_DATE_OF_RELEASE + "4 October 2019 "
            + PREFIX_IS_WATCHED + "true "
            + PREFIX_RUNNING_TIME + "122 "
            + PREFIX_DESCRIPTION + "Joker is funny "
            + PREFIX_ACTOR + "Joaquin Phoenix "
            + PREFIX_ACTOR + "Robert De Niro";

    public static final String MESSAGE_USAGE2 = COMMAND_WORD + ": Sync a show found online to the watchlist. "
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "New show added: %1$s";
    public static final String MESSAGE_DUPLICATE_SHOW = "This show already exists in the watchlist";
    public static final String MESSAGE_SUCCESS2 = "Add movie: %1$s";

    public static final String UNSUCCESSFUL_INDEX = "Search Result Page is currently empty.";
    public static final String UNSUCCESSFUL_LARGER = "The index is larger than the total number"
            + " of shows in search page.";
    public static final String NOT_AT_SEARCH_LIST_PAGE = "'Add Index' command is only available in Search Panel";

    private final Show toAdd;



    private final int index;
    private final boolean isFromSearch;

    /**
     * Creates an AddCommand to add the specified {@code Show}
     */
    public AddCommand(Show show) {
        requireNonNull(show);
        toAdd = show;
        index = -1;
        isFromSearch = false;
    }

    public AddCommand(int index) {
        requireNonNull(index);
        this.index = index;
        toAdd = null;
        isFromSearch = true;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (isFromSearch) {
            return fromSearch(model);
        }
        if (model.hasShow(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_SHOW);
        }

        model.addShow(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd), true);
    }

    /**
     * Retrieve movies from searchlist found in model.
     * @param model
     * @return
     * @throws CommandException
     */

    public CommandResult fromSearch(Model model) throws CommandException {
        List<Show> searchResultList = model.getSearchResultList();
        if (searchResultList.isEmpty()) {
            throw new CommandException(UNSUCCESSFUL_INDEX);
        }
        if (index > searchResultList.size()) {
            throw new CommandException(UNSUCCESSFUL_LARGER);
        }
        Show fromImdb = searchResultList.get(index - 1);
        if (model.hasShow(fromImdb)) {
            throw new CommandException(MESSAGE_DUPLICATE_SHOW);
        }

        model.addShow(fromImdb);
        return new CommandResult(String.format(MESSAGE_SUCCESS2, fromImdb), true);
    }
    public boolean isFromSearch() {
        return isFromSearch;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd)
                && index == (((AddCommand) other).index)
                && isFromSearch == ((AddCommand) other).isFromSearch);
    }
}
