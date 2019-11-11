package seedu.address.logic.commands.storage;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
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


/**
 * Contains integration tests (interaction with the Model) for {@code ListAllStudyPlansCommand}.
 */
public class ListAllStudyPlansCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalModulePlanner(), new UserPrefs(), getTypicalModulesInfo());
    }

    @Test
    public void execute_revertCommit_success() {

        Model expectedModel = new ModelManager(getTypicalModulePlanner(), new UserPrefs(), getTypicalModulesInfo());
        ListAllStudyPlansCommand command = new ListAllStudyPlansCommand();
        StringBuilder toReturn = new StringBuilder(ListAllStudyPlansCommand.MESSAGE_SUCCESS);
        for (StudyPlan studyPlan : expectedModel.getFilteredStudyPlanList()) {
            toReturn.append(studyPlan.toString()).append("\n");
        }
        CommandResult expectedResult = new CommandResult(toReturn.toString());
        assertCommandSuccess(command, model, expectedResult, expectedModel);
    }

    @Test
    public void equals() {
        ListAllStudyPlansCommand command1 = new ListAllStudyPlansCommand();
        ListAllStudyPlansCommand command2 = new ListAllStudyPlansCommand();

        // same object --> true
        assertTrue(command1.equals(command1));

        // same command --> true
        assertTrue(command1.equals(command2));

        // different types --> false
        assertFalse(command1.equals(1));

        // null --> false
        assertFalse(command1.equals(null));
    }

}
