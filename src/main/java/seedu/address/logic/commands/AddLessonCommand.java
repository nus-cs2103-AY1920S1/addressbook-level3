package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LESSONNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REPEAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTTIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENDTIME;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.scheduler.Scheduler;

/**
 * Adds a lesson to the address book.
 */
public class AddLessonCommand extends Command {

    public static final String COMMAND_WORD = "addlesson";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a lesson to the address book. "
            + "Parameters: "
            + PREFIX_LESSONNAME + "NAME "
            + PREFIX_STARTTIME + "START DATE "
            + PREFIX_ENDTIME + "END DATE "
            + "[" + PREFIX_REPEAT + "repeat] "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_LESSONNAME + "Math 4E7 "
            + PREFIX_STARTTIME + "14/07/2020 1200 "
            + PREFIX_ENDTIME + "14/07/2020 1400"
            + PREFIX_REPEAT + "repeat";

    public static final String MESSAGE_SUCCESS = "New lesson added: %1$s";
    public static final String MESSAGE_DUPLICATE_LESSON = "This lesson already exists in the address book";

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
