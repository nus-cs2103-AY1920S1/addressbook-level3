package seedu.tarence.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.tarence.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_TUTORIAL_NAME;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_TUTORIAL_WEEKS;

import java.util.List;

import seedu.tarence.commons.core.Messages;
import seedu.tarence.logic.commands.exceptions.CommandException;
import seedu.tarence.model.Model;
import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.person.Name;
import seedu.tarence.model.student.Student;
import seedu.tarence.model.tutorial.TutName;
import seedu.tarence.model.tutorial.Tutorial;
import seedu.tarence.model.tutorial.Week;
import seedu.tarence.model.tutorial.exceptions.WeekNotFoundException;

/**
 * Marks attendance of student in a specified tutorial.
 * Keyword matching is case insensitive.
 */
public class MarkAttendanceCommand extends Command {

    public static final String MESSAGE_MARK_ATTENDANCE_SUCCESS = "Attendance of %1$s marked as %2$s";

    public static final String COMMAND_WORD = "mark";
    private static final String[] COMMAND_SYNONYMS = {COMMAND_WORD.toLowerCase()};

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

    private final ModCode targetModCode;
    private final TutName targetTutName;
    private final Week week;
    private final Name targetStudName;

    public MarkAttendanceCommand(ModCode modCode, TutName tutName, Week week, Name studName) {
        requireAllNonNull(modCode, tutName, week, studName);
        this.targetModCode = modCode;
        this.targetTutName = tutName;
        this.targetStudName = studName;
        this.week = week;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Tutorial> lastShownList = model.getFilteredTutorialList();

        // TODO: Multiple tutorials, students error?
        Tutorial targetTutorial = lastShownList.stream()
                .filter(tut -> tut.getTutName().equals(targetTutName) && tut.getModCode().equals(targetModCode))
                .findFirst()
                .orElse(null);
        if (targetTutorial == null) {
            throw new CommandException(Messages.MESSAGE_INVALID_TUTORIAL_IN_MODULE);
        }

        Student targetStudent = targetTutorial.getStudents().stream()
            .filter(student -> student.getName().equals(targetStudName))
            .findFirst()
            .orElse(null);

        if (targetStudent == null) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_IN_TUTORIAL);
        }

        try {
            model.setAttendance(targetTutorial, week, targetStudent);
        } catch (WeekNotFoundException e) {
            throw new CommandException(Messages.MESSAGE_INVALID_WEEK_IN_TUTORIAL);
        }

        String isPresent = targetTutorial.getAttendance().isPresent(week, targetStudent) ? "present" : "absent";
        return new CommandResult(String.format(MESSAGE_MARK_ATTENDANCE_SUCCESS,
                targetStudName, isPresent));
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
