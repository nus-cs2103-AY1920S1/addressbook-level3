package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INVENTORY_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INVENTORY_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INVENTORY_TASKID;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.core.index.InvIndex;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.inventory.InvName;
import seedu.address.model.inventory.Inventory;
import seedu.address.model.inventory.Price;
import seedu.address.model.task.Task;

/**
 * Adds a task to the address book.
 */

public class AddInventoryCommand extends Command{
    public static final String COMMAND_WORD = "add-inv";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a inventory to the project Dashboard. "
            + "Parameters: "
            + PREFIX_INVENTORY_NAME + "NAME "
            + PREFIX_INVENTORY_PRICE + "PRICE "
            + PREFIX_INVENTORY_TASKID + "TASKID "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_INVENTORY_NAME + "BALLS "
            + PREFIX_INVENTORY_PRICE + "8.50"
            + PREFIX_INVENTORY_TASKID + "2";

    public static final String MESSAGE_SUCCESS = "New inventory added: %1$s";
    public static final String MESSAGE_DUPLICATE_INVENTORY = "This inventory already exists in the address book";
    public static final String MESSAGE_INDEX_EXCEEDED = "ThE index entered for tasks is invalid";

    private final Index taskID;
    private final InvName name;
    private final Price price;

    /**
     * Creates an AddCommand to add the specified {@code Task}
     */
    public AddInventoryCommand(Index taskID, InvName name, Price price) {
        requireAllNonNull(taskID, name, price);
        this.taskID = taskID;
        this.name = name;
        this.price = price;
    }
    public AddInventoryCommand(Index taskID, InvName name) {
        requireAllNonNull(taskID, name);
        this.taskID = taskID;
        this.name = name;
        this.price = new Price(0);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        int tasksLength = model.getTasksLength();

        if (taskID.getOneBased() > tasksLength) {
           throw new CommandException(MESSAGE_INDEX_EXCEEDED);
        }

        Task taskToAdd = model.getFilteredTasksList().get(taskID.getZeroBased());
        Inventory toAdd = new Inventory(taskToAdd, name, price);

        if (model.hasInventory(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_INVENTORY);
        }

        model.addInventory(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && taskID.equals(((AddInventoryCommand) other).taskID))
                && name.equals(((AddInventoryCommand)other).name)
                && price.equals(((AddInventoryCommand)other).price);
    }
}
