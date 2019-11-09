package com.typee.logic.interactive.parser.state.currentmachine;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.typee.logic.commands.CurrentCommand;
import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.Prefix;
import com.typee.logic.interactive.parser.state.exceptions.StateTransitionException;

public class CurrentStateTest {
    private static final String CURRENT_MESSAGE = "Current message";
    private CurrentState typicalCurrentState = new CurrentState(CURRENT_MESSAGE);

    @Test
    public void transition() {
        assertThrows(StateTransitionException.class, () -> typicalCurrentState.transition(new ArgumentMultimap()));
    }

    @Test
    public void buildCommand() {
        assertDoesNotThrow(() -> assertEquals(typicalCurrentState.buildCommand(), new CurrentCommand(CURRENT_MESSAGE)));
    }

    @Test
    public void getStateConstraints() {
        assertEquals(typicalCurrentState.getStateConstraints(), "The parser is currently inactive."
                + " Please enter a command to get started.");
    }

    @Test
    public void isEndState() {
        assertTrue(typicalCurrentState.isEndState());
    }

    @Test
    public void getPrefix() {
        assertEquals(typicalCurrentState.getPrefix(), new Prefix(""));
    }

}
