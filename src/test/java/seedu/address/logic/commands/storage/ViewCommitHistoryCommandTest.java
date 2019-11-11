package seedu.address.logic.commands.storage;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalModulesInfo.getTypicalModulesInfo;
import static seedu.address.testutil.TypicalStudyPlans.getTypicalModulePlanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ModulePlanner;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.ModulePlannerBuilder;


/**
 * Contains integration tests (interaction with the Model) for {@code ViewCommitHistoryCommand}.
 */
public class ViewCommitHistoryCommandTest {

    private static final String COMMIT_MESSAGE = "commit message";

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalModulePlanner(), new UserPrefs(), getTypicalModulesInfo());
        model.commitActiveStudyPlan(COMMIT_MESSAGE);
    }

    @Test
    public void execute_viewValidCommit_success() {
        Model expectedModel = new ModelManager(getTypicalModulePlanner(), new UserPrefs(), getTypicalModulesInfo());
        expectedModel.commitActiveStudyPlan(COMMIT_MESSAGE);

        int expectedActiveStudyPlanIndex = expectedModel.getActiveStudyPlan().getIndex();
        String expectedCommitHistoryText =
                expectedModel.getCommitListByStudyPlanIndex(expectedActiveStudyPlanIndex).toString();
        ViewCommitHistoryCommand command = new ViewCommitHistoryCommand();
        CommandResult expectedResult =
                new CommandResult(ViewCommitHistoryCommand.MESSAGE_SUCCESS + expectedCommitHistoryText);
        assertCommandSuccess(command, model, expectedResult, expectedModel);
    }

    @Test
    public void execute_noActiveStudyPlan_throwsCommandException() {
        ModulePlanner modulePlannerWithNoActivePlan = new ModulePlannerBuilder().build();
        Model modelWithNoActivePlan =
                new ModelManager(modulePlannerWithNoActivePlan, new UserPrefs(), getTypicalModulesInfo());
        ViewCommitHistoryCommand command = new ViewCommitHistoryCommand();
        assertThrows(CommandException.class, () -> command.execute(modelWithNoActivePlan));
    }

    @Test
    public void execute_noCommitHistory_throwsCommandException() {
        Model noHistoryModel = new ModelManager(getTypicalModulePlanner(), new UserPrefs(), getTypicalModulesInfo());
        ViewCommitHistoryCommand command = new ViewCommitHistoryCommand();
        assertThrows(CommandException.class, () -> command.execute(noHistoryModel));
    }

    @Test
    public void equals() {
        ViewCommitHistoryCommand command1 = new ViewCommitHistoryCommand();
        ViewCommitHistoryCommand command2 = new ViewCommitHistoryCommand();

        // same object -> returns true
        assertTrue(command1.equals(command1));

        // same command -> returns true
        assertTrue(command1.equals(command2));

        // different types -> returns false
        assertFalse(command1.equals(1));

        // null -> returns false
        assertFalse(command1.equals(null));
    }

}
