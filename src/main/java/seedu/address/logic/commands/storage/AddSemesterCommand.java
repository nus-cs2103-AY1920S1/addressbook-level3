package seedu.address.logic.commands.storage;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.semester.Semester;
import seedu.address.model.semester.SemesterName;
import seedu.address.model.semester.UniqueSemesterList;
import seedu.address.model.semester.exceptions.DuplicateSemesterException;
import seedu.address.model.studyplan.StudyPlan;

/**
 * Creates a new studyPlan.
 */
public class AddSemesterCommand extends Command {

    public static final String COMMAND_WORD = "addsem";
    public static final String HELP_MESSAGE = COMMAND_WORD + ": Adding a new semester";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a special semester or Year 5 semester. \n"
            + "A special semester follows the format Y[1-5]ST[1-2] or Y5S1 or Y5S2 \n"
            + "Parameters: "
            + "SPECIAL_SEMESTER_NAME \n"
            + "Example: " + COMMAND_WORD + " "
            + "Y2ST1";

    public static final String MESSAGE_SUCCESS = "New semester added: %1$s";
    public static final String MESSAGE_DUPLICATE_SEMESTER = "This semester already exists in the module planner";
    public static final String MESSAGE_NO_ACTIVE_STUDYPLAN = "You have no active study plan now.";

    private final SemesterName semesterName;

    /**
     * Creates an AddSemesterCommand to add the specified {@code StudyPlan}.
     */
    public AddSemesterCommand(SemesterName semesterName) {
        this.semesterName = semesterName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        StudyPlan activeStudyPlan = model.getActiveStudyPlan();
        if (activeStudyPlan == null) {
            throw new CommandException(MESSAGE_NO_ACTIVE_STUDYPLAN);
        }

        try {
            Semester semesterToAdd = new Semester(semesterName);
            UniqueSemesterList semesterList = activeStudyPlan.getSemesters();
            semesterList.add(semesterToAdd);
            semesterList.sortBySemesterName();
            model.addToHistory();
            return new CommandResult(String.format(MESSAGE_SUCCESS, semesterName));
        } catch (DuplicateSemesterException e) {
            throw new CommandException(MESSAGE_DUPLICATE_SEMESTER);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddSemesterCommand // instanceof handles nulls
                && semesterName.equals(((AddSemesterCommand) other).semesterName));
    }
}
