package seedu.address.logic.commands.storage;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_NO_STUDY_PLAN;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.semester.SemesterName;
import seedu.address.model.studyplan.StudyPlan;

/**
 * Deletes all modules inside the specified semester in the current active study plan.
 * If semester is a mainstream semester (i.e. not special terms or Year 5 semesters), the semester itself
 * is not deleted, and only the modules inside is cleared. But if the semester to be deleted is a special
 * semester or a Year 5 semester, then the whole semester will be removed from the study plan.
 */
public class DeleteSemesterCommand extends Command {

    public static final String COMMAND_WORD = "removesem";
    public static final String HELP_MESSAGE = COMMAND_WORD + ": Removing a semester";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes all modules inside the specified semester in the current active study plan.\n"
            + "Parameters: SEMESTER_NAME\n"
            + "Example: " + COMMAND_WORD + " y1s2";

    public static final String MESSAGE_DELETE_MAINSTREAM_SEMESTER_SUCCESS = "Cleared all modules in Semester: %1$s";
    public static final String MESSAGE_DELETE_SPECIAL_SEMESTER_SUCCESS = "Deleted Semester: %1$s";

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
            throw new CommandException(MESSAGE_NO_STUDY_PLAN);
        }

        if (SemesterName.isMainstreamSemester(semesterName.toString())) {
            model.deleteAllModulesInSemester(semesterName);
            model.addToHistory();
            return new CommandResult(String.format(MESSAGE_DELETE_MAINSTREAM_SEMESTER_SUCCESS,
                    semesterName.toString()));
        } else {
            model.deleteSemester(semesterName);
            model.addToHistory();
            return new CommandResult(String.format(MESSAGE_DELETE_SPECIAL_SEMESTER_SUCCESS,
                    semesterName.toString()));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteSemesterCommand // instanceof handles nulls
                && semesterName.equals(((DeleteSemesterCommand) other).semesterName)); // state check
    }
}
