package seedu.ezwatchlist.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.ezwatchlist.model.Model.PREDICATE_ALL_SHOWS;

import seedu.ezwatchlist.model.Model;

/**
 * Lists all shows in the watchlist to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all shows";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredShowList(PREDICATE_ALL_SHOWS);
        return new CommandResult(MESSAGE_SUCCESS, false);
    }
}
