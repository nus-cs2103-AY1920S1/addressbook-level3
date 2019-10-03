package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;
import seedu.address.model.item.CheckCommandPredicate;
import seedu.address.model.item.ExpiringSoonPredicate;
import seedu.address.model.item.ReminderThresholdExceededPredicate;

/**
 * Displays all items whose expiry date falls within the specified duration (in days).
 */
public class CheckCommand extends Command {
    public static final String COMMAND_WORD = "check";

    public static final String MESSAGE_SUCCESS = "Item(s) expiring soon";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays all items whose expiry date is within"
            + "the specified duration (in days). Expired items, if any, are also included in the list.\n"
            + "Format: check[|<days>]\n"
            + "Example: " + COMMAND_WORD + "|7\n"
            + "If no duration is specified, expired items and items whose days to expiry date are less than or equals "
            + "to the remainder threshold will be displayed.\n";

    private final CheckCommandPredicate predicate;

    public CheckCommand(ExpiringSoonPredicate predicate) {
        this.predicate = predicate;
    }

    public CheckCommand(ReminderThresholdExceededPredicate predicate) {
        this.predicate = predicate;
    }


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredItemList(this.predicate);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
