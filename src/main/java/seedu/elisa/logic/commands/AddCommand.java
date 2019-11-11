package seedu.elisa.logic.commands;

import static java.util.Objects.requireNonNull;

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

    /**
     * Executes this AddCommand to add the new item to this model.
     * @param model {@code Model} which the command should operate on.
     * @return CommandResult of executing this add
     * @throws CommandException if item fails to be added to this model
     */
    public CommandResult execute(ItemModel model) throws CommandException {
        requireNonNull(model);

        // Check if item already exists, else, add it to the model.
        if (model.hasItem(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_ITEM);
        } else {
            model.addItem(toAdd);
        }

        if (toAdd.hasAutoReschedule()) {
            Event event = toAdd.getEvent().get();
            RescheduleTask task = new RescheduleTask(toAdd, event.getPeriod(), model);
            AUTO_RESCHEDULE_MANAGER.add(task);
        }

        // Notify Ui to change the view the that of the newly added item.
        try {
            model.setVisualList(getListView());
        } catch (Exception e) {
            // should not enter here as listView given is definitely valid.
        }

        if (!isExecuted()) {
            model.getElisaCommandHistory().clearRedo();
            setExecuted(true);
        }

        return new CommandResult(String.format(getMessageSuccess(), toAdd));
    }

    public abstract String getListView();

    public abstract String getMessageSuccess();

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

