package com.typee.logic.interactive.parser.state.findmachine;

import static java.util.Objects.requireNonNull;

import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.Prefix;
import com.typee.logic.interactive.parser.state.State;
import com.typee.logic.interactive.parser.state.exceptions.StateTransitionException;

/**
 * Represents the first state of the state machine that builds the {@code FindCommand}.
 */
public class FindBufferState extends State {

    private static final String MESSAGE_CONSTRAINTS = "Find command initiated. Please enter the description, location,"
            + " attendees and priority to search for, prefixed by \"d/\", \"l/\", \"a/\" and \"p/\" respectively."
            + " All parameters are optional. However, at least one parameter should be specified.";

    public FindBufferState(ArgumentMultimap soFar) {
        super(soFar);
    }

    @Override
    public State transition(ArgumentMultimap newArgs) throws StateTransitionException {
        requireNonNull(newArgs);
        return new FindDescriptionState(soFar);
    }

    @Override
    public String getStateConstraints() {
        return MESSAGE_CONSTRAINTS;
    }

    @Override
    public boolean isEndState() {
        return false;
    }

    @Override
    public Prefix getPrefix() {
        return new Prefix("");
    }
}
