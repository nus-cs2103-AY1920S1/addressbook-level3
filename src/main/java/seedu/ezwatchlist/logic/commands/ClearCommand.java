package seedu.ezwatchlist.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.ezwatchlist.model.WatchList;
import seedu.ezwatchlist.model.Model;

/**
 * Clears the watchlist.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Watchlist has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setWatchList(new WatchList());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
