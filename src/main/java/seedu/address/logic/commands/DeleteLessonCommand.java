package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.UniqueLessonList;

/**
 * Deletes a lesson identified using it's displayed index from the notebook.
 */
public class DeleteLessonCommand extends Command {
    public static final String COMMAND_WORD = "deletelesson";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes the lesson identified "
            + "by the index number used in the displayed lesson list in each day tab.\n"
            + "Parameters: LESSON_INDEX (must be a positive integer) "
            + PREFIX_DAY + "DAY_OF_THE_WEEK (Monday: 1, Tuesday: 2 etc) "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DAY + "3 ";

    public static final String MESSAGE_DELETE_LESSON_SUCCESS = "Deleted Lesson: %1$s";

    private final Index targetIndex;
    private final Index day;

    public DeleteLessonCommand(Index targetIndex, Index day) {
        this.targetIndex = targetIndex;
        this.day = day;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<UniqueLessonList> lastShownList = model.getFilteredLessonWeekList();
        UniqueLessonList dayList = lastShownList.get(day.getZeroBased());


        if (targetIndex.getZeroBased() >= dayList.asUnmodifiableObservableList().size()
                || day.getZeroBased() < 0 || day.getZeroBased() > 7) {
            throw new CommandException(Messages.MESSAGE_INVALID_LESSON_DISPLAYED_INDEX);
        }

        Lesson lessonToDelete = dayList.asUnmodifiableObservableList().get(targetIndex.getZeroBased());
        model.deleteLesson(lessonToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_LESSON_SUCCESS, lessonToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteLessonCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteLessonCommand) other).targetIndex)); // state check
    }
}
