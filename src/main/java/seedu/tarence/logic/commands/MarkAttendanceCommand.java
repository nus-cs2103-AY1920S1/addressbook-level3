package seedu.tarence.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_TUTORIAL_NAME;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_TUTORIAL_WEEKS;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.tarence.commons.core.Messages;
import seedu.tarence.commons.core.index.Index;
import seedu.tarence.logic.commands.exceptions.CommandException;
import seedu.tarence.model.Model;
import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.person.Name;
import seedu.tarence.model.student.Student;
import seedu.tarence.model.tutorial.TutName;
import seedu.tarence.model.tutorial.Tutorial;
import seedu.tarence.model.tutorial.Week;

/**
 * Marks attendance of student in a specified tutorial.
 * Keyword matching is case insensitive.
 */
public class MarkAttendanceCommand extends Command {

    public static final String MESSAGE_MARK_ATTENDANCE_SUCCESS = "Attendance of %1$s marked as %2$s";
    public static final String MESSAGE_CONFIRM_MARK_ATTENDANCE_OF_STUDENT = "Do you want to mark "
            + "%1$s's attendance?\n"
            + "(y/n)";
    public static final String MESSAGE_MARK_ATTENDANCE_TUTORIAL = "Marking attendance of %1$s";

    public static final String COMMAND_WORD = "mark";
    private static final String[] COMMAND_SYNONYMS = {COMMAND_WORD.toLowerCase()};

    // TODO: Update message to include index format
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks the attendance of a student in a tutorial.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_TUTORIAL_NAME + "TUTORIAL_NAME "
            + PREFIX_MODULE + "MODULE_CODE "
            + PREFIX_TUTORIAL_WEEKS + "WEEK\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_TUTORIAL_NAME + "Lab 1 "
            + PREFIX_MODULE + "CS1010 "
            + PREFIX_TUTORIAL_WEEKS + "5\n";

    private final Optional<ModCode> targetModCode;
    private final Optional<TutName> targetTutName;
    private final Optional<Index> targetIndex;
    private final Week week;
    private final Optional<Name> targetStudName;

    public MarkAttendanceCommand(ModCode modCode, TutName tutName, Index index, Week week, Name studName) {
        this.targetModCode = Optional.ofNullable(modCode);
        this.targetTutName = Optional.ofNullable(tutName);
        this.targetIndex = Optional.ofNullable(index);
        this.targetStudName = Optional.ofNullable(studName);
        this.week = week;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Tutorial> lastShownList = model.getFilteredTutorialList();

        // TODO: Consider cases with multiple matching tutorials, students?
        Tutorial targetTutorial = null;
        if (targetModCode.isPresent() && targetTutName.isPresent()) {
            // format with modcode and tutname
            targetTutorial = lastShownList.stream()
                    .filter(tut -> tut.getTutName().equals(targetTutName.get())
                    && tut.getModCode().equals(targetModCode.get()))
                    .findFirst()
                    .orElse(null);
            if (targetTutorial == null) {
                throw new CommandException(Messages.MESSAGE_INVALID_TUTORIAL_IN_MODULE);
            }
        } else if (targetIndex.isPresent()) {
            // format with tutorial index
            try {
                targetTutorial = lastShownList.get(targetIndex.get().getZeroBased());
            } catch (IndexOutOfBoundsException e) {
                throw new CommandException(
                    String.format(Messages.MESSAGE_INVALID_TUTORIAL_DISPLAYED_INDEX));
            }
        }

        Set<Week> weeks = targetTutorial.getTimeTable().getWeeks();
        if (!weeks.contains(week)) {
            throw new CommandException(Messages.MESSAGE_INVALID_WEEK_IN_TUTORIAL);
        }

        if (targetStudName.isEmpty()) {
            // stores the chain of commands to mark attendance of a class if targetStudName is not specified
            List<Student> students = targetTutorial.getStudents();
            for (int i = students.size() - 1; i >= 0; i--) {
                model.storePendingCommand(
                        new MarkAttendanceVerifiedCommand(targetTutorial, week, students.get(i)));
                model.storePendingCommand(
                        new DisplayCommand(
                        String.format(MESSAGE_CONFIRM_MARK_ATTENDANCE_OF_STUDENT, students.get(i).getName())));
            }

            return new CommandResult(
                    String.format(MESSAGE_MARK_ATTENDANCE_TUTORIAL, targetTutorial.getTutName()));
        }

        // otherwise marks attendance of individual student
        Student targetStudent = targetTutorial.getStudents().stream()
            .filter(student -> student.getName().equals(targetStudName.get()))
            .findFirst()
            .orElse(null);

        if (targetStudent == null) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_IN_TUTORIAL);
        }

        model.setAttendance(targetTutorial, week, targetStudent);

        String isPresent = targetTutorial.getAttendance().isPresent(week, targetStudent) ? "present" : "absent";
        return new CommandResult(String.format(MESSAGE_MARK_ATTENDANCE_SUCCESS,
                targetStudent.getName(), isPresent), targetTutorial);
    }

    @Override
    public boolean needsInput() {
        return false;
    }

    @Override
    public boolean needsCommand(Command command) {
        return false;
    }

    /**
     * Returns true if user command matches command word or any defined synonyms, and false otherwise.
     *
     * @param userCommand command word from user.
     * @return whether user command matches specified command word or synonyms.
     */
    public static boolean isMatchingCommandWord(String userCommand) {
        for (String synonym : COMMAND_SYNONYMS) {
            if (synonym.equals(userCommand.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MarkAttendanceCommand // instanceof handles nulls
                && targetModCode.equals(((MarkAttendanceCommand) other).targetModCode)
                && week.equals(((MarkAttendanceCommand) other).week)
                && targetTutName.equals(((MarkAttendanceCommand) other).targetTutName)
                && targetStudName.equals(((MarkAttendanceCommand) other).targetStudName)); // state check
    }
}
