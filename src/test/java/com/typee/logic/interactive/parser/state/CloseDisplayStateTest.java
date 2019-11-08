package com.typee.logic.interactive.parser.state;

import static com.typee.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.CliSyntax;
import com.typee.logic.interactive.parser.state.calendarstate.CalendarState;
import com.typee.logic.interactive.parser.state.exceptions.StateTransitionException;

public class CloseDisplayStateTest {

    State closeDisplayState;

    @BeforeEach
    public void setup() {
        try {
            ArgumentMultimap argumentMultimap = new ArgumentMultimap();
            argumentMultimap.put(CliSyntax.PREFIX_CALENDAR, "closedisplay");
            closeDisplayState = new CalendarState(new ArgumentMultimap());
            closeDisplayState = closeDisplayState.transition(argumentMultimap);
        } catch (StateTransitionException e) {
            // StateTransitionException should not be thrown here.
        }
    }

    @Test
    public void transition_validArgumentMultimap_returnsPostTransitionState() {
        try {
            ArgumentMultimap validArgumentMultimap = new ArgumentMultimap();
            validArgumentMultimap.put(CliSyntax.PREFIX_CALENDAR, "closedisplay");
            validArgumentMultimap.put(CliSyntax.PREFIX_DATE, "11/11/2019");

            ArgumentMultimap transitionArgumentMultimap = new ArgumentMultimap();
            State postTransitionState = closeDisplayState.transition(transitionArgumentMultimap);

            assertEquals(postTransitionState, new CalendarState(validArgumentMultimap));
        } catch (StateTransitionException e) {
            // StateTransitionException should not be thrown here.
        }
    }

    @Test
    public void transition_tooManyArguments_throwsStateTransitionException() {
        try {
            ArgumentMultimap transitionArgumentMultimap = new ArgumentMultimap();
            transitionArgumentMultimap.put(CliSyntax.PREFIX_DATE, "11/11/2019");
            State postTransitionState = closeDisplayState.transition(transitionArgumentMultimap);
            transitionArgumentMultimap.put(CliSyntax.PREFIX_DATE, "12/11/2019");
            State finalPostTransitionState = postTransitionState;
            assertThrows(StateTransitionException.class, ()
                    -> finalPostTransitionState.transition(transitionArgumentMultimap));
        } catch (StateTransitionException e) {
            // StateTransitionException should not be thrown here.
        }
    }

    @Test
    public void transition_invalidDate_throwsStateTransitionException() {
        ArgumentMultimap transitionArgumentMultimap = new ArgumentMultimap();
        transitionArgumentMultimap.put(CliSyntax.PREFIX_DATE, "29/02/2019");
        State finalPostTransitionState = closeDisplayState;
        assertThrows(StateTransitionException.class, ()
                -> finalPostTransitionState.transition(transitionArgumentMultimap));
    }

    @Test
    public void getStateConstraints() {
        assertEquals(closeDisplayState.getStateConstraints(),
                "Please enter a date after \"d/\"/ The date must be in the dd/mm/yyyy format.");
    }

    @Test
    public void isEndState() {
        assertEquals(closeDisplayState.isEndState(), false);
    }

    @Test
    public void getPrefix() {
        assertEquals(closeDisplayState.getPrefix(), CliSyntax.PREFIX_DATE);
    }

}
