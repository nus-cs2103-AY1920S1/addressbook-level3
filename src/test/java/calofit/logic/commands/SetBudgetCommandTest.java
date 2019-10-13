package calofit.logic.commands;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import calofit.logic.commands.exceptions.CommandException;
import calofit.model.CalorieBudget;
import calofit.model.Model;
import calofit.model.dish.Calorie;

public class SetBudgetCommandTest {

    private final Calorie VALID_CALORIE = new Calorie(234);

    @Test
    public void testSetBudget() throws CommandException {
        CalorieBudget mockBudget = Mockito.mock(CalorieBudget.class);
        Mockito.doNothing().when(mockBudget).setCurrentBudget(Mockito.anyInt());

        Model mockModel = Mockito.mock(Model.class);
        Mockito.doReturn(mockBudget).when(mockModel).getCalorieBudget();

        SetBudgetCommand cmd = new SetBudgetCommand(VALID_CALORIE);
        cmd.execute(mockModel);

        Mockito.verify(mockBudget).setCurrentBudget(VALID_CALORIE.getValue());
        Mockito.verifyNoMoreInteractions(mockBudget);
    }
}