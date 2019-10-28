package seedu.address.logic.commands.storage;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalModulesInfo.getTypicalModulesInfo;
import static seedu.address.testutil.TypicalStudyPlans.SP_1;
import static seedu.address.testutil.TypicalStudyPlans.getTypicalModulePlanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;


/**
 * Contains integration tests (interaction with the Model) for {@code CommitStudyPlanCommand}.
 */
public class CommitStudyPlanCommandTest {

    private static final String COMMIT_MESSAGE = "this is a commit message.";
    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalModulePlanner(), new UserPrefs(), getTypicalModulesInfo());
    }

    @Test
    public void execute_commitStudyPlan_success() {
        Model expectedModel = new ModelManager(model.getModulePlanner(), new UserPrefs(), getTypicalModulesInfo());
        expectedModel.activateStudyPlan(SP_1.getIndex());
        expectedModel.commitActiveStudyPlan(COMMIT_MESSAGE);

        CommitStudyPlanCommand command = new CommitStudyPlanCommand(COMMIT_MESSAGE);
        CommandResult expectedResult =
                new CommandResult(String.format(CommitStudyPlanCommand.MESSAGE_SUCCESS, COMMIT_MESSAGE));
        assertCommandSuccess(command, model, expectedResult, expectedModel);
    }

}
