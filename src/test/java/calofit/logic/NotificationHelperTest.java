package calofit.logic;

import calofit.logic.commands.SetBudgetCommand;
import calofit.logic.commands.exceptions.CommandException;
import calofit.model.CalorieBudget;
import calofit.model.Model;
import calofit.model.dish.Calorie;
import calofit.model.meal.MealLog;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class NotificationHelperTest {

    private static final String BREAKFAST = "Looks like you have missed your breakfast,"
        + " please take some time out to eat your breakfast!";

    @Test
    public void testEmptyMealLog_afterTenAM() throws CommandException{
        MealLog emptyMealLog = Mockito.mock(MealLog.class);

    }
}
