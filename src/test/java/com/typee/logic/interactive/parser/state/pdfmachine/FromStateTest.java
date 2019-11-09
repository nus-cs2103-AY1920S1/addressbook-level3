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

class FromStateTest {
    private FromState typicalFromState = new FromState(new ArgumentMultimap());
    @Test
    public void transition_validArgumentMultimap_returnsPostTransitionState() {
        try {
            ArgumentMultimap validArgumentMultimap = new ArgumentMultimap();
            validArgumentMultimap.put(CliSyntax.PREFIX_FROM, "Ko Gi Hun");

            State postTransitionState = new FromState(new ArgumentMultimap());
            ArgumentMultimap transitionArgumentMultimap = new ArgumentMultimap();
            transitionArgumentMultimap.put(CliSyntax.PREFIX_FROM, "Ko Gi Hun");
            postTransitionState = postTransitionState.transition(transitionArgumentMultimap);

            assertEquals(postTransitionState, new FromState(validArgumentMultimap));
        } catch (StateTransitionException e) {
            // StateTransitionException should not be thrown here.
        }
    }

    @Test
    public void transition_tooManyArguments_throwsStateTransitionException() {
        try {
            State postTransitionState = new FromState(new ArgumentMultimap());
            ArgumentMultimap transitionArgumentMultimap = new ArgumentMultimap();
            transitionArgumentMultimap.put(CliSyntax.PREFIX_FROM, "Ko Gi Hun");
            postTransitionState = postTransitionState.transition(transitionArgumentMultimap);
            transitionArgumentMultimap.put(CliSyntax.PREFIX_FROM, "Jason Chan");
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
            State postTransitionState = new FromState(new ArgumentMultimap());
            ArgumentMultimap transitionArgumentMultimap = new ArgumentMultimap();
            transitionArgumentMultimap.put(CliSyntax.PREFIX_FROM, "1234");

            postTransitionState = postTransitionState.transition(transitionArgumentMultimap);
            transitionArgumentMultimap.put(CliSyntax.PREFIX_FROM, "1234");
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
                typicalFromState.transition(new ArgumentMultimap()));
    }

    @Test
    public void getStateConstraints() {
        assertEquals(typicalFromState.getStateConstraints(), "Index stored. Please enter the name of the sender,"
                + " i.e. the person who the report is from, prefixed by \"f/\".");
    }

    @Test
    public void getPrefix() {
        assertEquals(typicalFromState.getPrefix(), new Prefix("f/"));
    }
}
