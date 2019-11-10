package com.typee.logic.interactive.parser.state.pdfmachine;

import static com.typee.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.CliSyntax;
import com.typee.logic.interactive.parser.Prefix;
import com.typee.logic.interactive.parser.state.State;
import com.typee.logic.interactive.parser.state.exceptions.StateTransitionException;

class ToStateTest {
    private ToState typicalToState = new ToState(new ArgumentMultimap());
    @Test
    public void transition_validArgumentMultimap_returnsPostTransitionState() {
        try {
            ArgumentMultimap validArgumentMultimap = new ArgumentMultimap();
            validArgumentMultimap.put(CliSyntax.PREFIX_TO, "Daniel Kim");

            State postTransitionState = new ToState(new ArgumentMultimap());
            ArgumentMultimap transitionArgumentMultimap = new ArgumentMultimap();
            transitionArgumentMultimap.put(CliSyntax.PREFIX_TO, "Daniel Kim");
            postTransitionState = postTransitionState.transition(transitionArgumentMultimap);

            assertEquals(postTransitionState, new ToState(validArgumentMultimap));
        } catch (StateTransitionException e) {
            // StateTransitionException should not be thrown here.
        }
    }

    @Test
    public void transition_tooManyArguments_throwsStateTransitionException() {
        try {
            State postTransitionState = new ToState(new ArgumentMultimap());
            ArgumentMultimap transitionArgumentMultimap = new ArgumentMultimap();
            transitionArgumentMultimap.put(CliSyntax.PREFIX_TO, "Ko Gi Hun");
            postTransitionState = postTransitionState.transition(transitionArgumentMultimap);
            transitionArgumentMultimap.put(CliSyntax.PREFIX_TO, "Jason Chan");
            State finalPostTransitionState = postTransitionState;
            assertThrows(StateTransitionException.class, () ->
                    finalPostTransitionState.transition(transitionArgumentMultimap));
        } catch (StateTransitionException e) {
            // StateTransitionException should be handled in assertThrows.
        }
    }

    @Test
    public void transition_invalidFrom_throwsStateTransitionException() {
        try {
            State postTransitionState = new ToState(new ArgumentMultimap());
            ArgumentMultimap transitionArgumentMultimap = new ArgumentMultimap();
            transitionArgumentMultimap.put(CliSyntax.PREFIX_TO, "1234");

            postTransitionState = postTransitionState.transition(transitionArgumentMultimap);
            transitionArgumentMultimap.put(CliSyntax.PREFIX_TO, "1234");
            State finalPostTransitionState = postTransitionState;
            assertThrows(StateTransitionException.class, () ->
                    finalPostTransitionState.transition(transitionArgumentMultimap));
        } catch (StateTransitionException e) {
            // StateTransitionException should be handled in assertThrows.
        }
    }

    @Test
    public void transition() {
        Assertions.assertThrows(StateTransitionException.class, () ->
                typicalToState.transition(new ArgumentMultimap()));
    }

    @Test
    public void getStateConstraints() {
        assertEquals(typicalToState.getStateConstraints(), "Sender name stored. Please enter the name of the receiver,"
                + " i.e. the person who the report is sent to, prefixed by \"t/\".");
    }

    @Test
    public void getPrefix() {
        assertEquals(typicalToState.getPrefix(), new Prefix("t/"));
    }
}
