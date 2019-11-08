package com.typee.logic.commands;

import static com.typee.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import com.typee.model.Model;
import com.typee.model.ModelManager;

public class CalendarPreviousMonthCommandTest {

    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_calendarPreviousMonth_success() {
        CommandResult expectedCommandResult = new CommandResult(CalendarPreviousMonthCommand.MESSAGE_SUCCESS,
                true, CalendarPreviousMonthCommand.COMMAND_WORD);
        assertCommandSuccess(new CalendarPreviousMonthCommand(), model, expectedCommandResult, expectedModel);
    }

}
