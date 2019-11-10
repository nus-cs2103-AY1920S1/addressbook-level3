package com.typee.logic.interactive.parser.state.addmachine;

import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_LOCATION;
import static java.util.Objects.requireNonNull;

import java.util.Optional;

import com.fasterxml.jackson.databind.ser.impl.MapEntrySerializer;
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

    private static final String MESSAGE_CONSTRAINTS = "Where will the engagement be held? Please enter the location"
            + " prefixed by " + PREFIX_LOCATION.getPrefix() + ". Example - [l/I3-AUD]";
    private static final String MESSAGE_INVALID_INPUT = "Invalid input! Please enter a valid location after "
            + PREFIX_LOCATION.getPrefix() + ". The location cannot be blank.";

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
        requireKeywordPresence(location, MESSAGE_INVALID_INPUT);
        enforceValidity(location);
    }

    private void enforceValidity(Optional<String> location) throws StateTransitionException {
        if (!Location.isValid((location.get()))) {
            throw new StateTransitionException(MESSAGE_INVALID_INPUT);
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
