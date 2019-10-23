package seedu.address.logic.commands.storage;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.semester.SemesterName;
import seedu.address.model.studyplan.StudyPlan;

/**
 * Deletes all modules inside the specified semester in the current active study plan.
 */
public class DeleteSemesterCommand extends Command {

    public static final String COMMAND_WORD = "removesem";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes all modules inside the specified semester in the current active study plan.\n"
            + "Parameters: SEMESTER_NAME\n"
            + "Example: " + COMMAND_WORD + " y1s2";

    public static final String MESSAGE_DELETE_SEMESTER_SUCCESS = "Deleted Semester: %1$s";
    public static final String MESSAGE_NO_ACTIVE_STUDYPLAN = "You don't have any study plan currently. Create now!";

    private final SemesterName semesterName;

    public DeleteSemesterCommand(SemesterName semesterName) {
        requireNonNull(semesterName);
        this.semesterName = semesterName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        StudyPlan activeStudyPlan = model.getActiveStudyPlan();

        if (activeStudyPlan == null) {
            return new CommandResult(MESSAGE_NO_ACTIVE_STUDYPLAN);
        }

        model.deleteAllModulesInSemester(semesterName);

        return new CommandResult(String.format(MESSAGE_DELETE_SEMESTER_SUCCESS, semesterName.toString()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteSemesterCommand // instanceof handles nulls
                && semesterName.equals(((DeleteSemesterCommand) other).semesterName)); // state check
    }
}
