package seedu.savenus.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.savenus.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.savenus.testutil.TypicalMenu.CHICKEN_RICE;
import static seedu.savenus.testutil.TypicalMenu.getTypicalMenu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.savenus.logic.commands.exceptions.CommandException;
import seedu.savenus.model.Model;
import seedu.savenus.model.ModelManager;
import seedu.savenus.model.UserPrefs;

/**
 * Contains integration tests and unit tests for RecommendCommand.
 */
public class RecommendCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalMenu(), new UserPrefs());
        expectedModel = new ModelManager(model.getMenu(), new UserPrefs());
    }

    @Test
    public void execute_recommendCommand() {
        assertCommandSuccess(new RecommendCommand(), model, RecommendCommand.MESSAGE_SUCCESS, expectedModel);
        assertTrue(model.getRecommendationSystem().isInUse());
    }

    @Test
    public void recommendCommand_notInUse_afterList() {
        assertCommandSuccess(new RecommendCommand(), model, RecommendCommand.MESSAGE_SUCCESS, expectedModel);
        assertTrue(model.getRecommendationSystem().isInUse());

        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
        assertFalse(model.getRecommendationSystem().isInUse());
    }

    @Test
    public void recommendCommand_notInUse_afterAdd() throws CommandException {
        assertCommandSuccess(new RecommendCommand(), model, RecommendCommand.MESSAGE_SUCCESS, expectedModel);
        assertTrue(model.getRecommendationSystem().isInUse());

        CommandResult commandResult = new AddCommand(CHICKEN_RICE).execute(model);
        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, CHICKEN_RICE), commandResult.getFeedbackToUser());

        assertFalse(model.getRecommendationSystem().isInUse());
    }

    @Test
    public void equals() {
        final RecommendCommand standardCommand = new RecommendCommand();

        // same object -> returns true
        assertEquals(standardCommand, standardCommand);

        // null -> returns false
        assertNotEquals(null, standardCommand);
    }

}
