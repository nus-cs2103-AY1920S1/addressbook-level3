package calofit.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import calofit.commons.util.ObservableUtil;
import calofit.logic.commands.exceptions.CommandException;
import calofit.model.CalorieBudget;
import calofit.model.Model;
import calofit.model.dish.Calorie;
import calofit.testutil.Assert;
import calofit.testutil.TypicalDishes;
import calofit.testutil.TypicalMeals;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class SuggestCommandTest {

    @Test
    public void testSuggestList() throws CommandException {

        Model mockModel = Mockito.mock(Model.class);

        SuggestCommand suggestCommand = new SuggestCommand();

        CommandResult result = suggestCommand.execute(mockModel);

        assertEquals(result.getFeedbackToUser(), SuggestCommand.MESSAGE_SUCCESS);

        Mockito.verify(mockModel).setDishFilterPredicate(null);
    }
}
