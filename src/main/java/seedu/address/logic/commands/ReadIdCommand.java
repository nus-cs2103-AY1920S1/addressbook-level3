package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DRIVER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Customer;
import seedu.address.model.task.Task;

/**
 * Displays the full details of the entity using it's unique id from its list.
 * Entity refers to Task, Driver and Customer.
 */
public class ReadIdCommand extends Command {

    public static final String COMMAND_WORD = "read";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Get the details of Task / Driver / Customer using their UNIQUE ID.\n"
            + "Parameters: [" + PREFIX_TASK + " Task ID]  |  "
            + "Parameters: [" + PREFIX_CUSTOMER + " Customer ID]  |  "
            + "Parameters: [" + PREFIX_DRIVER + " Driver ID]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TASK + " 1 \n"
            + "Example 2: " + COMMAND_WORD + " "
            + PREFIX_DRIVER + " 3";

    public static final String MESSAGE_INVALID_ID = "Invalid %1$s id.";

    private final String className;
    private final int id;

    public ReadIdCommand(String className, int id) {
        this.id = id;
        this.className = className;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (className.equals(Task.class.getSimpleName())) {
            if (!model.hasTask(id)) {
                throw new CommandException(String.format(MESSAGE_INVALID_ID, className));
            }

            return new CommandResult(model.getTask(id).toString());
        } else if (className.equals(Customer.class.getSimpleName())) {
            if (!model.hasCustomer(id)) {
                throw new CommandException(String.format(MESSAGE_INVALID_ID, className));
            }

            return new CommandResult(model.getCustomer(id).toString());
        } else {
            //for read driver
            if (!model.hasDriver(id)) {
                throw new CommandException(String.format(MESSAGE_INVALID_ID, className));
            }

            return new CommandResult(model.getDriver(id).toString());
        }
    }
}
