package seedu.elisa.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.elisa.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.elisa.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.elisa.logic.parser.CliSyntax.PREFIX_REMINDER;
import static seedu.elisa.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.elisa.commons.core.item.Event;
import seedu.elisa.commons.core.item.Item;
import seedu.elisa.logic.commands.exceptions.CommandException;
import seedu.elisa.model.AutoRescheduleManager;
import seedu.elisa.model.ItemModel;
import seedu.elisa.model.RescheduleTask;


/**
 * Add an Item to the item list.
 */
public abstract class AddCommand extends UndoableCommand {

    public static final AutoRescheduleManager AUTO_RESCHEDULE_MANAGER = AutoRescheduleManager.getInstance();
    public static final String COMMAND_WORD = "add";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a Task to the Task List. "
            + "Parameters: "
            + "description "
            + "<Optional> " + PREFIX_DATETIME + "Deadline "
            + "<Optional> " + PREFIX_REMINDER + "Reminder "
            + "<Optional> " + PREFIX_PRIORITY + "Priority "
            + "<Optional> " + PREFIX_TAG + "Tag ";

    public static final String MESSAGE_SUCCESS = "New Item added: %1$s";
    public static final String MESSAGE_DUPLICATE_ITEM = "This item already exists.";

    protected final Item toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Item}
     */
    public AddCommand(Item item) {
        requireNonNull(item);
        toAdd = item;
    }

    public abstract CommandResult execute(ItemModel model) throws CommandException;

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }

    @Override
    public void reverse(ItemModel model) throws CommandException {
        model.deleteItem(toAdd);

        if (toAdd.hasAutoReschedule()) {
            Event event = toAdd.getEvent().get(); // if autoReschedule is present, item definitely has an event.
            RescheduleTask.removeFromAllTasks(toAdd.getEvent().get());
        }
    }
}

