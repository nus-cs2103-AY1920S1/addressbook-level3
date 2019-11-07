package com.typee.logic.interactive.parser.state;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.Prefix;
import com.typee.logic.interactive.parser.state.exceptions.StateTransitionException;

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

    public abstract Prefix getPrefix();

    protected void requireKeywordPresence(Optional<String> keywordArgument, String errorMessage)
            throws StateTransitionException {
        if (keywordArgument.isEmpty()) {
            throw new StateTransitionException(errorMessage);
        }
    }

    protected void disallowDuplicatePrefix(ArgumentMultimap newArgs) throws StateTransitionException {
        if (!soFar.isDisjointWith(newArgs)) {
            throw new StateTransitionException(MESSAGE_DUPLICATE_PREFIX);
        }
    }

    protected void collateArguments(State state, ArgumentMultimap newArgs, Prefix prefix) {
        String value = newArgs.getValue(prefix).get();
        state.soFar.put(prefix, value);
        newArgs.clearValues(prefix);
    }

}
