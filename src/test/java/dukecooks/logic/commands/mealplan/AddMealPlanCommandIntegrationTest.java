package dukecooks.logic.commands.mealplan;

import static dukecooks.testutil.mealplan.TypicalMealPlans.getTypicalMealPlanBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.model.Model;
import dukecooks.model.ModelManager;
import dukecooks.model.UserPrefs;
import dukecooks.model.mealplan.components.MealPlan;

/**
 * Contains integration tests (interaction with the Model) for {@code AddMealPlanCommand}.
 */
public class AddMealPlanCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalMealPlanBook(), new UserPrefs());
    }

    @Test
    public void execute_duplicateMealPlan_throwsCommandException() {
        MealPlan recipeInList = model.getMealPlanBook().getMealPlanList().get(0);
        CommandTestUtil.assertMealPlanCommandFailure(new AddMealPlanCommand(recipeInList), model,
                AddMealPlanCommand.MESSAGE_DUPLICATE_MEALPLAN);
    }

}
