package com.typee.logic.interactive.parser.state;

import com.typee.logic.interactive.parser.ArgumentMultimap;

/**
 * Represents a state that handles an optional input.
 */
public interface OptionalState {
    /**
     * Returns true if the state can be skipped.
     * A state can be skipped if no arguments are supplied.
     *
     * @param newArgs Arguments to be processed.
     * @return true if the state can be skipped.
     */
    public boolean canBeSkipped(ArgumentMultimap newArgs);
}
