package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.AddLessonCommand.MESSAGE_INVALID_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENDTIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LESSONNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REPEAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTTIME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_LESSONS;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.lesson.ClassName;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.Time;
import seedu.address.model.lesson.UniqueLessonList;

/**
 * Edits the details of an existing lesson in the notebook.
 */
public class EditLessonCommand extends Command {

    public static final String COMMAND_WORD = "editlesson";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the lesson identified "
            + "by the index number used in the displayed lesson list in each day tab. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: LESSON_INDEX (must be a positive integer) "
            + PREFIX_DAY + "DAY_OF_THE_WEEK (Monday: 1, Tuesday: 2 etc) "
            + "[" + PREFIX_LESSONNAME + "LESSON_NAME] "
            + "[" + PREFIX_STARTTIME + "START TIME] "
            + "[" + PREFIX_ENDTIME + "END TIME] "
            + "[" + PREFIX_REPEAT + "REPEAT] "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DAY + "3 "
            + PREFIX_LESSONNAME + "Math 3E7 "
            + PREFIX_STARTTIME + "14/10/2019 1200";


    public static final String MESSAGE_EDIT_LESSON_SUCCESS = "Edited Lesson: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_LESSON = "This lesson already exists in the classroom.";

    private final Index index;
    private final EditLessonDescriptor editLessonDescriptor;
    private final Index day;

    /**
     * @param index of the lesson in the filtered lesson list to edit
     * @param editLessonDescriptor details to edit the lesson with
     * @param day
     */
    public EditLessonCommand(Index index, EditLessonDescriptor editLessonDescriptor, Index day) {
        requireNonNull(index);
        requireNonNull(editLessonDescriptor);

        this.index = index;
        this.editLessonDescriptor = new EditLessonDescriptor(editLessonDescriptor);
        this.day = day;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<UniqueLessonList> lastShownList = model.getFilteredLessonWeekList();
        UniqueLessonList dayList = lastShownList.get(day.getZeroBased());

        if (index.getZeroBased() >= dayList.asUnmodifiableObservableList().size()
            || day.getZeroBased() < 0 || day.getZeroBased() > 7) {
            throw new CommandException(Messages.MESSAGE_INVALID_LESSON_DISPLAYED_INDEX);
        }

        Lesson lessonToEdit = dayList.asUnmodifiableObservableList().get(index.getZeroBased());
        Lesson editedLesson = createEditedLesson(lessonToEdit, editLessonDescriptor);

        if (!lessonToEdit.isSameLesson(editedLesson) && model.hasLesson(editedLesson)) {
            throw new CommandException(MESSAGE_DUPLICATE_LESSON);
        } else if (!editedLesson.endTimeIsAfterStartTime()) {
            throw new CommandException(MESSAGE_INVALID_END_TIME);
        }

        model.setLesson(lessonToEdit, editedLesson);
        model.updateFilteredLessonList(PREDICATE_SHOW_ALL_LESSONS);
        model.saveState();
        return new CommandResult(String.format(MESSAGE_EDIT_LESSON_SUCCESS, editedLesson));
    }

    /**
     * Creates and returns a {@code Lesson} with the details of {@code lessonToEdit}
     * edited with {@code editLessonDescriptor}.
     */
    private static Lesson createEditedLesson(Lesson lessonToEdit, EditLessonDescriptor editLessonDescriptor) {
        assert lessonToEdit != null;

        ClassName updatedLessonName = editLessonDescriptor.getName().orElse(lessonToEdit.getName());
        Time startTime = editLessonDescriptor.getStartTime().orElse(lessonToEdit.getStartTime());
        Time endtime = editLessonDescriptor.getEndTime().orElse(lessonToEdit.getEndTime());


        return new Lesson(startTime, endtime, updatedLessonName);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditStudentCommand)) {
            return false;
        }

        // state check
        EditLessonCommand e = (EditLessonCommand) other;
        return index.equals(e.index)
                && editLessonDescriptor.equals(e.editLessonDescriptor);
    }

    /**
     * Stores the details to edit the lesson with. Each non-empty field value will replace the
     * corresponding field value of the lesson.
     */
    public static class EditLessonDescriptor {
        private ClassName name;
        private Time startTime;
        private Time endTime;

        public EditLessonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditLessonDescriptor(EditLessonDescriptor toCopy) {
            setName(toCopy.name);
            setStartTime(toCopy.startTime);
            setEndTime(toCopy.endTime);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, startTime, endTime);
        }

        public void setName(ClassName name) {
            this.name = name;
        }

        public Optional<ClassName> getName() {
            return Optional.ofNullable(name);
        }

        public void setStartTime(Time startTime) {
            this.startTime = startTime;
        }

        public Optional<Time> getStartTime() {
            return Optional.ofNullable(startTime);
        }

        public void setEndTime(Time endTime) {
            this.endTime = endTime;
        }

        public Optional<Time> getEndTime() {
            return Optional.ofNullable(endTime);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditLessonDescriptor)) {
                return false;
            }

            // state check
            EditLessonDescriptor e = (EditLessonDescriptor) other;

            return getName().equals(e.getName())
                    && getStartTime().equals(e.getStartTime())
                    && getEndTime().equals(e.getEndTime());
        }
    }
}
