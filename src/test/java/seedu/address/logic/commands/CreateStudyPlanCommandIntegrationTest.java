package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalStudyPlans.getTypicalModulePlanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.storage.CreateStudyPlanCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.testutil.StudyPlanBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code CreateStudyPlanCommand}.
 */
public class CreateStudyPlanCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalModulePlanner(), new UserPrefs());
    }

    @Test
    public void execute_newStudyPlan_success() {
        StudyPlan validStudyPlan = new StudyPlanBuilder().build();

        Model expectedModel = new ModelManager(model.getModulePlanner(), new UserPrefs());
        expectedModel.addStudyPlan(validStudyPlan);

        assertCommandSuccess(new CreateStudyPlanCommand(validStudyPlan), model,
                String.format(CreateStudyPlanCommand.MESSAGE_SUCCESS, validStudyPlan), expectedModel);
    }

    @Test
    public void execute_duplicateStudyPlan_throwsCommandException() {
        StudyPlan studyPlanInList = model.getModulePlanner().getStudyPlanList().get(0);
        assertCommandFailure(new CreateStudyPlanCommand(studyPlanInList), model,
                CreateStudyPlanCommand.MESSAGE_DUPLICATE_STUDYPLAN);
    }

}
