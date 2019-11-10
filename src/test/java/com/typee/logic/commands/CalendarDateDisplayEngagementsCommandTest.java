package com.typee.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.typee.model.Model;
import com.typee.model.ModelManager;

class CalendarDateDisplayEngagementsCommandTest {

    private Model model = new ModelManager();

    @Test
    public void constructor_nullEngagement_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CalendarOpenDisplayCommand(null));
    }

    @Test
    public void execute_validDate_displaySuccessful() {
        LocalDate validDate = LocalDate.of(2019, 10, 22);
        String formattedValidDateString = "22/10/2019";
        CommandResult commandResult = new CalendarOpenDisplayCommand(validDate).execute(model);

        assertEquals(CalendarOpenDisplayCommand.MESSAGE_SUCCESS + formattedValidDateString,
                commandResult.getFeedbackToUser());
    }

    /* Invalid dates are not tested since they should have already been accounted
       for by the CalendarCommandParser and are handled by its unit tests. */

    @Test
    public void equals() {
        LocalDate date = LocalDate.of(2019, 10, 22);
        CalendarOpenDisplayCommand calendarDateDisplayEngagementsCommand =
                new CalendarOpenDisplayCommand(date);
        CalendarOpenDisplayCommand identicalCalendarDateDisplayEngagementsCommand =
                new CalendarOpenDisplayCommand(date);
        assertTrue(calendarDateDisplayEngagementsCommand
                .equals(identicalCalendarDateDisplayEngagementsCommand));

        LocalDate otherDate = LocalDate.of(2019, 10, 23);
        CalendarOpenDisplayCommand differentCalendarDateDisplayEngagementsCommand =
                new CalendarOpenDisplayCommand(otherDate);
        assertFalse(calendarDateDisplayEngagementsCommand
                .equals(differentCalendarDateDisplayEngagementsCommand));

        CalendarOpenDisplayCommand typicalCommand = new CalendarOpenDisplayCommand(date);
        assertTrue(typicalCommand.equals(typicalCommand));

        assertFalse(typicalCommand.equals(new CalendarNextMonthCommand()));
    }

}
