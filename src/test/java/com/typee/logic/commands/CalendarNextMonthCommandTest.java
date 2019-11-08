package com.typee.logic.commands;

import static com.typee.logic.commands.CommandTestUtil.assertCommandSuccess;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.typee.model.Model;
import com.typee.model.ModelManager;

public class CalendarNextMonthCommandTest {

    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_calendarNextMonth_success() {
        CommandResult expectedCommandResult = new CommandResult(CalendarNextMonthCommand.MESSAGE_SUCCESS,
                true, CalendarNextMonthCommand.COMMAND_WORD);
        assertCommandSuccess(new CalendarNextMonthCommand(), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void equals_identicalInstance_returnTrue() {
        final CalendarNextMonthCommand typicalInstance = new CalendarNextMonthCommand();
        assertTrue(typicalInstance.equals(typicalInstance));
    }
}
