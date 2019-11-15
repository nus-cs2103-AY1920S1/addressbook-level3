package dukecooks.logic.commands.mealplan;

import static dukecooks.logic.commands.CommandTestUtil.assertCommandSuccess;
import static dukecooks.testutil.mealplan.TypicalMealPlans.getTypicalMealPlanBook;

import org.junit.jupiter.api.Test;

import dukecooks.model.Model;
import dukecooks.model.ModelManager;
import dukecooks.model.UserPrefs;
import dukecooks.model.mealplan.MealPlanBook;

public class ClearMealPlanCommandTest {

    @Test
    public void execute_emptyMealPlanBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearMealPlanCommand(), model, ClearMealPlanCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyMealPlanBook_success() {
        Model model = new ModelManager(getTypicalMealPlanBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalMealPlanBook(), new UserPrefs());
        expectedModel.setMealPlanBook(new MealPlanBook());

        assertCommandSuccess(new ClearMealPlanCommand(), model, ClearMealPlanCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
