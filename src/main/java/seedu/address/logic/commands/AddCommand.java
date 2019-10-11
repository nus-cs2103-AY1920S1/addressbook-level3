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

    public static final String MESSAGE_SUCCESS = "New Task added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This task already exists in the Task List";

    private final Item toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Item}
     */
    public AddCommand(Item item) {
        requireNonNull(item);
        toAdd = item;
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
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}

