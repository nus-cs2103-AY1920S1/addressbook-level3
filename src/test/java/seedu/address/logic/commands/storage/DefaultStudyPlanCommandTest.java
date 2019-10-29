package seedu.address.logic.commands.storage;

import static seedu.address.testutil.TypicalModulesInfo.getTypicalModulesInfo;
import static seedu.address.testutil.TypicalStudyPlans.getTypicalModulePlanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;


/**
 * Contains integration tests (interaction with the Model) for {@code DefaultStudyPlanCommand}.
 */
public class DefaultStudyPlanCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalModulePlanner(), new UserPrefs(), getTypicalModulesInfo());
    }

    @Test
    public void execute_commitStudyPlan_success() {
        /*
        // TODO: add a full modulesinfo
        StudyPlan defaultStudyPlan = DefaultStudyPlanUtil.getDefaultStudyPlan(getTypicalModulesInfo());

        Model expectedModel = new ModelManager(model.getModulePlanner(), new UserPrefs(), getTypicalModulesInfo());
        expectedModel.addStudyPlan(defaultStudyPlan);
        expectedModel.activateStudyPlan(defaultStudyPlan.getIndex());

        DefaultStudyPlanCommand command = new DefaultStudyPlanCommand();
        CommandResult expectedResult =
                new CommandResult(DefaultStudyPlanCommand.MESSAGE_SUCCESS, true, false);
        assertCommandSuccess(command, model, expectedResult, expectedModel);
         */
    }

}
