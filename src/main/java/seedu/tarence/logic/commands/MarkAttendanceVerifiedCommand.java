package seedu.tarence.logic.commands;

import seedu.tarence.logic.commands.exceptions.CommandException;
import seedu.tarence.model.Model;
import seedu.tarence.model.student.Student;
import seedu.tarence.model.tutorial.Tutorial;
import seedu.tarence.model.tutorial.Week;
import seedu.tarence.storage.Storage;

/**
 * Represents a followup to {@code MarkAttendanceCommand} where the {@code Student} to be marked has been verified as
 * a valid one in the application.
 */
public class MarkAttendanceVerifiedCommand extends Command {

    private final Tutorial targetTutorial;
    private final Week week;
    private final Student targetStudent;

    MarkAttendanceVerifiedCommand(Tutorial targetTutorial, Week week, Student targetStudent) {
        this.targetTutorial = targetTutorial;
        this.week = week;
        this.targetStudent = targetStudent;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        boolean isPresent;
        targetTutorial.setAttendance(week, targetStudent);
        isPresent = targetTutorial.getAttendance().isPresent(week, targetStudent);

        return new CommandResult(
                String.format(MarkAttendanceCommand.MESSAGE_MARK_ATTENDANCE_SUCCESS,
                targetStudent.getName(),
                isPresent ? "present" : "absent"), targetTutorial);
    }

    @Override
    public CommandResult execute(Model model, Storage storage) throws CommandException {
        return execute(model);
    }

    @Override
    public boolean needsInput() {
        return true;
    }

    @Override
    public boolean needsCommand(Command command) {
        return command instanceof ConfirmYesCommand
                || command instanceof ConfirmNoCommand
                || command instanceof DisplayCommand;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof MarkAttendanceVerifiedCommand)) {
            return false;
        }

        return (o == this)
                || ((MarkAttendanceVerifiedCommand) o).targetTutorial.equals(this.targetTutorial)
                    && ((MarkAttendanceVerifiedCommand) o).week.equals(this.week)
                    && ((MarkAttendanceVerifiedCommand) o).targetStudent.equals(this.targetStudent);
    }
}
