package com.typee.logic.interactive.parser.state.addmachine;

import com.typee.logic.commands.Command;
import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.state.EndState;
import com.typee.logic.interactive.parser.state.State;
import com.typee.logic.interactive.parser.state.StateTransitionException;

public class EndDateState extends EndState {

    private static final String MESSAGE_CONSTRAINTS = "The start time must conform to the dd/mm/yyyy/hhmm format.";

    protected EndDateState(ArgumentMultimap soFar) {
        super(soFar);
    }

    @Override
    public Command buildCommand() {
        return null;
    }

    @Override
    public State transition(ArgumentMultimap newArgs) throws StateTransitionException {
        throw new StateTransitionException("Cannot transition from an end-state.");
    }

    @Override
    public String getStateConstraints() {
        return MESSAGE_CONSTRAINTS;
    }

    @Override
    public boolean isEndState() {
        return true;
    }
}
