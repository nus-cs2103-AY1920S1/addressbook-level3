package com.typee.logic.interactive.parser.state.calendarmachine;

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

public class CalendarStateTest {

    private CalendarState calendarState;

    @BeforeEach
    public void setUp() {
        calendarState = new CalendarState(new ArgumentMultimap());
    }

    @Test
    public void transition_validArgumentMultimap_returnsPostTransitionState() {
        try {
            ArgumentMultimap validArgumentMultimap = new ArgumentMultimap();
            validArgumentMultimap.put(CliSyntax.PREFIX_CALENDAR, "opendisplay");
            validArgumentMultimap.put(CliSyntax.PREFIX_DATE, "11/11/2019");

            ArgumentMultimap transitionArgumentMultimap = new ArgumentMultimap();
            transitionArgumentMultimap.put(CliSyntax.PREFIX_CALENDAR, "opendisplay");
            State postTransitionState = calendarState.transition(transitionArgumentMultimap);
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
            ArgumentMultimap transitionArgumentMultimap = new ArgumentMultimap();
            transitionArgumentMultimap.put(CliSyntax.PREFIX_CALENDAR, "opendisplay");
            State postTransitionState = calendarState.transition(transitionArgumentMultimap);
            transitionArgumentMultimap.put(CliSyntax.PREFIX_DATE, "11/11/2019");
            postTransitionState = postTransitionState.transition(transitionArgumentMultimap);
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
        try {
            ArgumentMultimap transitionArgumentMultimap = new ArgumentMultimap();
            transitionArgumentMultimap.put(CliSyntax.PREFIX_CALENDAR, "opendisplay");
            State postTransitionState = calendarState.transition(transitionArgumentMultimap);
            transitionArgumentMultimap.put(CliSyntax.PREFIX_DATE, "29/02/2019");
            State finalPostTransitionState = postTransitionState;
            assertThrows(StateTransitionException.class, ()
                -> finalPostTransitionState.transition(transitionArgumentMultimap));
        } catch (StateTransitionException e) {
            // StateTransitionException should not be thrown here.
        }
    }

    @Test
    public void transition_zeroYear_throwsStateTransitionException() {
        try {
            ArgumentMultimap transitionArgumentMultimap = new ArgumentMultimap();
            transitionArgumentMultimap.put(CliSyntax.PREFIX_CALENDAR, "opendisplay");
            State postTransitionState = calendarState.transition(transitionArgumentMultimap);
            transitionArgumentMultimap.put(CliSyntax.PREFIX_DATE, "01/01/0000");
            State finalPostTransitionState = postTransitionState;
            assertThrows(StateTransitionException.class, ()
                -> finalPostTransitionState.transition(transitionArgumentMultimap));
        } catch (StateTransitionException e) {
            // StateTransitionException should not be thrown here.
        }
    }

    @Test
    public void transition_invalidPrefix_throwsStateTransitionException() {
        ArgumentMultimap invalidArgumentMultimap = new ArgumentMultimap();
        invalidArgumentMultimap.put(new Prefix("hi"), "opendisplay");
        assertThrows(StateTransitionException.class, ()
            -> calendarState.transition(invalidArgumentMultimap));
    }

    @Test
    public void transition_invalidTransitionArgumentMultimap_throwsStateTransitionException() {
        ArgumentMultimap invalidArgumentMultimap = new ArgumentMultimap();
        invalidArgumentMultimap.put(CliSyntax.PREFIX_CALENDAR, "hi");
        assertThrows(StateTransitionException.class, ()
            -> calendarState.transition(invalidArgumentMultimap));
    }

    @Test
    public void getStateConstraints() {
        assertEquals(calendarState.getStateConstraints(), "What would you like to do with the calendar? Please enter"
                + " the command prefixed by \"c/\". Allowed actions are"
                + " \"nextmonth\", \"previousmonth\", \"opendisplay\", and \"closedisplay\".");
    }

    @Test
    public void isEndState() {
        assertFalse(calendarState.isEndState());
    }

    @Test
    public void getPrefix() {
        assertEquals(calendarState.getPrefix(), CliSyntax.PREFIX_CALENDAR);
    }

}
