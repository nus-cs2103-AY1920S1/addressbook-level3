package com.typee.logic.interactive.parser.state.addmachine;

import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_PRIORITY;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.state.State;

class PriorityStateTest {

    @Test
    void getStateConstraints_valid_returnsConstraints() {
        State state = new PriorityState(new ArgumentMultimap());
        assertEquals(state.getStateConstraints(), "The priority of an engagement can be"
                + " LOW, MEDIUM, HIGH or NONE. Please enter the priority prefixed by \"p/\".");
    }

    @Test
    void isEndState_valid_returnsFalse() {
        State state = new PriorityState(new ArgumentMultimap());
        assertFalse(state.isEndState());
    }

    @Test
    void getPrefix_valid_returnsPrefix() {
        State state = new PriorityState(new ArgumentMultimap());
        assertEquals(state.getPrefix(), PREFIX_PRIORITY);
    }
}