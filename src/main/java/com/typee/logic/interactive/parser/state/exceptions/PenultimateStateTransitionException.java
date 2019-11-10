package com.typee.logic.interactive.parser.state.exceptions;

/**
 * Represents an exceptional situation where excessive arguments are supplied.
 */
public class PenultimateStateTransitionException extends StateTransitionException {

    public PenultimateStateTransitionException(String message) {
        super(message);
    }

}
