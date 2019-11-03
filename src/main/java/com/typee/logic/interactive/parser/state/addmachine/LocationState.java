package com.typee.logic.interactive.parser.state.addmachine;

import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.state.State;
import com.typee.logic.interactive.parser.state.StateTransitionException;

public class LocationState extends State {
    
    protected LocationState(ArgumentMultimap soFar) {
        super(soFar);
    }

    @Override
    public State transition(ArgumentMultimap newArgs) throws StateTransitionException {
        return null;
    }

    @Override
    public String getStateConstraints() {
        return null;
    }

    @Override
    public boolean isEndState() {
        return false;
    }
}
