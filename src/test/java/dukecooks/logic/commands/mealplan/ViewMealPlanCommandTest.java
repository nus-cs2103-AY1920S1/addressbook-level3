package dukecooks.logic.commands.mealplan;

import static dukecooks.logic.commands.CommandTestUtil.assertCommandSuccess;
import static dukecooks.testutil.TypicalIndexes.INDEX_FAILURE_MEALPLAN;
import static dukecooks.testutil.TypicalIndexes.INDEX_FIRST_MEALPLAN;
import static dukecooks.testutil.TypicalIndexes.INDEX_SECOND_MEALPLAN;
import static dukecooks.testutil.mealplan.TypicalMealPlans.getTypicalMealPlanBook;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dukecooks.commons.core.Messages;
import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.model.Model;
import dukecooks.model.ModelManager;
import dukecooks.model.UserPrefs;
import dukecooks.model.mealplan.components.MealPlan;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ViewMealPlanCommand.
 */
public class ViewMealPlanCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalMealPlanBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getMealPlanBook(), new UserPrefs());
    }

    @Test
    public void execute_viewMealPlan_throwsCommandException() {
        CommandTestUtil.assertMealPlanCommandFailure(new ViewMealPlanCommand(INDEX_FAILURE_MEALPLAN), model,
                Messages.MESSAGE_INVALID_MEALPLAN_DISPLAYED_INDEX);
    }

    @Test
    public void execute_viewMealPlan_success() {
        MealPlan viewedMealPlan = model.getFilteredMealPlanList().get(INDEX_FIRST_MEALPLAN.getZeroBased());
        assertCommandSuccess(new ViewMealPlanCommand(INDEX_FIRST_MEALPLAN), expectedModel,
                String.format(ViewMealPlanCommand.MESSAGE_SUCCESS, viewedMealPlan.getName()),
                expectedModel);
    }

    @Test
    public void equals() {
        ViewMealPlanCommand firstViewMealPlanCommand = new ViewMealPlanCommand(INDEX_FIRST_MEALPLAN);
        ViewMealPlanCommand secondViewMealPlanCommand = new ViewMealPlanCommand(INDEX_FIRST_MEALPLAN);
        ViewMealPlanCommand thirdViewMealPlanCommand = new ViewMealPlanCommand(INDEX_SECOND_MEALPLAN);

        // same object -> return true
        assertTrue(firstViewMealPlanCommand.equals(firstViewMealPlanCommand));

        // same values -> returns true
        assertTrue(firstViewMealPlanCommand.equals(secondViewMealPlanCommand));

        // different types -> returns false
        assertFalse(firstViewMealPlanCommand.equals(1));

        // null -> returns false
        assertFalse(firstViewMealPlanCommand.equals(null));

        // different view diary commands -> returns false
        assertFalse(firstViewMealPlanCommand.equals(thirdViewMealPlanCommand));
    }
}
