package seedu.tarence.logic.commands;

import java.util.List;

import seedu.tarence.logic.commands.exceptions.CommandException;
import seedu.tarence.model.Model;
import seedu.tarence.model.student.Student;
import seedu.tarence.model.tutorial.Tutorial;
import seedu.tarence.model.tutorial.Week;

/**
 * Represents a followup to {@code MarkAttendanceCommand} where the {@code Student} to be marked has been verified as
 * a valid one in the application.
 */
public class MarkAttendanceVerifiedCommand extends Command {

    private Tutorial targetTutorial;
    private Week week;
    private Student targetStudent;

    MarkAttendanceVerifiedCommand(Tutorial targetTutorial, Week week, Student targetStudent) {
        this.targetTutorial = targetTutorial;
        this.week = week;
        this.targetStudent = targetStudent;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        targetTutorial.setAttendance(week, targetStudent);

        boolean isPresent = targetTutorial.getAttendance().isPresent(week, targetStudent);
        List<Student> students = targetTutorial.getStudents();
        int nextStudentIndex = students.indexOf(targetStudent) + 1;
        if (nextStudentIndex >= students.size()) {
            return new CommandResult(
                String.format(MarkAttendanceCommand.MESSAGE_MARK_ATTENDANCE_SUCCESS,
                targetStudent.getName(),
                isPresent));
        }

        Student nextStudent = targetTutorial.getStudents().get(nextStudentIndex);
        model.storePendingCommand(
                new MarkAttendanceVerifiedCommand(targetTutorial, week, nextStudent));
        return new CommandResult(
                String.format(MarkAttendanceCommand.MESSAGE_MARK_ATTENDANCE_SUCCESS,
                targetStudent.getName(),
                isPresent)
                + "\n"
                + String.format(MarkAttendanceCommand.MESSAGE_CONFIRM_MARK_ATTENDANCE_OF_STUDENT,
                nextStudent.getName()));
    }

    @Override
    public boolean needsInput() {
        return true;
    }

    @Override
    public boolean needsCommand(Command command) {
        return command instanceof ConfirmYesCommand || command instanceof ConfirmNoCommand;
    }
}
