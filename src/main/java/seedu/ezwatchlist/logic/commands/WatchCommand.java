package seedu.ezwatchlist.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.ezwatchlist.model.Model.PREDICATE_SHOW_ALL_SHOWS;

import java.util.List;
import java.util.Set;

import seedu.ezwatchlist.commons.core.Messages;
import seedu.ezwatchlist.commons.core.index.Index;
import seedu.ezwatchlist.logic.commands.exceptions.CommandException;
import seedu.ezwatchlist.model.Model;
import seedu.ezwatchlist.model.show.*;
import seedu.ezwatchlist.model.actor.Actor;

/**
 * Marks an existing show in the watchlist as watched.
 */
public class WatchCommand extends Command {

    public static final String COMMAND_WORD = "watch";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks an existing show in the watchlist as watched "
            + "by the index number used in the displayed show list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_WATCH_SHOW_SUCCESS = "Marked show as watched: %1$s";
    public static final String MESSAGE_UNWATCH_SHOW_SUCCESS = "Unmarked show as watched: %1$s";
    public static final String MESSAGE_DUPLICATE_SHOW = "This show already exists in the watchlist.";

    private final Index index;

    /**
     * @param index of the show in the filtered show list to edit
     */
    public WatchCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Show> lastShownList = model.getFilteredShowList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_SHOW_DISPLAYED_INDEX);
        }

        Show showToEdit = lastShownList.get(index.getZeroBased());
        Show editedShow = createEditedShow(showToEdit);

        if (!showToEdit.isSameShow(editedShow) && model.hasShow(editedShow)) {
            throw new CommandException(MESSAGE_DUPLICATE_SHOW);
        }

        model.setShow(showToEdit, editedShow);
        model.updateFilteredShowList(PREDICATE_SHOW_ALL_SHOWS);

        boolean isWatched = editedShow.isWatched().value;

        if (isWatched) {
            return new CommandResult(String.format(MESSAGE_WATCH_SHOW_SUCCESS, editedShow));
        } else {
            return new CommandResult(String.format(MESSAGE_UNWATCH_SHOW_SUCCESS, editedShow));
        }
    }

    /**
     * Creates and returns a {@code Show} with the details of {@code showToEdit}
     * edited with {@code editShowDescriptor}.
     */
    private static Show createEditedShow(Show showToEdit) {
        assert showToEdit != null;

        Name name = showToEdit.getName();
        Date dateOfRelease = showToEdit.getDateOfRelease();
        IsWatched updatedIsWatched = new IsWatched(!showToEdit.isWatched().value);
        Description description = showToEdit.getDescription();
        RunningTime runningTime = showToEdit.getRunningTime();
        Set<Actor> actors = showToEdit.getActors();

        if (showToEdit.type.equals("Movie")) {
            Movie editedShow = new Movie(name, description, updatedIsWatched,
                    dateOfRelease, runningTime, actors);
            return editedShow;
        } else { //showToEdit.type.equals("Tv show")
            TvShow editedShow = new TvShow(name, description, updatedIsWatched,
                    dateOfRelease, runningTime, actors, 0,0,null);
            return editedShow;
        }
    }

}
