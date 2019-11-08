package com.typee.logic.interactive.parser.state.findmachine;

import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_PRIORITY;
import static java.util.Objects.requireNonNull;

import java.util.Optional;

import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.Prefix;
import com.typee.logic.interactive.parser.state.OptionalState;
import com.typee.logic.interactive.parser.state.PenultimateState;
import com.typee.logic.interactive.parser.state.State;
import com.typee.logic.interactive.parser.state.exceptions.StateTransitionException;
import com.typee.model.engagement.Priority;

/**
 * Represents an optional state (also penultimate state) in the state machine that builds the {@code FindCommand}.
 * Accepts a priority level.
 */
public class FindPriorityState extends PenultimateState implements OptionalState {

    private static final String MESSAGE_CONSTRAINTS = "Please enter the priority to search for, prefixed by \"p/\".";

    protected FindPriorityState(ArgumentMultimap soFar) {
        super(soFar);
    }

    @Override
    public State transition(ArgumentMultimap newArgs) throws StateTransitionException {
        requireNonNull(newArgs);

        Optional<String> priority = newArgs.getValue(PREFIX_PRIORITY);
        performGuardChecks(newArgs, priority);
        if (priority.isPresent()) {
            collateArguments(this, newArgs, PREFIX_PRIORITY);
        }

        enforceNoExcessiveArguments(newArgs);

        return new FindEndState(soFar);
    }

    /**
     * Ensures that no arguments are duplicated.
     * If a priority level is input, ensures that it is valid.
     *
     * @param newArgs Unprocessed arguments.
     * @param priority Priority level.
     * @throws StateTransitionException If the priority level is invalid.
     */
    private void performGuardChecks(ArgumentMultimap newArgs, Optional<String> priority)
            throws StateTransitionException {
        disallowDuplicatePrefix(newArgs);
        if (priority.isPresent()) {
            enforceValidity(priority.get());
        }
    }

    private void enforceValidity(String priority) throws StateTransitionException {
        if (!Priority.isValid(priority)) {
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

    @Override
    public boolean canBeSkipped(ArgumentMultimap newArgs) {
        if (newArgs.getValue(PREFIX_PRIORITY).isPresent()) {
            return false;
        }
        return true;
    }
}
