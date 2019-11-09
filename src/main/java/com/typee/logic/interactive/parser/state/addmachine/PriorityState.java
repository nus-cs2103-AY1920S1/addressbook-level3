package com.typee.logic.interactive.parser.state.addmachine;

import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_PRIORITY;
import static java.util.Objects.requireNonNull;

import java.util.Optional;

import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.Prefix;
import com.typee.logic.interactive.parser.state.PenultimateState;
import com.typee.logic.interactive.parser.state.State;
import com.typee.logic.interactive.parser.state.exceptions.StateTransitionException;
import com.typee.model.engagement.Priority;

/**
 * Represents the penultimate state of the finite state machine that builds the {@code AddCommand}.
 * Handles input of the priority level.
 */
public class PriorityState extends PenultimateState {

    private static final String MESSAGE_CONSTRAINTS = "The priority of an engagement can be LOW, MEDIUM, HIGH or NONE."
            + " Please enter the priority prefixed by \"p/\".";
    private static final String MESSAGE_MISSING_KEYWORD = "Invalid input! Please enter the priority prefixed by"
            + " \"p/\". The priority can be one of LOW, MEDIUM, HIGH or NONE.";

    protected PriorityState(ArgumentMultimap soFar) {
        super(soFar);
    }

    @Override
    public State transition(ArgumentMultimap newArgs) throws StateTransitionException {
        requireNonNull(newArgs);

        Optional<String> priority = newArgs.getValue(PREFIX_PRIORITY);
        performGuardChecks(newArgs, priority);
        collateArguments(this, newArgs, PREFIX_PRIORITY);

        enforceNoExcessiveArguments(newArgs);

        return new AddCommandEndState(soFar);
    }

    private void performGuardChecks(ArgumentMultimap newArgs, Optional<String> priority)
            throws StateTransitionException {
        disallowDuplicatePrefix(newArgs);
        requireKeywordPresence(priority, MESSAGE_MISSING_KEYWORD);
        enforceValidity(priority);
    }

    private void enforceValidity(Optional<String> priority) throws StateTransitionException {
        if (!Priority.isValid(priority.get())) {
            throw new StateTransitionException(Priority.getMessageConstraints());
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
        return PREFIX_PRIORITY;
    }
}
