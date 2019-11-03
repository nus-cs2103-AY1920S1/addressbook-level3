package com.typee.logic.interactive.parser.state.addmachine;

import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_ATTENDEES;
import static java.util.Objects.requireNonNull;

import java.util.Optional;

import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.state.State;
import com.typee.logic.interactive.parser.state.StateTransitionException;
import com.typee.logic.parser.Prefix;

public class AttendeesState extends State {

    protected AttendeesState(ArgumentMultimap soFar) {
        super(soFar);
    }

    @Override
    public State transition(ArgumentMultimap newArgs) throws StateTransitionException {
        requireNonNull(newArgs);

        Optional<String> attendees = newArgs.getValue(PREFIX_ATTENDEES);
        performGuardChecks(newArgs, attendees);
        collateArguments(this, newArgs, PREFIX_ATTENDEES);

        return new PriorityState(soFar);
    }

    private void performGuardChecks(ArgumentMultimap newArgs) {
        
    }

    @Override
    public String getStateConstraints() {
        return null;
    }

    @Override
    public boolean isEndState() {
        return false;
    }

    @Override
    public Prefix getPrefix() {
        return PREFIX_ATTENDEES;
    }
}
