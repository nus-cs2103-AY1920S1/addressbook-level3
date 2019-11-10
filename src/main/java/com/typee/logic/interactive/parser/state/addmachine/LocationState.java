package com.typee.logic.interactive.parser.state.addmachine;

import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_LOCATION;
import static java.util.Objects.requireNonNull;

import java.util.Optional;

import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.Prefix;
import com.typee.logic.interactive.parser.state.State;
import com.typee.logic.interactive.parser.state.exceptions.StateTransitionException;
import com.typee.model.engagement.Location;

/**
 * Represents the state of the finite state machine that builds the {@code AddCommand}.
 * Handles input of the location.
 */
public class LocationState extends State {

    private static final String MESSAGE_CONSTRAINTS = "Please enter the location of the meeting,"
            + " prefixed by \"l/\".";
    private static final String MESSAGE_MISSING_KEYWORD = "Please enter a valid location after the prefix \"l/\".";

    protected LocationState(ArgumentMultimap soFar) {
        super(soFar);
    }

    @Override
    public State transition(ArgumentMultimap newArgs) throws StateTransitionException {
        requireNonNull(newArgs);

        Optional<String> location = newArgs.getValue(PREFIX_LOCATION);
        performGuardChecks(newArgs, location);
        collateArguments(this, newArgs, PREFIX_LOCATION);

        return new DescriptionState(soFar);
    }

    private void performGuardChecks(ArgumentMultimap newArgs, Optional<String> location)
            throws StateTransitionException {
        disallowDuplicatePrefix(newArgs);
        requireKeywordPresence(location, MESSAGE_MISSING_KEYWORD);
        enforceValidity(location);
    }

    private void enforceValidity(Optional<String> location) throws StateTransitionException {
        if (!Location.isValid((location.get()))) {
            throw new StateTransitionException(Location.MESSAGE_CONSTRAINTS);
        }
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
        return PREFIX_LOCATION;
    }
}
