package io.xpire.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import io.xpire.model.Model;
import io.xpire.model.item.ExpiringSoonPredicate;
import io.xpire.model.item.Item;
import io.xpire.model.item.ReminderThresholdExceededPredicate;

/**
 * Displays all items whose expiry date falls within the specified duration (in days).
 */
public class CheckCommand extends Command {
    public static final String COMMAND_WORD = "check";

    public static final String MESSAGE_SUCCESS = "Item(s) expiring soon";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays all items whose expiry date is within"
            + "the specified duration (in days). Expired items, if any, are also included in the list.\n"
            + "Format: check[|<days>] (days must be a non negative number)\n"
            + "Example: " + COMMAND_WORD + "|7\n"
            + "If no duration is specified, expired items and items whose days to expiry date are less than or equals "
            + "to the remainder threshold will be displayed.\n";

    private final Predicate<Item> predicate;

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
