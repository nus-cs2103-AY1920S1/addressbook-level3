package seedu.guilttrip.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.guilttrip.model.Model.PREDICATE_SHOW_ALL_ENTRIES;

import seedu.guilttrip.logic.CommandHistory;
import seedu.guilttrip.model.Model;

/**
 * Lists all wishes in the guilttrip to the user.
 */
public class WishListCommand extends Command {

    public static final String COMMAND_WORD = "wishList";
    public static final String ONE_LINER_DESC = COMMAND_WORD + ": Lists all wishes in the guilttrip to the user.;";
    public static final String MESSAGE_USAGE = ONE_LINER_DESC;

    public static final String MESSAGE_SUCCESS = "Listed all wishes";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredWishes(PREDICATE_SHOW_ALL_ENTRIES);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
