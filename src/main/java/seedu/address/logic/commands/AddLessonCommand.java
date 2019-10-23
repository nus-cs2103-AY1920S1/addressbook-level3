package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LESSONNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.scheduler.Scheduler;

/**
 * Adds a lesson to the classroom.
 */
public class AddLessonCommand extends Command {

    public static final String COMMAND_WORD = "addlesson";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a lesson to the classroom. "
            + "Parameters: "
            + PREFIX_LESSONNAME + "NAME "
            + PREFIX_TIME + "DATE "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_LESSONNAME + "Math 4E7 "
            + PREFIX_TIME + "14/07/2020 1200 ";

    public static final String MESSAGE_SUCCESS = "New lesson added: %1$s";
    public static final String MESSAGE_DUPLICATE_LESSON = "This lesson already exists in the classroom";

    private final Lesson toAdd;

    public AddLessonCommand(Lesson lesson) {
        requireNonNull(lesson);
        toAdd = lesson;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasLesson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_LESSON);
        }

        model.addLesson(toAdd);
        Scheduler scheduler = new Scheduler(toAdd);
        scheduler.scheduleLesson();
        model.saveState();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddLessonCommand // instanceof handles nulls
                && toAdd.equals(((AddLessonCommand) other).toAdd));
    }
}
