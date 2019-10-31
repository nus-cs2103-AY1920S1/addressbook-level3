package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ENTRIES;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class WishListCommand extends Command {

    public static final String COMMAND_WORD = "wishList";

    public static final String MESSAGE_SUCCESS = "Listed all wishes";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredWishes(PREDICATE_SHOW_ALL_ENTRIES);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
