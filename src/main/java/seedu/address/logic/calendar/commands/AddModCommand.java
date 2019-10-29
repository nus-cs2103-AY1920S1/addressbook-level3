package seedu.address.logic.calendar.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.calendar.parser.CliSyntax.PREFIX_TASKDAY;
import static seedu.address.logic.calendar.parser.CliSyntax.PREFIX_TASKDESCRIPTION;
import static seedu.address.logic.calendar.parser.CliSyntax.PREFIX_TASKTAG;
import static seedu.address.logic.calendar.parser.CliSyntax.PREFIX_TASKTIME;
import static seedu.address.logic.calendar.parser.CliSyntax.PREFIX_TASKTITLE;

import seedu.address.logic.calendar.commands.exceptions.CommandException;
import seedu.address.model.calendar.CalendarModel;
import seedu.address.model.calendar.task.Task;

/**
 * Adds a <code>ModuleTask</code> to Modulo's calendar.
 */
public class AddModCommand extends Command {

    public static final String COMMAND_WORD = "addmod";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a module to the calendar. "
        + "Parameters: "
        + PREFIX_TASKTITLE + "TITLE "
        + PREFIX_TASKDAY + "DAY "
        + PREFIX_TASKDESCRIPTION + "DESCRIPTION "
        + PREFIX_TASKTIME + "TIME "
        + "[" + PREFIX_TASKTAG + "TAG]...\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_TASKTITLE + "CS2109 Tutorial "
        + PREFIX_TASKDAY + "thursday "
        + PREFIX_TASKDESCRIPTION + "LT17 "
        + PREFIX_TASKTIME + "08:00 "
        + PREFIX_TASKTAG + "IMPORTANT "
        + PREFIX_TASKTAG + "CS2109";

    public static final String MESSAGE_SUCCESS = "New module added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This module task already exists in the calendar";

    private final Task toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Task}
     */
    public AddModCommand(Task task) {
        requireNonNull(task);
        toAdd = task;
    }

    @Override
    public CommandResult execute(CalendarModel calendarModel) throws CommandException {
        requireNonNull(calendarModel);

        if (calendarModel.hasTask(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        calendarModel.addTask(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof AddModCommand // instanceof handles nulls
            && toAdd.equals(((AddModCommand) other).toAdd));
    }
}
