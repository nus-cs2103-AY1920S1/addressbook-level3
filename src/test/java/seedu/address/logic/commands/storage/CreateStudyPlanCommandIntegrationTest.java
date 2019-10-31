package seedu.address.logic.commands.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
 * Contains integration tests (interaction with the Model) for {@code CreateStudyPlanCommand}.
 */
public class CreateStudyPlanCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalModulePlanner(), new UserPrefs(), getTypicalModulesInfo());
    }

    @Test
    public void execute_newStudyPlan_success() throws CommandException {
        StudyPlan validStudyPlan = new StudyPlanBuilder().build();

        Model expectedModel = new ModelManager(getTypicalModulePlanner(), new UserPrefs(), getTypicalModulesInfo());
        expectedModel.addStudyPlan(validStudyPlan);
        expectedModel.activateStudyPlan(validStudyPlan.getIndex());

        CreateStudyPlanCommand command = new CreateStudyPlanCommand(validStudyPlan.getTitle().toString());
        CommandResult expectedResult = new CommandResult(String.format(CreateStudyPlanCommand.MESSAGE_SUCCESS,
                validStudyPlan.getTitle().toString()), true, false);
        CommandResult actualResult = command.execute(model);
        assertEquals(expectedResult, actualResult);
        assertEquals(expectedModel.getActiveStudyPlan().getTitle(), model.getActiveStudyPlan().getTitle());
        // does not use assertCommandSuccess due to uncertainty with undo/redo
    }

}
