package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_SHOWS;

import seedu.address.model.Model;

/**
 * Lists all shows in the watchlist to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all shows";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredShowList(PREDICATE_SHOW_ALL_SHOWS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
