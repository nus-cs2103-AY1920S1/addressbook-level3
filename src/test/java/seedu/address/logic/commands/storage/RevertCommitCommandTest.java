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
import seedu.address.model.UserPrefs;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.testutil.StudyPlanBuilder;


/**
 * Contains integration tests (interaction with the Model) for {@code RevertCommitCommand}.
 */
public class RevertCommitCommandTest {

    private static final String COMMIT_MESSAGE = "this is a commit message.";
    private static final int INVALID_COMMIT_NUMBER = 100;

    private Model model;
    private StudyPlan validStudyPlan = new StudyPlanBuilder().build();

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalModulePlanner(), new UserPrefs(), getTypicalModulesInfo());
        model.addStudyPlan(validStudyPlan);
        model.activateStudyPlan(validStudyPlan.getIndex());
        model.commitActiveStudyPlan(COMMIT_MESSAGE);
    }

    @Test
    public void execute_revertValidCommit_success() {
        int studyPlanIndex = validStudyPlan.getIndex();
        int commitNumber = 0;

        Model expectedModel = new ModelManager(getTypicalModulePlanner(), new UserPrefs(), getTypicalModulesInfo());
        expectedModel.addStudyPlan(validStudyPlan);
        expectedModel.activateStudyPlan(studyPlanIndex);
        expectedModel.commitActiveStudyPlan(COMMIT_MESSAGE);
        expectedModel.revertToCommit(studyPlanIndex, commitNumber);

        RevertCommitCommand command = new RevertCommitCommand(studyPlanIndex, commitNumber);
        CommandResult expectedResult =
                new CommandResult(RevertCommitCommand.MESSAGE_SUCCESS, true, false);
        assertCommandSuccess(command, model, expectedResult, expectedModel);
    }

    @Test
    public void execute_revertInvalidCommit_throwsCommandException() {
        int studyPlanIndex = validStudyPlan.getIndex();

        RevertCommitCommand command = new RevertCommitCommand(studyPlanIndex, INVALID_COMMIT_NUMBER);
        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    public void equals() {
        RevertCommitCommand command1 = new RevertCommitCommand(1, 2);
        RevertCommitCommand command2 = new RevertCommitCommand(3, 4);

        // same object -> returns true
        assertTrue(command1.equals(command1));

        // same values -> returns true
        RevertCommitCommand command3 = new RevertCommitCommand(1, 2);
        assertTrue(command1.equals(command3));

        // different types -> returns false
        assertFalse(command1.equals(1));

        // null -> returns false
        assertFalse(command1.equals(null));

        // different indices -> returns false
        assertFalse(command1.equals(command2));
    }

}
