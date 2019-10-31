package seedu.address.logic.commands.storage;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalModulesInfo.getTypicalModulesInfo;
import static seedu.address.testutil.TypicalStudyPlans.getTypicalModulePlanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.semester.Semester;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.testutil.StudyPlanBuilder;
import seedu.address.ui.ResultViewType;


/**
 * Contains integration tests (interaction with the Model) for {@code ViewStudyPlanCommand}.
 */
public class ViewStudyPlanCommandTest {

    private Model model;
    private StudyPlan validStudyPlan = new StudyPlanBuilder().build();

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalModulePlanner(), new UserPrefs(), getTypicalModulesInfo());
        model.addStudyPlan(validStudyPlan);
    }

    @Test
    public void execute_revertCommit_success() {
        int studyPlanIndex = validStudyPlan.getIndex();

        Model expectedModel = new ModelManager(getTypicalModulePlanner(), new UserPrefs(), getTypicalModulesInfo());
        expectedModel.addStudyPlan(validStudyPlan);

        ObservableList<Semester> expectedSemesters = validStudyPlan.getSemesters().asUnmodifiableObservableList();
        String expectedText = String.format(ViewStudyPlanCommand.MESSAGE_SUCCESS, studyPlanIndex,
                validStudyPlan.getTitle().toString());
        ViewStudyPlanCommand command = new ViewStudyPlanCommand(studyPlanIndex);
        CommandResult<Semester> expectedResult =
                new CommandResult<>(expectedText, ResultViewType.SEMESTER, expectedSemesters);
        assertCommandSuccess(command, model, expectedResult, expectedModel);
    }

}
