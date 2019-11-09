package com.typee.logic.interactive.parser.state.undomachine;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.typee.logic.commands.UndoCommand;
import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.Prefix;
import com.typee.logic.interactive.parser.state.exceptions.StateTransitionException;

public class UndoStateTest {
    private UndoState typicalUndoState = new UndoState(new ArgumentMultimap());

    @Test
    public void transition() {
        assertThrows(StateTransitionException.class, () -> typicalUndoState.transition(new ArgumentMultimap()));
    }

    @Test
    public void buildCommand() {
        assertDoesNotThrow(() -> assertEquals(typicalUndoState.buildCommand(), new UndoCommand()));
    }

    @Test
    public void getStateConstraints() {
        assertEquals(typicalUndoState.getStateConstraints(), "The latest undoable command will be undone.");
    }

    @Test
    public void isEndState() {
        assertTrue(typicalUndoState.isEndState());
    }

    @Test
    public void getPrefix() {
        assertEquals(typicalUndoState.getPrefix(), new Prefix(""));
    }
}
