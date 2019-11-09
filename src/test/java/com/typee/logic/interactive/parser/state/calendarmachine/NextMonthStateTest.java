package com.typee.logic.interactive.parser.state.calendarmachine;

import static com.typee.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.typee.logic.commands.CalendarNextMonthCommand;
import com.typee.logic.commands.exceptions.CommandException;
import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.CliSyntax;
import com.typee.logic.interactive.parser.Prefix;
import com.typee.logic.interactive.parser.state.State;
import com.typee.logic.interactive.parser.state.exceptions.StateTransitionException;

public class NextMonthStateTest {

    private State nextMonthState;

    @BeforeEach
    public void setup() {
        try {
            ArgumentMultimap argumentMultimap = new ArgumentMultimap();
            argumentMultimap.put(CliSyntax.PREFIX_CALENDAR, "nextmonth");
            nextMonthState = new CalendarState(new ArgumentMultimap());
            nextMonthState = nextMonthState.transition(argumentMultimap);
        } catch (StateTransitionException e) {
            // StateTransitionException should not be thrown here.
        }
    }

    @Test
    public void buildCommand() {
        try {
            assertEquals(((NextMonthState) nextMonthState).buildCommand(), new CalendarNextMonthCommand());
        } catch (CommandException e) {
            // CommandException should not be thrown here.
        }
    }

    @Test
    public void transition() {
        ArgumentMultimap argumentMultimap = new ArgumentMultimap();
        assertThrows(StateTransitionException.class, ()
            -> nextMonthState.transition(argumentMultimap));
    }

    @Test
    public void getStateConstraints() {
        assertEquals(nextMonthState.getStateConstraints(), "Next month displayed.");
    }

    @Test
    public void isEndState() {
        assertTrue(nextMonthState.isEndState());
    }

    @Test
    public void getPrefix() {
        assertEquals(nextMonthState.getPrefix(), new Prefix(""));
    }

}
