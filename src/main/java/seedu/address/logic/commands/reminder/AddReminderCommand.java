package seedu.address.logic.commands.reminder;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_TIME;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.reminder.Reminder;


/**
 * command to add reminders.
 */
public class AddReminderCommand extends Command {
    public static final String COMMAND_WORD = "add_reminder";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a Reminder for a certain task. "
            + "Parameters: "
            + PREFIX_REMINDER_DESCRIPTION + "DESCRIPTION "
            + PREFIX_REMINDER_TIME + "START_TIME, END_TIME\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_REMINDER_DESCRIPTION + "CS2103T Lecture "
            + PREFIX_REMINDER_TIME + "13/10/2019 13:00, 13/10/2019 15:00 ";

    public static final String MESSAGE_SUCCESS = "New reminder added: %1$s";

    private final Reminder toAdd;

    /**
     * Creates an AddTaskCommand to add the specified {@code Task}
     */
    public AddReminderCommand(Reminder reminder) {
        requireNonNull(reminder);
        toAdd = reminder;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.addReminder(toAdd);
        model.commitTutorAid();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd),
                false, false, false, false, false,
                false, false, true);
    }
}
