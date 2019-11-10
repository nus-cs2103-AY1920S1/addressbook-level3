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

    private static final String MESSAGE_INVALID_DESCRIPTION = "The description of an engagement cannot be empty.";
    private static final String MESSAGE_MISSING_KEYWORD = "Please enter the description after the prefix \"d/\".";
    private static final String MESSAGE_CONSTRAINTS = "Please enter a non-blank description prefixed by \"d/\".";

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
        requireKeywordPresence(description, MESSAGE_MISSING_KEYWORD);
        enforceValidity(description);
    }

    private void enforceValidity(Optional<String> description) throws StateTransitionException {
        if (description.get().isBlank()) {
            throw new StateTransitionException(MESSAGE_INVALID_DESCRIPTION);
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
