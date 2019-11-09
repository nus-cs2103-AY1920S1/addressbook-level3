package com.typee.logic.interactive.parser.state.listmachine;

import static com.typee.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.typee.logic.commands.ListCommand;
import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.Prefix;
import com.typee.logic.interactive.parser.state.exceptions.StateTransitionException;

class ListStateTest {

    private ListState listState;

    @BeforeEach
    public void setUp() {
        ArgumentMultimap argumentMultimap = new ArgumentMultimap();
        listState = new ListState(argumentMultimap);
    }

    @Test
    public void buildCommand() {
        assertEquals(listState.buildCommand(), new ListCommand());
    }

    @Test
    public void transition() {
        ArgumentMultimap argumentMultimap = new ArgumentMultimap();
        assertThrows(StateTransitionException.class, ()
            -> listState.transition(argumentMultimap));
    }

    @Test
    public void getStateConstraints() {
        assertEquals(listState.getStateConstraints(), "Listed all engagements.");
    }

    @Test
    public void isEndState() {
        assertTrue(listState.isEndState());
    }

    @Test
    public void getPrefix() {
        assertEquals(listState.getPrefix(), new Prefix(""));
    }

}
