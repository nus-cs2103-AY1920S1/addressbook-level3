package com.typee.logic.interactive.parser.state.exceptions;

/**
 * Represents an exceptional situation where a transition to the next state in a state machine is invalid.
 */
public class StateTransitionException extends Exception {

    public StateTransitionException(String message) {
        super(message);
    }

}
