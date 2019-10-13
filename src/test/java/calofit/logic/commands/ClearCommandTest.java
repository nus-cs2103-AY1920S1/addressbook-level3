package calofit.logic.commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import calofit.model.Model;
import calofit.model.dish.DishDatabase;

public class ClearCommandTest {

    @Test
    public void execute_emptyDishDatabase_success() {
        Model mock = Mockito.mock(Model.class);
        Mockito.doNothing().when(mock).setDishDatabase(Mockito.any());

        CommandResult result = new ClearCommand().execute(mock);

        ArgumentCaptor<DishDatabase> dbCaptor = ArgumentCaptor.forClass(DishDatabase.class);
        Mockito.verify(mock).setDishDatabase(dbCaptor.capture());
        Assertions.assertEquals(new DishDatabase(), dbCaptor.getValue());
        Mockito.verifyNoMoreInteractions(mock);

        Assertions.assertEquals(result.getFeedbackToUser(), ClearCommand.MESSAGE_SUCCESS);
    }
}
