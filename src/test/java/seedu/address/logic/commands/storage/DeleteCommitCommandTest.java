package seedu.address.logic.commands.storage;

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
 * Contains integration tests (interaction with the Model) for {@code DeleteCommitCommand}.
 */
public class DeleteCommitCommandTest {

    private static final String COMMIT_MESSAGE = "this is a commit message.";
    private static final int VALID_COMMIT_INDEX = 0;
    private static final int INVALID_COMMIT_INDEX = 100;

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
    public void execute_deleteCommit_success() {
        int studyPlanIndex = validStudyPlan.getIndex();
        int commitNumber = VALID_COMMIT_INDEX;

        Model expectedModel = new ModelManager(getTypicalModulePlanner(), new UserPrefs(), getTypicalModulesInfo());
        expectedModel.addStudyPlan(validStudyPlan);
        expectedModel.activateStudyPlan(studyPlanIndex);
        expectedModel.commitActiveStudyPlan(COMMIT_MESSAGE);
        expectedModel.deleteCommit(studyPlanIndex, commitNumber);

        DeleteCommitCommand command = new DeleteCommitCommand(studyPlanIndex, commitNumber);
        CommandResult expectedResult =
                new CommandResult(String.format(DeleteCommitCommand.MESSAGE_SUCCESS,
                        studyPlanIndex + "." + commitNumber));
        assertCommandSuccess(command, model, expectedResult, expectedModel);
    }

    @Test
    public void execute_deleteInvalidCommit_throwsCommandException() {
        int studyPlanIndex = validStudyPlan.getIndex();

        DeleteCommitCommand command = new DeleteCommitCommand(studyPlanIndex, INVALID_COMMIT_INDEX);
        assertThrows(CommandException.class, () -> command.execute(model));
    }

}
