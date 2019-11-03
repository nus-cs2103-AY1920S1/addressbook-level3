package com.typee.logic.interactive.parser.state.addmachine;

import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_START_TIME;
import static java.util.Objects.requireNonNull;

import java.util.Optional;

import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.state.State;
import com.typee.logic.interactive.parser.state.StateTransitionException;

public class StartDateState implements State {

    private static final String MESSAGE_CONSTRAINTS = "The start time must conform to the dd/mm/yyyy/hhmm format.";

    private ArgumentMultimap soFar;

    public StartDateState(ArgumentMultimap soFar) {
        this.soFar = soFar;
    }

    @Override
    public State transition(ArgumentMultimap newArgs) throws StateTransitionException {
        requireNonNull(newArgs);

        Optional<String> startDate = newArgs.getValue(PREFIX_START_TIME);

        if (startDate.isEmpty()) {
            throw new StateTransitionException()
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
}
