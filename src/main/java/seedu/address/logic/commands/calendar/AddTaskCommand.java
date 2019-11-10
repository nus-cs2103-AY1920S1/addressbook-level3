package seedu.address.logic.commands.calendar;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLASSID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MARKING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_TIME;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.task.Task;


/**
 * command to add tasks.
 */
public class AddTaskCommand extends Command {
    public static final String COMMAND_WORD = "add_task";
    public static final String MESSAGE_DUPLICATE_TASKS =
            "This task already exists in the address book";
    public static final String MESSAGE_DUPLICATE_REMINDER =
            "This reminder already exists in the address book";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the calendar. "
            + "Parameters: "
            + PREFIX_CLASSID + "MODULE "
            + PREFIX_MARKING + "MARKING_STATUS " + "(Y OR N) "
            + PREFIX_TASK_TIME + "START_TIME, END_TIME"
            + "...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_CLASSID + "CS2103T "
            + PREFIX_MARKING + "Y "
            + PREFIX_TASK_TIME + "13/10/2019 13:00, 13/10/2019 15:00 ";

    public static final String MESSAGE_SUCCESS = "New task added:\n%1$s";

    private final Task toAdd;
    private boolean hasReminder = false;
    private Reminder reminder;

    /**
     * Creates an AddTaskCommand to add the specified {@code Task}
     */
    public AddTaskCommand(Task task) {
        requireNonNull(task);
        toAdd = task;
        //can initialize new Reminder here
    }

    public void getReminder(Reminder reminder) {
        this.reminder = reminder;
        this.hasReminder = true;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        //execute new Reminder here but just that it returns Task result

        if (model.hasTask(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_TASKS);
        }

        if (hasReminder) {
            if (model.hasReminder(reminder)) {
                throw new CommandException(MESSAGE_DUPLICATE_REMINDER);
            }
            model.addReminder(reminder);
            model.commitTutorAid();
        }
        model.addTask(toAdd);
        model.commitTutorAid();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd),
                false, false, false, false, true,
                false, false, false);
    }


}
