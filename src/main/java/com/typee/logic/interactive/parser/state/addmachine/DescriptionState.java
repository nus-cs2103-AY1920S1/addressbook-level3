package com.typee.logic.interactive.parser.state.addmachine;

import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_DESCRIPTION;
import static java.util.Objects.requireNonNull;

import java.util.Optional;

import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.Prefix;
import com.typee.logic.interactive.parser.state.State;
import com.typee.logic.interactive.parser.state.exceptions.StateTransitionException;

/**
 * Represents the state of the finite state machine that builds the {@code AddCommand}.
 * Handles input of the description.
 */
public class DescriptionState extends State {

    private static final String MESSAGE_INVALID_INPUT = "Invalid input! Please enter a valid description after "
            + PREFIX_DESCRIPTION.getPrefix() + ". The description cannot be blank and should be less than"
            + " 100 characters in length.";
    private static final String MESSAGE_CONSTRAINTS = "What is the engagement about? Please enter a brief description "
            + "prefixed by " + PREFIX_DESCRIPTION.getPrefix() + ". Example - [d/CS2103T Discussion.]";
    private static final int MAXIMUM_ALLOWED_LENGTH = 100;

    protected DescriptionState(ArgumentMultimap soFar) {
        super(soFar);
    }

    @Override
    public State transition(ArgumentMultimap newArgs) throws StateTransitionException {
        requireNonNull(newArgs);

        Optional<String> description = newArgs.getValue(PREFIX_DESCRIPTION);
        performGuardChecks(newArgs, description);
        collateArguments(this, newArgs, PREFIX_DESCRIPTION);

        return new AttendeesState(soFar);
    }

    private void performGuardChecks(ArgumentMultimap newArgs, Optional<String> description)
            throws StateTransitionException {
        disallowDuplicatePrefix(newArgs);
        requireKeywordPresence(description, MESSAGE_INVALID_INPUT);
        enforceValidity(description);
    }

    private void enforceValidity(Optional<String> description) throws StateTransitionException {
        if (isInvalid(description)) {
            throw new StateTransitionException(MESSAGE_INVALID_INPUT);
        }
    }

    private boolean isInvalid(Optional<String> description) {
        return description.get().isBlank() || description.get().length() >= MAXIMUM_ALLOWED_LENGTH;
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
