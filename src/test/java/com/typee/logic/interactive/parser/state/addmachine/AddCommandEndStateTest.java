package com.typee.logic.interactive.parser.state.addmachine;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.typee.logic.commands.AddCommand;
import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.Prefix;
import com.typee.logic.interactive.parser.state.State;
import com.typee.logic.interactive.parser.state.exceptions.StateTransitionException;

class AddCommandEndStateTest {

    @Test
    void transition_throwsStateTransitionException() {
        State state = new AddCommandEndState(new ArgumentMultimap());
        assertThrows(StateTransitionException.class, () -> state.transition(new ArgumentMultimap()));
    }

    @Test
    void getStateConstraints() {
        State state = new AddCommandEndState(new ArgumentMultimap());
        assertEquals(state.getStateConstraints(), "Engagement successfully added!");
    }

    @Test
    void isEndState() {
        State state = new AddCommandEndState(new ArgumentMultimap());
        assertTrue(state.isEndState());
    }

    @Test
    void getPrefix() {
        State state = new AddCommandEndState(new ArgumentMultimap());
        assertEquals(state.getPrefix(), new Prefix(""));
    }
}