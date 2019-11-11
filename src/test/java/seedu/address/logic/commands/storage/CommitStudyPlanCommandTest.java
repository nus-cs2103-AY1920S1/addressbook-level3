package seedu.address.logic.commands.storage;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

    private static final String COMMIT_MESSAGE_1 = "this is a commit message.";
    private static final String COMMIT_MESSAGE_2 = "this is also a commit message.";

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalModulePlanner(), new UserPrefs(), getTypicalModulesInfo());
    }

    @Test
    public void execute_commitStudyPlan_success() {
        Model expectedModel = new ModelManager(model.getModulePlanner(), new UserPrefs(), getTypicalModulesInfo());
        expectedModel.activateStudyPlan(SP_1.getIndex());
        expectedModel.commitActiveStudyPlan(COMMIT_MESSAGE_1);

        CommitStudyPlanCommand command = new CommitStudyPlanCommand(COMMIT_MESSAGE_1);
        CommandResult expectedResult =
                new CommandResult(String.format(CommitStudyPlanCommand.MESSAGE_SUCCESS, COMMIT_MESSAGE_1));
        assertCommandSuccess(command, model, expectedResult, expectedModel);
    }

    @Test
    public void equals() {
        CommitStudyPlanCommand command1 = new CommitStudyPlanCommand(COMMIT_MESSAGE_1);
        CommitStudyPlanCommand command2 = new CommitStudyPlanCommand(COMMIT_MESSAGE_2);

        // same object -> returns true
        assertTrue(command1.equals(command1));

        // same values -> returns true
        CommitStudyPlanCommand command3 = new CommitStudyPlanCommand(COMMIT_MESSAGE_1);
        assertTrue(command1.equals(command3));

        // different types -> returns false
        assertFalse(command1.equals(1));

        // null -> returns false
        assertFalse(command1.equals(null));

        // different commit messages -> returns false
        assertFalse(command1.equals(command2));
    }

}
