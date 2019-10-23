package seedu.ezwatchlist.logic.commands;

import seedu.ezwatchlist.api.ApiMain;
import seedu.ezwatchlist.commons.core.Messages;
import seedu.ezwatchlist.commons.core.index.Index;
import seedu.ezwatchlist.logic.commands.exceptions.CommandException;
import seedu.ezwatchlist.model.Model;
import seedu.ezwatchlist.model.actor.Actor;
import seedu.ezwatchlist.model.show.Show;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Objects.requireNonNull;
import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_NAME;

public class SyncCommand extends Command {

    public static final String COMMAND_WORD = "sync";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sync a show from IMDB to the watchlist. "
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Sync movie: %1$s";
    public static final String MESSAGE_DUPLICATE_SHOW = "?"; //"This show already exists in the watchlist";

    private Index toSync;

    public SyncCommand(Index toSync){
        requireNonNull(toSync);
        this.toSync = toSync;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Show> searchResultList = model.getSearchResultList();
        System.err.println(searchResultList);
        if (toSync.getZeroBased() >= searchResultList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_SHOW_DISPLAYED_INDEX);
        }
        Show FromIMDB = searchResultList.get(toSync.getZeroBased());
        model.addShow(FromIMDB);
        return new CommandResult(String.format(MESSAGE_SUCCESS, FromIMDB));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SyncCommand // instanceof handles nulls
                && toSync.equals(((SyncCommand) other).toSync));
    }

}
