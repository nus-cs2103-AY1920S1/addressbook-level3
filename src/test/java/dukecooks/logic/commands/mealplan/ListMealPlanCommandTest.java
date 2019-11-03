package dukecooks.logic.commands.mealplan;

import static dukecooks.logic.commands.CommandTestUtil.assertCommandSuccess;
import static dukecooks.testutil.TypicalIndexes.INDEX_FIRST_RECIPE;
import static dukecooks.testutil.mealplan.TypicalMealPlans.getTypicalMealPlanBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.model.Model;
import dukecooks.model.ModelManager;
import dukecooks.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListMealPlanCommand.
 */
public class ListMealPlanCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalMealPlanBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getMealPlanBook(), new UserPrefs());
    }

    @Test
    public void execute_mealPlanListIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListMealPlanCommand(), model, ListMealPlanCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_mealPlanListIsFiltered_showsEverything() {
        CommandTestUtil.showMealPlanAtIndex(model, INDEX_FIRST_RECIPE);
        assertCommandSuccess(new ListMealPlanCommand(), model, ListMealPlanCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
