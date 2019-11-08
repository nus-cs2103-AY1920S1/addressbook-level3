package com.typee.logic.interactive.parser.state;

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
import com.typee.logic.interactive.parser.state.calendarstate.CalendarState;
import com.typee.logic.interactive.parser.state.calendarstate.OpenDisplayEndState;
import com.typee.logic.interactive.parser.state.exceptions.StateTransitionException;

public class OpenDisplayEndStateTest {

    private State openDisplayEndState;

    @BeforeEach
    public void setup() {
        try {
            ArgumentMultimap argumentMultimap = new ArgumentMultimap();
            argumentMultimap.put(CliSyntax.PREFIX_CALENDAR, "opendisplay");
            openDisplayEndState = new CalendarState(new ArgumentMultimap());
            openDisplayEndState = openDisplayEndState.transition(argumentMultimap);
            argumentMultimap.put(CliSyntax.PREFIX_DATE, "11/11/2019");
            openDisplayEndState = openDisplayEndState.transition(argumentMultimap);
        } catch (StateTransitionException e) {
            // StateTransitionException should not be thrown here.
        }
    }

    @Test
    public void buildCommand_validDate_returnsCalendarOpenDisplayCommand() {
        try {
            LocalDate validDate = LocalDate.of(2019, 11, 11);
            assertEquals(((OpenDisplayEndState) openDisplayEndState).buildCommand(),
                    new CalendarOpenDisplayCommand(validDate));
        } catch (CommandException e) {
            // CommandException should not be thrown here.
        }
    }

    @Test
    public void transition() {
        ArgumentMultimap argumentMultimap = new ArgumentMultimap();
        State finalPostTransitionState = openDisplayEndState;
        assertThrows(StateTransitionException.class, ()
            -> finalPostTransitionState.transition(argumentMultimap));
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
