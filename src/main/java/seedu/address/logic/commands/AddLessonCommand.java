package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENDTIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LESSONNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REPEAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTTIME;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.lesson.Lesson;

/**
 * Adds a lesson to the classroom.
 */
public class AddLessonCommand extends Command {

    public static final String COMMAND_WORD = "addlesson";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a lesson to the classroom. "
            + "Parameters: "
            + PREFIX_LESSONNAME + "NAME "
            + PREFIX_STARTTIME + "START TIME "
            + PREFIX_ENDTIME + "END TIME "
            + "[" + PREFIX_REPEAT + "repeat] "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_LESSONNAME + "Math 4E7 "
            + PREFIX_STARTTIME + "14/07/2020 1200 "
            + PREFIX_ENDTIME + "14/07/2020 1400"
            + PREFIX_REPEAT + "repeat";

    public static final String MESSAGE_SUCCESS = "New lesson added: %1$s";
    public static final String MESSAGE_DUPLICATE_LESSON = "This lesson already exists in the classroom";
    public static final String MESSAGE_INVALID_END_TIME = "The end time should be after the start time";
    public static final String MESSAGE_INVALID_LESSON_TIME = "Another lesson in the same timeslot already exists";

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
        } else if (!toAdd.endTimeIsAfterStartTime()) {
            throw new CommandException(MESSAGE_INVALID_END_TIME);
        }

        if (model.checkTimingExist(toAdd)) {
            throw new CommandException(MESSAGE_INVALID_LESSON_TIME);
        }

        model.addLesson(toAdd);
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
