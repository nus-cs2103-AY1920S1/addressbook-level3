package seedu.elisa.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.elisa.commons.core.item.Item;
import seedu.elisa.logic.commands.exceptions.CommandException;
import seedu.elisa.model.ItemModel;
import seedu.elisa.model.item.TaskList;

/**
 * Adds a Task to the item model.
 */
public class AddTaskCommand extends AddCommand {

    public static final String SHOW_TASK_VIEW = "T";
    public static final String MESSAGE_SUCCESS = "New Task added: %1$s\nDon't just watch it pile up!";

    public AddTaskCommand(Item item) {
        super(item);
    }

    @Override
    public CommandResult execute(ItemModel model) throws CommandException {
        requireNonNull(model);

        // Check if item already exists, else, add it to the model.
        if (model.hasItem(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_ITEM);
        } else {
            model.addItem(toAdd);
        }

        // Notify Ui to change the view the that of the newly added item.
        try {
            if(!(model.getVisualList() instanceof TaskList)){
                model.setVisualList(SHOW_TASK_VIEW);
            }

        } catch (Exception e) {
            // should not enter here as itemType is definitely valid.
        }

        if (!isExecuted()) {
            model.getElisaCommandHistory().clearRedo();
            setExecuted(true);
        }
        //return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }
}
