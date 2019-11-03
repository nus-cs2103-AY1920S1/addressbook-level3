package com.typee.logic.interactive.parser.state.addmachine;

import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_PRIORITY;
import static java.util.Objects.requireNonNull;

import java.util.Optional;

import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.state.State;
import com.typee.logic.interactive.parser.state.StateTransitionException;
import com.typee.logic.parser.Prefix;

public class PriorityState extends State {

    private static final String MESSAGE_CONSTRAINTS = "The priority of an engagement can be LOW, MEDIUM, HIGH or NONE."
            + " Please enter the priority prefixed by \"p/\".";

    protected PriorityState(ArgumentMultimap soFar) {
        super(soFar);
    }

    @Override
    public State transition(ArgumentMultimap newArgs) throws StateTransitionException {
        requireNonNull(newArgs);

        Optional<String> priority = newArgs.getValue(PREFIX_PRIORITY);
        performGuardChecks(newArgs, priority);
        collateArguments(this, newArgs, PREFIX_PRIORITY);

        return null;
    }

    private void performGuardChecks(ArgumentMultimap newArgs, Optional<String> priority)
            throws StateTransitionException {
        disallowDuplicatePrefix(newArgs);
        requireKeywordPresence(priority);
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
