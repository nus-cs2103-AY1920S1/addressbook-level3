package com.typee.logic.interactive.parser.state;

import static com.typee.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.CliSyntax;
import com.typee.logic.interactive.parser.state.calendarstate.CalendarState;
import com.typee.logic.interactive.parser.state.exceptions.StateTransitionException;

public class CalendarStateTest {

    // Wrong values for each argument

    // Wrong order

    @Test
    public void transition_validArgumentMultimap_returnsPostTransitionState() {
        try {
            ArgumentMultimap validArgumentMultimap = new ArgumentMultimap();
            validArgumentMultimap.put(CliSyntax.PREFIX_CALENDAR, "opendisplay");
            validArgumentMultimap.put(CliSyntax.PREFIX_DATE, "11/11/2019");

            State postTransitionState = new CalendarState(new ArgumentMultimap());
            ArgumentMultimap transitionArgumentMultimap = new ArgumentMultimap();
            transitionArgumentMultimap.put(CliSyntax.PREFIX_CALENDAR, "opendisplay");
            postTransitionState = postTransitionState.transition(transitionArgumentMultimap);
            transitionArgumentMultimap.put(CliSyntax.PREFIX_DATE, "11/11/2019");
            postTransitionState = postTransitionState.transition(transitionArgumentMultimap);

            assertEquals(postTransitionState, new CalendarState(validArgumentMultimap));
        } catch (StateTransitionException e) {
            // StateTransitionException should not be thrown here.
        }
    }

    @Test
    public void transition_tooManyArguments_throwsStateTransitionException() {
        try {
            State postTransitionState = new CalendarState(new ArgumentMultimap());
            ArgumentMultimap transitionArgumentMultimap = new ArgumentMultimap();
            transitionArgumentMultimap.put(CliSyntax.PREFIX_CALENDAR, "opendisplay");
            postTransitionState = postTransitionState.transition(transitionArgumentMultimap);
            transitionArgumentMultimap.put(CliSyntax.PREFIX_DATE, "11/11/2019");
            postTransitionState = postTransitionState.transition(transitionArgumentMultimap);
            transitionArgumentMultimap.put(CliSyntax.PREFIX_DATE, "12/11/2019");
            State finalPostTransitionState = postTransitionState;
            assertThrows(StateTransitionException.class,
                    () -> finalPostTransitionState.transition(transitionArgumentMultimap));
        } catch (StateTransitionException e) {
            // StateTransitionException should be handled in assertThrows.
        }
    }

    @Test
    public void transition_invalidDate_throwsStateTransitionException() {
        try {
            State postTransitionState = new CalendarState(new ArgumentMultimap());
            ArgumentMultimap transitionArgumentMultimap = new ArgumentMultimap();
            transitionArgumentMultimap.put(CliSyntax.PREFIX_CALENDAR, "opendisplay");
            postTransitionState = postTransitionState.transition(transitionArgumentMultimap);
            transitionArgumentMultimap.put(CliSyntax.PREFIX_DATE, "29/02/2019");
            State finalPostTransitionState = postTransitionState;
            assertThrows(StateTransitionException.class,
                    () -> finalPostTransitionState.transition(transitionArgumentMultimap));
        } catch (StateTransitionException e) {
            // StateTransitionException should be handled in assertThrows.
        }
    }

}
