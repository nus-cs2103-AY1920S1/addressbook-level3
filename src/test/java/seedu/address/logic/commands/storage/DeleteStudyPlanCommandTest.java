package seedu.address.logic.commands.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalModulesInfo.getTypicalModulesInfo;
import static seedu.address.testutil.TypicalStudyPlans.SP_1;
import static seedu.address.testutil.TypicalStudyPlans.SP_2;
import static seedu.address.testutil.TypicalStudyPlans.getTypicalModulePlanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.studyplan.exceptions.StudyPlanNotFoundException;

/**
 * Contains integration tests (interaction with the Model) for {@code DeleteStudyPlanCommand}.
 */
public class DeleteStudyPlanCommandTest {

    private static final Index INVALID_INDEX_POSITIVE = Index.fromOneBased(100);

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalModulePlanner(), new UserPrefs(), getTypicalModulesInfo());
    }

    @Test
    public void execute_deleteNonActiveStudyPlan_success() throws CommandException {
        Model expectedModel = new ModelManager(getTypicalModulePlanner(), new UserPrefs(), getTypicalModulesInfo());
        expectedModel.deleteStudyPlan(SP_2);

        DeleteStudyPlanCommand command = new DeleteStudyPlanCommand(Index.fromZeroBased((SP_2.getIndex())));
        CommandResult expectedResult =
                new CommandResult(String.format(DeleteStudyPlanCommand.MESSAGE_SUCCESS, SP_2));
        CommandResult actualResult = command.execute(model);

        // check command result
        assertEquals(expectedResult, actualResult);

        // check whether SP_2 is gone
        assertThrows(StudyPlanNotFoundException.class, () -> model.getStudyPlan(SP_2.getIndex()));
    }

    @Test
    public void execute_deleteActiveStudyPlan_success() throws CommandException {
        // SP_1 is the active study plan
        Model expectedModel = new ModelManager(getTypicalModulePlanner(), new UserPrefs(), getTypicalModulesInfo());
        expectedModel.deleteStudyPlan(SP_1);

        DeleteStudyPlanCommand command = new DeleteStudyPlanCommand(Index.fromZeroBased((SP_1.getIndex())));
        CommandResult expectedResult =
                new CommandResult(String.format(DeleteStudyPlanCommand.MESSAGE_SUCCESS, SP_1),
                        true, false);
        CommandResult actualResult = command.execute(model);

        // check command result
        assertEquals(expectedResult, actualResult);

        // check whether SP_1 is gone
        assertThrows(StudyPlanNotFoundException.class, () -> model.getStudyPlan(SP_1.getIndex()));
    }

    @Test
    public void execute_studyPlanIndexExceedsLimit_throwsCommandException() throws CommandException {
        DeleteStudyPlanCommand command = new DeleteStudyPlanCommand(INVALID_INDEX_POSITIVE);
        assertThrows(CommandException.class, () -> command.execute(model));
    }

}
