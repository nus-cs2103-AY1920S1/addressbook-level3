package calofit.logic.commands;

import calofit.model.meal.Dish;
import calofit.testutil.DishBuilder;
import calofit.testutil.TypicalDishes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import calofit.model.Model;
import calofit.model.ModelManager;
import calofit.model.UserPrefs;

import static calofit.logic.commands.CommandTestUtil.assertCommandFailure;
import static calofit.logic.commands.CommandTestUtil.assertCommandSuccess;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(TypicalDishes.getTypicalDishDatabase(), new UserPrefs());
    }

    @Test
    public void execute_newDish_success() {
        Dish validDish = new DishBuilder().withName("Charlie").build();

        Model expectedModel = new ModelManager(model.getDishDatabase(), new UserPrefs());
        expectedModel.addDish(validDish);

        assertCommandSuccess(new AddCommand(validDish), model,
                String.format(AddCommand.MESSAGE_SUCCESS, validDish), expectedModel);
    }

    @Test
    public void execute_duplicateDish_throwsCommandException() {
        Dish dishInList = model.getDishDatabase().getDishList().get(0);
        assertCommandFailure(new AddCommand(dishInList), model, AddCommand.MESSAGE_DUPLICATE_MEAL);
    }

}
