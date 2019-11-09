package com.typee.logic.interactive.parser.state.redomachine;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.typee.logic.commands.RedoCommand;
import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.Prefix;
import com.typee.logic.interactive.parser.state.exceptions.StateTransitionException;

public class RedoStateTest {
    private RedoState typicalRedoState = new RedoState(new ArgumentMultimap());

    @Test
    public void transition() {
        assertThrows(StateTransitionException.class, () -> typicalRedoState.transition(new ArgumentMultimap()));
    }

    @Test
    public void buildCommand() {
        assertDoesNotThrow(() -> assertEquals(typicalRedoState.buildCommand(), new RedoCommand()));
    }

    @Test
    public void getStateConstraints() {
        assertEquals(typicalRedoState.getStateConstraints(), "The last undone command will be redone.");
    }

    @Test
    public void isEndState() {
        assertTrue(typicalRedoState.isEndState());
    }

    @Test
    public void getPrefix() {
        assertEquals(typicalRedoState.getPrefix(), new Prefix(""));
    }
}
