package com.typee.logic.interactive.parser.state.pdfmachine;

import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_LIST_INDEX;
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
    public static final String EXPECTED_CONSTRAINTS = "Which engagement would you like to make a PDF of? Please enter "
            + "the index of the engagement prefixed by " + PREFIX_LIST_INDEX.getPrefix() + ". Example - [i/4]";
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
        assertEquals(EXPECTED_CONSTRAINTS, typicalPdfIndexState.getStateConstraints());
    }

    @Test
    public void getPrefix() {
        assertEquals(typicalPdfIndexState.getPrefix(), new Prefix("i/"));
    }
}
