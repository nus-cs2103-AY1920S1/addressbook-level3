package calofit.logic.commands;

import calofit.testutil.MealBuilder;
import calofit.testutil.TypicalMeals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import calofit.model.Model;
import calofit.model.ModelManager;
import calofit.model.UserPrefs;
import calofit.model.meal.Meal;

import static calofit.logic.commands.CommandTestUtil.assertCommandFailure;
import static calofit.logic.commands.CommandTestUtil.assertCommandSuccess;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(TypicalMeals.getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newMeal_success() {
        Meal validMeal = new MealBuilder().withName("Charlie").build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addMeal(validMeal);

        assertCommandSuccess(new AddCommand(validMeal), model,
                String.format(AddCommand.MESSAGE_SUCCESS, validMeal), expectedModel);
    }

    @Test
    public void execute_duplicateMeal_throwsCommandException() {
        Meal mealInList = model.getAddressBook().getMealList().get(0);
        assertCommandFailure(new AddCommand(mealInList), model, AddCommand.MESSAGE_DUPLICATE_MEAL);
    }

}
