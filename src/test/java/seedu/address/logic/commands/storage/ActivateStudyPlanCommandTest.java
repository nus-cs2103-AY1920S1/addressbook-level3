package seedu.address.logic.commands.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalModulesInfo.getTypicalModulesInfo;
import static seedu.address.testutil.TypicalStudyPlans.SP_1;
import static seedu.address.testutil.TypicalStudyPlans.getTypicalModulePlanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) for {@code ActivateStudyPlanCommand}.
 */
public class ActivateStudyPlanCommandTest {

    private static final int INVALID_SP_INDEX = 80;

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalModulePlanner(), new UserPrefs(), getTypicalModulesInfo());
        model.addStudyPlan(SP_1);
    }

    @Test
    public void execute_commitStudyPlan_success() throws CommandException {
        Model expectedModel = new ModelManager(getTypicalModulePlanner(), new UserPrefs(), getTypicalModulesInfo());
        expectedModel.activateStudyPlan(SP_1.getIndex());

        ActivateStudyPlanCommand command = new ActivateStudyPlanCommand(SP_1.getIndex());
        CommandResult expectedResult = new CommandResult(String.format(ActivateStudyPlanCommand.MESSAGE_SUCCESS,
                        SP_1.getIndex(), SP_1.getTitle()), true, false);
        CommandResult actualResult = command.execute(model);
        assertEquals(actualResult, expectedResult);
        assertEquals(model.getActiveStudyPlan(), expectedModel.getActiveStudyPlan());
        // note: assertCommandSuccess was not used due to uncertainty with undo/redo
    }

    @Test
    public void execute_commitStudyPlan_throwsCommandException() throws CommandException {
        Model expectedModel = new ModelManager(getTypicalModulePlanner(), new UserPrefs(), getTypicalModulesInfo());
        expectedModel.activateStudyPlan(SP_1.getIndex());

        ActivateStudyPlanCommand command = new ActivateStudyPlanCommand(INVALID_SP_INDEX);
        assertThrows(CommandException.class, () -> command.execute(model));
    }

}
