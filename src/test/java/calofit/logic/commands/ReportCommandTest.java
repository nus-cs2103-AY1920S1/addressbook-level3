package calofit.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import calofit.logic.commands.exceptions.CommandException;
import calofit.model.Model;
import calofit.model.meal.Meal;
import calofit.model.meal.MealLog;

public class ReportCommandTest {

    @Test
    public void test_commandResult_returned() throws CommandException {
        Model mockModel = Mockito.mock(Model.class);
        MealLog mockMealLog = Mockito.mock(MealLog.class);
        ReportCommand reportCommand = new ReportCommand();
        CommandResult mockResult = Mockito.mock(CommandResult.class);
        ObservableList<Meal> emptyListSpy = Mockito.spy(FXCollections.observableList(new ArrayList<>()));

        Mockito.doReturn(ReportCommand.MESSAGE_SUCCESS).when(mockResult).getFeedbackToUser();

        Mockito.doReturn(mockMealLog).when(mockModel).getMealLog();
        Mockito.doReturn(emptyListSpy).when(mockMealLog).getCurrentMonthMeals();
        Mockito.doReturn(1).when(emptyListSpy).size();

        CommandResult actualResult = reportCommand.execute(mockModel);

        assertEquals(mockResult.getFeedbackToUser(), actualResult.getFeedbackToUser());
        assertTrue(actualResult.isShowReport());
        assertFalse(actualResult.isExit());
        assertFalse(actualResult.isShowHelp());
    }

    @Test
    public void test_commandException_thrown() {
        Model mockModel = Mockito.mock(Model.class);
        ReportCommand failedCommand = new ReportCommand();
        MealLog mockMealLog = Mockito.mock(MealLog.class);
        ObservableList<Meal> emptyListSpy = Mockito.spy(FXCollections.observableList(new ArrayList<>()));

        Mockito.doReturn(mockMealLog).when(mockModel).getMealLog();
        Mockito.doReturn(emptyListSpy).when(mockMealLog).getCurrentMonthMeals();
        Mockito.doReturn(0).when(emptyListSpy).size();

        assertThrows(CommandException.class, () -> failedCommand.execute(mockModel));
    }

}


