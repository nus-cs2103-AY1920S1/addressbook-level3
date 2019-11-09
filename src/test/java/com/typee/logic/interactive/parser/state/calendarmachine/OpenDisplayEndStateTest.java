package com.typee.logic.interactive.parser.state.calendarmachine;

import static com.typee.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.typee.logic.commands.CalendarOpenDisplayCommand;
import com.typee.logic.commands.exceptions.CommandException;
import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.CliSyntax;
import com.typee.logic.interactive.parser.Prefix;
import com.typee.logic.interactive.parser.state.State;
import com.typee.logic.interactive.parser.state.exceptions.StateTransitionException;

public class OpenDisplayEndStateTest {

    private OpenDisplayEndState openDisplayEndState;

    @BeforeEach
    public void setUp() {
        try {
            ArgumentMultimap argumentMultimap = new ArgumentMultimap();
            argumentMultimap.put(CliSyntax.PREFIX_CALENDAR, "opendisplay");
            State calendarState = new CalendarState(new ArgumentMultimap());
            calendarState = calendarState.transition(argumentMultimap);
            argumentMultimap.put(CliSyntax.PREFIX_DATE, "11/11/2019");
            openDisplayEndState = (OpenDisplayEndState) calendarState.transition(argumentMultimap);
        } catch (StateTransitionException e) {
            // StateTransitionException should not be thrown here.
        }
    }

    @Test
    public void buildCommand_validDate_returnsCalendarOpenDisplayCommand() {
        try {
            LocalDate validDate = LocalDate.of(2019, 11, 11);
            assertEquals(openDisplayEndState.buildCommand(),
                    new CalendarOpenDisplayCommand(validDate));
        } catch (CommandException e) {
            // CommandException should not be thrown here.
        }
    }

    @Test
    public void transition() {
        ArgumentMultimap argumentMultimap = new ArgumentMultimap();
        assertThrows(StateTransitionException.class, ()
            -> openDisplayEndState.transition(argumentMultimap));
    }

    @Test
    public void getStateConstraints() {
        assertEquals(openDisplayEndState.getStateConstraints(), "Displayed engagements on the entered date.");
    }

    @Test
    public void isEndState() {
        assertTrue(openDisplayEndState.isEndState());
    }

    @Test
    public void getPrefix() {
        assertEquals(openDisplayEndState.getPrefix(), new Prefix(""));
    }

}
