package com.typee.logic.interactive.parser.state.deletemachine;

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

class IndexStateTest {

    private IndexState indexState;

    @BeforeEach
    public void setUp() {
        ArgumentMultimap argumentMultimap = new ArgumentMultimap();
        indexState = new IndexState(argumentMultimap);
    }

    @Test
    public void transition_validArgumentMultimap_returnsPostTransitionState() {
        try {
            ArgumentMultimap validArgumentMultimap = new ArgumentMultimap();
            validArgumentMultimap.put(CliSyntax.PREFIX_LIST_INDEX, "1");

            ArgumentMultimap transitionArgumentMultimap = new ArgumentMultimap();
            transitionArgumentMultimap.put(CliSyntax.PREFIX_LIST_INDEX, "1");
            State postTransitionState = indexState.transition(transitionArgumentMultimap);

            assertEquals(postTransitionState, new IndexState(validArgumentMultimap));
        } catch (StateTransitionException e) {
            // StateTransitionException should not be thrown here.
        }
    }

    @Test
    public void transition_tooManyArguments_throwsStateTransitionException() {
        try {
            ArgumentMultimap transitionArgumentMultimap = new ArgumentMultimap();
            transitionArgumentMultimap.put(CliSyntax.PREFIX_LIST_INDEX, "1");
            State postTransitionState = indexState.transition(transitionArgumentMultimap);
            transitionArgumentMultimap.put(CliSyntax.PREFIX_LIST_INDEX, "1");
            State finalPostTransitionState = postTransitionState;
            assertThrows(StateTransitionException.class, ()
                -> finalPostTransitionState.transition(transitionArgumentMultimap));
        } catch (StateTransitionException e) {
            // StateTransitionException should not be thrown here.
        }
    }

    @Test
    public void transition_invalidIndex_throwsStateTransitionException() {
        ArgumentMultimap transitionArgumentMultimap = new ArgumentMultimap();
        transitionArgumentMultimap.put(CliSyntax.PREFIX_LIST_INDEX, "0");
        State finalPostTransitionState = indexState;
        assertThrows(StateTransitionException.class, ()
            -> finalPostTransitionState.transition(transitionArgumentMultimap));
    }

    @Test
    public void transition_invalidArgumentMultimap_throwsStateTransitionException() {
        ArgumentMultimap invalidArgumentMultimap = new ArgumentMultimap();
        invalidArgumentMultimap.put(new Prefix("hi"), "1");
        assertThrows(StateTransitionException.class, ()
            -> indexState.transition(invalidArgumentMultimap));
    }

    @Test
    public void getStateConstraints() {
        assertEquals(indexState.getStateConstraints(),
                "Delete command initiated. Please enter the index of the"
                + " engagement you would like to delete. The index should be prefixed by \"i/\".");
    }

    @Test
    public void isEndState() {
        assertFalse(indexState.isEndState());
    }

    @Test
    public void getPrefix() {
        assertEquals(indexState.getPrefix(), CliSyntax.PREFIX_LIST_INDEX);
    }

}
