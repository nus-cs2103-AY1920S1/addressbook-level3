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

/**
 * Contains integration tests (interaction with the Model) for {@code EditTitleCommand}.
 */
public class EditTitleCommandTest {

    private Model model;
    private String newTitle = "new title";

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalModulePlanner(), new UserPrefs(), getTypicalModulesInfo());
    }

    @Test
    public void execute_newStudyPlan_success() throws CommandException {

        Model expectedModel = new ModelManager(model.getModulePlanner(), new UserPrefs(), getTypicalModulesInfo());
        expectedModel.changeActiveStudyPlanTitle(newTitle);

        EditTitleCommand command = new EditTitleCommand(newTitle);
        CommandResult expectedResult =
                new CommandResult(String.format(EditTitleCommand.MESSAGE_EDIT_STUDYPLAN_SUCCESS, newTitle),
                        true, false);

        // check command result
        CommandResult actualResult = command.execute(model);
        assertEquals(expectedResult, actualResult);
        // check study plans
        assertEquals(expectedModel.getActiveStudyPlan(), model.getActiveStudyPlan());
    }

}
