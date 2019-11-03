package com.typee.logic.interactive.parser.state;

import static java.util.Objects.requireNonNull;

import java.util.Map;

import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.parser.Prefix;
import com.typee.logic.parser.exceptions.ParseException;

public abstract class State {

    private static final String MESSAGE_DUPLICATE_PREFIX = "You've supplied arguments to the same parameter more"
            + " than once. Please avoid duplication.";

    protected final ArgumentMultimap soFar;

    protected State(ArgumentMultimap soFar) {
        requireNonNull(soFar);
        this.soFar = soFar;
    }

    public abstract State transition(ArgumentMultimap newArgs) throws StateTransitionException;

    public abstract String getStateConstraints();

    public abstract boolean isEndState();

    private abstract isValid(String argument);

    protected void disallowDuplicatePrefix(ArgumentMultimap newArgs) throws StateTransitionException {
        if (!soFar.isDisjointWith(newArgs)) {
            throw new StateTransitionException(MESSAGE_DUPLICATE_PREFIX);
        }
    }

}
