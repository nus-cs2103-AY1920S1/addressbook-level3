package com.typee.logic.interactive.parser.state.sortmachine;

import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_PROPERTY;
import static com.typee.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.CliSyntax;
import com.typee.logic.interactive.parser.Prefix;
import com.typee.logic.interactive.parser.state.State;
import com.typee.logic.interactive.parser.state.exceptions.StateTransitionException;

public class PropertyStateTest {

    public static final String EXPECTED_CONSTRAINTS = "Which property would you like to sort by? Please enter the "
            + "property prefixed by " + PREFIX_PROPERTY.getPrefix() + ". Example - [p/start]";

    private static final PropertyState TYPICAL_PROPERTY_STATE = new PropertyState(new ArgumentMultimap());

    @Test
    public void transition_validArgumentMultimap_returnsPostTransitionState() {
        try {
            ArgumentMultimap validArgumentMultimap = new ArgumentMultimap();
            validArgumentMultimap.put(CliSyntax.PREFIX_PROPERTY, "start");

            State postTransitionState = new PropertyState(new ArgumentMultimap());
            ArgumentMultimap transitionArgumentMultimap = new ArgumentMultimap();
            transitionArgumentMultimap.put(CliSyntax.PREFIX_PROPERTY, "start");
            postTransitionState = postTransitionState.transition(transitionArgumentMultimap);

            assertEquals(postTransitionState, new PropertyState(validArgumentMultimap));
        } catch (StateTransitionException e) {
            // StateTransitionException should not be thrown here.
        }
    }

    @Test
    public void transition_invalidOrder_throwsStateTransitionException() {
        try {
            State postTransitionState = new PropertyState(new ArgumentMultimap());
            ArgumentMultimap transitionArgumentMultimap = new ArgumentMultimap();
            transitionArgumentMultimap.put(CliSyntax.PREFIX_PROPERTY, "stadt");
            postTransitionState = postTransitionState.transition(transitionArgumentMultimap);
            State finalPostTransitionState = postTransitionState;
            assertThrows(StateTransitionException.class, ()
                -> finalPostTransitionState.transition(transitionArgumentMultimap));
        } catch (StateTransitionException e) {
            // StateTransitionException should be handled in assertThrows.
        }
    }

    @Test
    public void getStateConstraints() {
        assertEquals(EXPECTED_CONSTRAINTS, TYPICAL_PROPERTY_STATE.getStateConstraints());
    }

    @Test
    public void isEndState() {
        assertFalse(TYPICAL_PROPERTY_STATE.isEndState());
    }

    @Test
    public void getPrefix() {
        assertEquals(TYPICAL_PROPERTY_STATE.getPrefix(), new Prefix("p/"));
    }

}
