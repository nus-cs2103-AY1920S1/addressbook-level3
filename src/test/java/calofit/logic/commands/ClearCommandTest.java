package calofit.logic.commands;

import calofit.model.DishDatabase;
import calofit.model.Model;
import calofit.model.ModelManager;
import calofit.model.UserPrefs;
import calofit.testutil.TypicalDishes;
import org.junit.jupiter.api.Test;

import static calofit.logic.commands.CommandTestUtil.assertCommandSuccess;

public class ClearCommandTest {

    @Test
    public void execute_emptyDishDatabase_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyDishDatabase_success() {
        Model model = new ModelManager(TypicalDishes.getTypicalDishDatabase(), new UserPrefs());
        Model expectedModel = new ModelManager(TypicalDishes.getTypicalDishDatabase(), new UserPrefs());
        expectedModel.setDishDatabase(new DishDatabase());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
