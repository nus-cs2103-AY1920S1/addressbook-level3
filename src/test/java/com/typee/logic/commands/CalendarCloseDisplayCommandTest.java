package com.typee.logic.commands;

import static com.typee.logic.commands.CommandTestUtil.assertCommandSuccess;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.typee.commons.util.DateUtil;
import com.typee.model.Model;
import com.typee.model.ModelManager;

public class CalendarCloseDisplayCommandTest {

    private static final LocalDate TYPICAL_DATE = LocalDate.of(2019, 10, 22);

    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_validCommand_assertCommandSuccess() {
        assertCommandSuccess(new CalendarCloseDisplayCommand(TYPICAL_DATE), model,
                CalendarCloseDisplayCommand.MESSAGE_SUCCESS
                        + DateUtil.getFormattedDateString(TYPICAL_DATE), expectedModel);
    }

    @Test
    public void equalsTest() {
        CalendarCloseDisplayCommand typicalInstance = new CalendarCloseDisplayCommand(TYPICAL_DATE);

        assertTrue(typicalInstance.equals(typicalInstance));

        assertFalse(typicalInstance.equals(new CalendarNextMonthCommand()));
    }
}
