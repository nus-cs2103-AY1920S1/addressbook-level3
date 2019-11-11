package com.typee.logic.interactive.parser.state.calendarmachine;

import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_DATE;
import static com.typee.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.CliSyntax;
import com.typee.logic.interactive.parser.Prefix;
import com.typee.logic.interactive.parser.state.State;
import com.typee.logic.interactive.parser.state.exceptions.StateTransitionException;

public class OpenDisplayStateTest {

    public static final String EXPECTED_CONSTRAINTS = "Which day's engagements would you like to view? Please enter a "
            + "date prefixed by " + PREFIX_DATE.getPrefix() + ". Example - [d/15/11/2019]";
    private OpenDisplayState openDisplayState;

    @BeforeEach
    public void setUp() {
        try {
            ArgumentMultimap argumentMultimap = new ArgumentMultimap();
            argumentMultimap.put(CliSyntax.PREFIX_CALENDAR, "opendisplay");
            State calendarState = new CalendarState(new ArgumentMultimap());
            openDisplayState = (OpenDisplayState) calendarState.transition(argumentMultimap);
        } catch (StateTransitionException e) {
            // StateTransitionException should not be thrown here.
        }
    }

    @Test
    public void transition_validArgumentMultimap_returnsPostTransitionState() {
        try {
            ArgumentMultimap validArgumentMultimap = new ArgumentMultimap();
            validArgumentMultimap.put(CliSyntax.PREFIX_CALENDAR, "opendisplay");
            validArgumentMultimap.put(CliSyntax.PREFIX_DATE, "11/11/2019");

            ArgumentMultimap transitionArgumentMultimap = new ArgumentMultimap();
            State postTransitionState = openDisplayState.transition(transitionArgumentMultimap);

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
            State postTransitionState = openDisplayState.transition(transitionArgumentMultimap);
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
        State finalPostTransitionState = openDisplayState;
        assertThrows(StateTransitionException.class, ()
            -> finalPostTransitionState.transition(transitionArgumentMultimap));
    }

    @Test
    public void transition_invalidArgumentMultimap_throwsStateTransitionException() {
        ArgumentMultimap invalidArgumentMultimap = new ArgumentMultimap();
        invalidArgumentMultimap.put(new Prefix("hi"), "11/11/2019");
        assertThrows(StateTransitionException.class, ()
            -> openDisplayState.transition(invalidArgumentMultimap));
    }

    @Test
    public void getStateConstraints() {
        assertEquals(EXPECTED_CONSTRAINTS, openDisplayState.getStateConstraints());
    }

    @Test
    public void isEndState() {
        assertFalse(openDisplayState.isEndState());
    }

    @Test
    public void getPrefix() {
        assertEquals(openDisplayState.getPrefix(), CliSyntax.PREFIX_DATE);
    }

}
