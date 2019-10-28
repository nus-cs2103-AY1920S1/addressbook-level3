package seedu.address.logic.commands.storage;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalModulesInfo.getTypicalModulesInfo;
import static seedu.address.testutil.TypicalStudyPlans.getTypicalModulePlanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
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
        model = new ModelManager(getTypicalModulePlanner(), new UserPrefs(), getTypicalModulesInfo());
    }

    @Test
    public void execute_newStudyPlan_success() {
        StudyPlan validStudyPlan = new StudyPlanBuilder().build();

        Model expectedModel = new ModelManager(model.getModulePlanner(), new UserPrefs(), getTypicalModulesInfo());
        expectedModel.addStudyPlan(validStudyPlan);

        CreateStudyPlanCommand command = new CreateStudyPlanCommand(validStudyPlan.getTitle().toString());
        CommandResult expectedResult = new CommandResult(String.format(CreateStudyPlanCommand.MESSAGE_SUCCESS,
                validStudyPlan.getTitle().toString()), true, false);
        assertCommandSuccess(command, model, expectedResult, expectedModel);
    }

}
