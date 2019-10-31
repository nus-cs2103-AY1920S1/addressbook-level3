package seedu.address.logic.commands.storage;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
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
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Views the simplified study plan identified by the index used in the displayed study plan list.\n"
            + "Parameters: PLAN_INDEX (must be a positive integer)\n";
    public static final String MESSAGE_SUCCESS = "Here is a simple view of your study plan"
            + "[index: %1$d, title: %2$s]";
    public static final String MESSAGE_INVALID_STUDYPLAN_INDEX = "The study plan index you have entered is invalid!";

    private int studyPlanIndex;

    public ViewStudyPlanCommand(int studyPlanIndex) {
        this.studyPlanIndex = studyPlanIndex;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        StudyPlan activeStudyPlan = model.getActiveStudyPlan();
        if (activeStudyPlan == null) {
            return new CommandResult(MESSAGE_INVALID_STUDYPLAN_INDEX);
        }

        try {
            StudyPlan studyPlan = model.getStudyPlan(studyPlanIndex);
            ObservableList<Semester> semesters = studyPlan.getSemesters().asUnmodifiableObservableList();
            return new CommandResult<>(String.format(MESSAGE_SUCCESS, studyPlanIndex, studyPlan.getTitle().toString()),
                    ResultViewType.SEMESTER, semesters);
        } catch (StudyPlanNotFoundException e) {
            return new CommandResult(MESSAGE_INVALID_STUDYPLAN_INDEX);
        }
    }

}
