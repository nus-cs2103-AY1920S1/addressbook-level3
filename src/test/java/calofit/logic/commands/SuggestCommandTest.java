package calofit.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import calofit.logic.commands.exceptions.CommandException;
import calofit.model.Model;


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
