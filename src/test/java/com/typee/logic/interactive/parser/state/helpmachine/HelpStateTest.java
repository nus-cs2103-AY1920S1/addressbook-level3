package com.typee.logic.interactive.parser.state.helpmachine;

import static com.typee.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.typee.logic.commands.HelpCommand;
import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.Prefix;
import com.typee.logic.interactive.parser.state.exceptions.StateTransitionException;

class HelpStateTest {

    private HelpState helpState;

    @BeforeEach
    public void setUp() {
        ArgumentMultimap argumentMultimap = new ArgumentMultimap();
        helpState = new HelpState(argumentMultimap);
    }

    @Test
    public void buildCommand() {
        assertEquals(helpState.buildCommand(), new HelpCommand());
    }

    @Test
    public void transition() {
        ArgumentMultimap argumentMultimap = new ArgumentMultimap();
        assertThrows(StateTransitionException.class, ()
            -> helpState.transition(argumentMultimap));
    }

    @Test
    public void getStateConstraints() {
        assertEquals(helpState.getStateConstraints(), "Please refer to the user guide for more information.");
    }

    @Test
    public void isEndState() {
        assertTrue(helpState.isEndState());
    }

    @Test
    public void getPrefix() {
        assertEquals(helpState.getPrefix(), new Prefix(""));
    }

}
