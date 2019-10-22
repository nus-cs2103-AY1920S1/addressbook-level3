package calofit.logic.commands;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import calofit.logic.commands.exceptions.CommandException;
import calofit.model.Model;
import calofit.model.ModelManager;

public class ReportCommandTest {

    @Test
    public void test_commandResult_returned() throws CommandException {
        Model model = Mockito.mock(Model.class);
        ReportCommand mockReportCommand = Mockito.mock(ReportCommand.class);
        Mockito.doReturn(Mockito.mock(CommandResult.class)).when(mockReportCommand).execute(model);
    }

    @Test
    public void test_commandException_thrown() {
        Model mockModel = new ModelManager();
        ReportCommand mockReportCommand = new ReportCommandStub();
        assertThrows(CommandException.class, () -> mockReportCommand.execute(mockModel));
    }
    /*
    @Test
    public void verify_model_interaction() throws CommandException {
        Model mockModel = Mockito.mock(Model.class);
        MealLog log = Mockito.mock(MealLog.class);
        log.addMeal(new Meal(new Dish(), new Timestamp(LocalDateTime.now()));
        Mockito.doReturn(log).when(mockModel).getMealLog();
        ReportCommand mockReportCommand = new ReportCommandStub();
        mockReportCommand.execute(mockModel);
        Mockito.verify(mockModel).updateStatistics();
    }
    */
    public static class ReportCommandStub extends ReportCommand {
        public ReportCommandStub() {
            super();
        }
    }

}


