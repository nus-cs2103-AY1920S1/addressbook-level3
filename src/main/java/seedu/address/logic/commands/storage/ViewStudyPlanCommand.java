package seedu.address.logic.commands.storage;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_NO_STUDY_PLAN;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.semester.Semester;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.model.studyplan.exceptions.StudyPlanNotFoundException;
import seedu.address.ui.ResultViewType;

/**
 * Represents a command for the user to view the simplified version of the study plan with
 * the given index in the left panel of GUI. This does not activate the study plan and does not allow
 * the user to modify the study plan under view.
 */
public class ViewStudyPlanCommand extends Command {

    public static final String COMMAND_WORD = "viewplan";

    public static final String HELP_MESSAGE = COMMAND_WORD + ": Viewing another study plan";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Views the simplified study plan identified by the unique ID of the target study plan.\n"
            + "Parameters: PLAN_ID (must be a positive integer)\n";
    public static final String MESSAGE_SUCCESS = "Here is a simple view of your study plan"
            + "[ID: %1$d, Title: %2$s]";
    public static final String MESSAGE_INVALID_STUDY_PLAN_ID = "The study plan ID you have entered is invalid!";

    private int studyPlanIndex;

    public ViewStudyPlanCommand(int studyPlanIndex) {
        this.studyPlanIndex = studyPlanIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        StudyPlan activeStudyPlan = model.getActiveStudyPlan();
        if (activeStudyPlan == null) {
            throw new CommandException(MESSAGE_NO_STUDY_PLAN);
        }

        try {
            StudyPlan studyPlan = model.getStudyPlan(studyPlanIndex);
            ObservableList<Semester> semesters = studyPlan.getSemesters().asUnmodifiableObservableList();
            return new CommandResult<>(String.format(MESSAGE_SUCCESS, studyPlanIndex, studyPlan.getTitle().toString()),
                    ResultViewType.SEMESTER, semesters);
        } catch (StudyPlanNotFoundException e) {
            throw new CommandException(MESSAGE_INVALID_STUDY_PLAN_ID);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewStudyPlanCommand // instanceof handles nulls
                && studyPlanIndex == ((ViewStudyPlanCommand) other).studyPlanIndex);
    }

}
