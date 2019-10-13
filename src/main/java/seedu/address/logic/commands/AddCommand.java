package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.commons.core.item.Item;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ItemModel;


/**
 * Add an Item to the item list.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a Task to the Task List. "
            + "Parameters: "
            + "description "
            + "<Optional> " + PREFIX_DATETIME + "Deadline "
            + "<Optional> " + PREFIX_REMINDER + "Reminder "
            + "<Optional> " + PREFIX_PRIORITY + "Priority "
            + "<Optional> " + PREFIX_TAG + "Tag ";

    public static final String MESSAGE_SUCCESS = "New Item added: %1$s";
    public static final String MESSAGE_DUPLICATE_ITEM = "This item already exists in the List";

    public static String itemType;

    private final Item toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Item}
     */
    public AddCommand(Item item) {
        requireNonNull(item);
        toAdd = item;
    }

    public static void setItemType(String newItemType) {
        itemType = newItemType;
    }

    @Override
    public CommandResult execute(ItemModel model) throws CommandException {
        requireNonNull(model);

        /*
        if (model.hasItem(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }
        */

        model.addItem(toAdd);
        // Notify Ui to change the view the that of the newly added item.
        try {
            model.setVisualList(itemType);
        } catch (Exception e) {
            // should not enter here as itemType is definitely valid.
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd), itemType);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}

