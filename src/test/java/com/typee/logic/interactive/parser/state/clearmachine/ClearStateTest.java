package com.typee.logic.interactive.parser.state.clearmachine;

import static com.typee.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.typee.logic.commands.ClearCommand;
import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.Prefix;
import com.typee.logic.interactive.parser.state.exceptions.StateTransitionException;

class ClearStateTest {

    private ClearState clearState;

    @BeforeEach
    public void setUp() {
        ArgumentMultimap argumentMultimap = new ArgumentMultimap();
        clearState = new ClearState(argumentMultimap);
    }

    @Test
    public void buildCommand() {
        assertEquals(clearState.buildCommand(), new ClearCommand());
    }

    @Test
    public void transition() {
        ArgumentMultimap argumentMultimap = new ArgumentMultimap();
        assertThrows(StateTransitionException.class, ()
            -> clearState.transition(argumentMultimap));
    }

    @Test
    public void getStateConstraints() {
        assertEquals(clearState.getStateConstraints(), "Clear command initiated. Emptying engagement list.");
    }

    @Test
    public void isEndState() {
        assertTrue(clearState.isEndState());
    }

    @Test
    public void getPrefix() {
        assertEquals(clearState.getPrefix(), new Prefix(""));
    }

}
