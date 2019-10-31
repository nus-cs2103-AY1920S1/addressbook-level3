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
        assertThrows(NullPointerException.class, () -> new CalendarDateDisplayEngagementsCommand(null));
    }

    @Test
    public void execute_validDate_displaySuccessful() {
        LocalDate validDate = LocalDate.of(2019, 10, 22);
        String formattedValidDateString = "22/10/2019";
        CommandResult commandResult = new CalendarDateDisplayEngagementsCommand(validDate).execute(model);

        assertEquals(CalendarDateDisplayEngagementsCommand.MESSAGE_SUCCESS + formattedValidDateString,
                commandResult.getFeedbackToUser());
    }

    /* Invalid dates are not tested since they should have already been accounted
       for by the CalendarCommandParser and are handled by its unit tests. */

    @Test
    public void equals() {
        LocalDate date = LocalDate.of(2019, 10, 22);
        CalendarDateDisplayEngagementsCommand calendarDateDisplayEngagementsCommand =
                new CalendarDateDisplayEngagementsCommand(date);
        CalendarDateDisplayEngagementsCommand identicalCalendarDateDisplayEngagementsCommand =
                new CalendarDateDisplayEngagementsCommand(date);
        assertTrue(calendarDateDisplayEngagementsCommand
                .equals(identicalCalendarDateDisplayEngagementsCommand));

        LocalDate otherDate = LocalDate.of(2019, 10, 23);
        CalendarDateDisplayEngagementsCommand differentCalendarDateDisplayEngagementsCommand =
                new CalendarDateDisplayEngagementsCommand(otherDate);
        assertFalse(calendarDateDisplayEngagementsCommand
                .equals(differentCalendarDateDisplayEngagementsCommand));
    }

}
