package seedu.address.logic.commands.storage;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalModulesInfo.getTypicalModulesInfo;
import static seedu.address.testutil.TypicalStudyPlans.SP_1;
import static seedu.address.testutil.TypicalStudyPlans.SP_2;
import static seedu.address.testutil.TypicalStudyPlans.getTypicalModulePlanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.semester.Semester;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.ui.ResultViewType;


/**
 * Contains integration tests (interaction with the Model) for {@code ViewCommitCommand}.
 */
public class ViewCommitCommandTest {

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
        StudyPlan expectedCommittedPlan =
                expectedModel.getCommitListByStudyPlanIndex(expectedActiveStudyPlanIndex)
                        .getCommitByIndex(0).getStudyPlan();
        ObservableList<Semester> expectedSemesters =
                expectedCommittedPlan.getSemesters().asUnmodifiableObservableList();
        ViewCommitCommand command = new ViewCommitCommand(SP_1.getIndex(), 0);
        CommandResult<Semester> expectedResult =
                new CommandResult<>(ViewCommitCommand.MESSAGE_SUCCESS, ResultViewType.SEMESTER, expectedSemesters);
        assertCommandSuccess(command, model, expectedResult, expectedModel);
    }

    @Test
    public void execute_wrongActiveStudyPlanIndex_throwsCommandException() {
        ViewCommitCommand command = new ViewCommitCommand(SP_2.getIndex(), 0);
        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    public void execute_commitIndexOutOfBounds_throwsCommandException() {
        ViewCommitCommand command = new ViewCommitCommand(SP_1.getIndex(), 100);
        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    public void equals() {
        ViewCommitCommand command1 = new ViewCommitCommand(SP_1.getIndex(), 0);
        ViewCommitCommand command2 = new ViewCommitCommand(SP_2.getIndex(), 1);

        // same object -> returns true
        assertTrue(command1.equals(command1));

        // same values -> returns true
        ViewCommitCommand command3 = new ViewCommitCommand(SP_1.getIndex(), 0);
        assertTrue(command1.equals(command3));

        // different types -> returns false
        assertFalse(command1.equals(1));

        // null -> returns false
        assertFalse(command1.equals(null));

        // different commits -> returns false
        assertFalse(command1.equals(command2));
    }

}
