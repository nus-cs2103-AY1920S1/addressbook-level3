package com.typee.logic.interactive.parser.state.addmachine;

import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_ENGAGEMENT_TYPE;
import static java.util.Objects.requireNonNull;

import java.util.Optional;

import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.Prefix;
import com.typee.logic.interactive.parser.state.State;
import com.typee.logic.interactive.parser.state.exceptions.StateTransitionException;
import com.typee.model.engagement.EngagementType;

/**
 * Represents the initial state of the finite state machine that builds the {@code AddCommand}.
 */
public class TypeState extends State {

    private static final String MESSAGE_CONSTRAINTS = "Add Command initiated. Please enter a valid engagement type"
            + " after the prefix \"t/\".";
    private static final String MESSAGE_MISSING_KEYWORD = "Please enter a valid engagement type following"
            + " \"t/\".";

    public TypeState(ArgumentMultimap soFar) {
        super(soFar);
    }

    @Override
    public State transition(ArgumentMultimap newArgs) throws StateTransitionException {
        requireNonNull(newArgs);

        Optional<String> typeValue = newArgs.getValue(PREFIX_ENGAGEMENT_TYPE);
        performGuardChecks(newArgs, typeValue);
        collateArguments(this, newArgs, PREFIX_ENGAGEMENT_TYPE);

        return new StartDateState(soFar);
    }

    private void performGuardChecks(ArgumentMultimap newArgs, Optional<String> typeValue)
            throws StateTransitionException {
        disallowDuplicatePrefix(newArgs);
        requireKeywordPresence(typeValue, MESSAGE_MISSING_KEYWORD);
        enforceValidity(typeValue);
    }

    private void enforceValidity(Optional<String> typeValue) throws StateTransitionException {
        if (!EngagementType.isValid((typeValue.get()))) {
            throw new StateTransitionException(EngagementType.getMessageConstraints());
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
        return PREFIX_ENGAGEMENT_TYPE;
    }

}
