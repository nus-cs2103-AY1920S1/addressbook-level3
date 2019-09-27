package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;
import seedu.address.model.item.ExpiringSoonPredicate;

/**
 * Finds and lists all items in expiry date tracker whose expiry date falls within the specified duration.
 * Duration is in days.
 */
public class CheckCommand extends Command {
    public static final String COMMAND_WORD = "check";

    public static final String MESSAGE_SUCCESS = "Listed items expiring soon";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all items whose expiry date is within"
            + "the specified duration (in days) and displays them as a list with index numbers.\n"
            + "Parameters: <DAYS>\n"
            + "Example: " + COMMAND_WORD + " 7";

    private final ExpiringSoonPredicate predicate;

    public CheckCommand(ExpiringSoonPredicate predicate) {
        this.predicate = predicate;
    }


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredItemList(predicate);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
