package com.typee.logic.interactive.parser.state.findmachine;

import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_DESCRIPTION;
import static java.util.Objects.requireNonNull;

import java.util.Optional;

import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.Prefix;
import com.typee.logic.interactive.parser.state.State;
import com.typee.logic.interactive.parser.state.StateTransitionException;

public class FindDescriptionState extends State {

    private static final String MESSAGE_CONSTRAINTS = "Find command initiated! Please enter a description to"
            + " search for prefixed by \"d/\".";
    private static final String MESSAGE_INVALID_INPUT = "Invalid input. The entered description cannot be blank!";

    public FindDescriptionState(ArgumentMultimap soFar) {
        super(soFar);
    }

    @Override
    public State transition(ArgumentMultimap newArgs) throws StateTransitionException {
        requireNonNull(newArgs);

        Optional<String> description = newArgs.getValue(PREFIX_DESCRIPTION);
        performGuardChecks(newArgs, description);
        collateArguments(this, newArgs, PREFIX_DESCRIPTION);

        return new FindLocationState(soFar);
    }

    private void performGuardChecks(ArgumentMultimap newArgs, Optional<String> description)
            throws StateTransitionException {
        disallowDuplicatePrefix(newArgs);
        if (description.isPresent()) {
            enforceValidity(description.get());
        }
    }

    private void enforceValidity(String description) throws StateTransitionException {
        if (description.isBlank()) {
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
        return PREFIX_DESCRIPTION;
    }
}
