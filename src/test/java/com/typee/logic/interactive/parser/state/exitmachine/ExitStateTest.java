package com.typee.logic.interactive.parser.state.exitmachine;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.typee.logic.commands.ExitCommand;
import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.Prefix;
import com.typee.logic.interactive.parser.state.exceptions.StateTransitionException;

public class ExitStateTest {
    private ExitState typicalExitState = new ExitState(new ArgumentMultimap());

    @Test
    public void transition() {
        assertThrows(StateTransitionException.class, () -> typicalExitState.transition(new ArgumentMultimap()));
    }

    @Test
    public void buildCommand() {
        assertDoesNotThrow(() -> assertEquals(typicalExitState.buildCommand(), new ExitCommand()));
    }

    @Test
    public void getStateConstraints() {
        assertEquals(typicalExitState.getStateConstraints(), "Goodbye!");
    }

    @Test
    public void isEndState() {
        assertTrue(typicalExitState.isEndState());
    }

    @Test
    public void getPrefix() {
        assertEquals(typicalExitState.getPrefix(), new Prefix(""));
    }
}
