package com.typee.logic.interactive.parser.state;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.Prefix;
import com.typee.logic.interactive.parser.state.exceptions.StateTransitionException;

/**
 * Represents an abstract state. Each state is a unit that is a part of a finite state machine.
 */
public abstract class State {

    private static final String MESSAGE_DUPLICATE_PREFIX = "You've supplied arguments to the same parameter more"
            + " than once. Please avoid duplication.";

    /** ArgumentMultimap that tracks inputs up until the current state of the finite state machine. */
    protected final ArgumentMultimap soFar;

    protected State(ArgumentMultimap soFar) {
        requireNonNull(soFar);
        this.soFar = soFar;
    }

    /**
     * Transitions the current state into the next state.
     *
     * @param newArgs {@cod ArgumentMultimap} containing unprocessed arguments.
     * @return next {@code State}.
     * @throws StateTransitionException If a transition is not possible.
     */
    public abstract State transition(ArgumentMultimap newArgs) throws StateTransitionException;

    /**
     * Returns the constraints on the input handled by the state.
     *
     * @return the input constraints.
     */
    public abstract String getStateConstraints();

    /**
     * Returns true if the {@code State} is the accepting state of the finite-state machine, i.e. the {@code EndState}.
     *
     * @return true if the state is the last state.
     */
    public abstract boolean isEndState();

    /**
     * Returns the {@code Prefix} whose argument is processed.
     *
     * @return the {@code Prefix}.
     */
    public abstract Prefix getPrefix();

    /**
     * Checks if the argument specified is present.
     * If not, the specified error message is thrown.
     *
     * @param keywordArgument Argument that needs to be present.
     * @param errorMessage {@code String} to be shown to the user if the argument is absent.
     * @throws StateTransitionException If the argument is absent.
     */
    protected void requireKeywordPresence(Optional<String> keywordArgument, String errorMessage)
            throws StateTransitionException {
        if (keywordArgument.isEmpty()) {
            throw new StateTransitionException(errorMessage);
        }
    }

    /**
     * Disallows the input of duplicate prefixes.
     *
     * @param newArgs {@code ArgumentMultimap} to check duplication in.
     * @throws StateTransitionException If duplicate inputs are present for the same prefixes.
     */
    protected void disallowDuplicatePrefix(ArgumentMultimap newArgs) throws StateTransitionException {
        if (!soFar.isDisjointWith(newArgs)) {
            throw new StateTransitionException(MESSAGE_DUPLICATE_PREFIX);
        }
    }

    /**
     * Collates the argument processed by the input {@code State} and appends it to the multimap of arguments
     * processed so far.
     *
     * @param state {@code State} that processes the input.
     * @param newArgs Unprocessed arguments.
     * @param prefix Prefix (parameter) handled by the input state.
     */
    protected void collateArguments(State state, ArgumentMultimap newArgs, Prefix prefix) {
        String value = newArgs.getValue(prefix).get();
        state.soFar.put(prefix, value);
        newArgs.clearValues(prefix);
    }

}
