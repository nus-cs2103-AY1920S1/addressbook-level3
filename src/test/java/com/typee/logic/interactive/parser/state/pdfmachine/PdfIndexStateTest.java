package com.typee.logic.interactive.parser.state.pdfmachine;

import static com.typee.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.CliSyntax;
import com.typee.logic.interactive.parser.Prefix;
import com.typee.logic.interactive.parser.state.State;
import com.typee.logic.interactive.parser.state.exceptions.StateTransitionException;

class PdfIndexStateTest {
    private PdfIndexState typicalPdfIndexState;
    @BeforeEach
    void setUp() {
        typicalPdfIndexState = new PdfIndexState(new ArgumentMultimap());
    }
    @Test
    public void transition_validArgumentMultimap_returnsPostTransitionState() {
        try {
            ArgumentMultimap validArgumentMultimap = new ArgumentMultimap();
            validArgumentMultimap.put(CliSyntax.PREFIX_LIST_INDEX, "1");

            State postTransitionState = new PdfIndexState(new ArgumentMultimap());
            ArgumentMultimap transitionArgumentMultimap = new ArgumentMultimap();
            transitionArgumentMultimap.put(CliSyntax.PREFIX_LIST_INDEX, "1");
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
            transitionArgumentMultimap.put(CliSyntax.PREFIX_LIST_INDEX, "1");
            postTransitionState = postTransitionState.transition(transitionArgumentMultimap);
            transitionArgumentMultimap.put(CliSyntax.PREFIX_LIST_INDEX, "1");
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
            transitionArgumentMultimap.put(CliSyntax.PREFIX_LIST_INDEX, "hello");

            postTransitionState = postTransitionState.transition(transitionArgumentMultimap);
            transitionArgumentMultimap.put(CliSyntax.PREFIX_LIST_INDEX, "hello");
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
                typicalPdfIndexState.transition(new ArgumentMultimap()));
    }

    @Test
    public void getStateConstraints() {
        assertEquals(typicalPdfIndexState.getStateConstraints(), "PDF command initiated. Please enter the index of the"
                + " engagement you would like to generate a report for. The index should be prefixed by \"i/\".");
    }

    @Test
    public void getPrefix() {
        assertEquals(typicalPdfIndexState.getPrefix(), new Prefix("i/"));
    }
}
