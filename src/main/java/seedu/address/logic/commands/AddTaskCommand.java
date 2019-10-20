package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GOODS;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.task.Task;

/**
 * Adds a task into the task manager.
 */
public class AddTaskCommand extends Command {

    public static final String COMMAND_WORD = "addT";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the task list. \n"
            + "Parameters: "
            + "[" + PREFIX_GOODS + "DESCRIPTION] "
            + "[" + PREFIX_CUSTOMER + "CUSTOMER ID] "
            + "[" + PREFIX_DATETIME + "DATETIME] \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_GOODS + "20 boxes of utensils "
            + PREFIX_CUSTOMER + "41 "
            + PREFIX_DATETIME + "18/12/2019";
    public static final String MESSAGE_SUCCESS = "New task added: %s";
    public static final String MESSAGE_DUPLICATE_TASK = "Task is already exists in the task list.";

    public static final String MESSAGE_INVALID_CUSTOMER_ID = "Invalid customer id.";

    private final Task taskToAdd;
    private final int customerId;

    public AddTaskCommand(Task taskToAdd, int customerId) {
        requireAllNonNull(taskToAdd, customerId);
        this.taskToAdd = taskToAdd;
        this.customerId = customerId;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasCustomer(customerId)) {
            throw new CommandException(MESSAGE_INVALID_CUSTOMER_ID);
        }

        taskToAdd.setCustomer(model.getCustomer(customerId));
        model.addTask(taskToAdd);

        return new CommandResult(String.format(MESSAGE_SUCCESS, taskToAdd));
    }
}
