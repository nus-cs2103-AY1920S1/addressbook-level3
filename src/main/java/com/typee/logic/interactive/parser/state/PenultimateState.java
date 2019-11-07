package com.typee.logic.interactive.parser.state;

import com.typee.logic.interactive.parser.ArgumentMultimap;

public abstract class PenultimateState extends State {

    private final String MESSAGE_EXCESSIVE_ARGUMENTS = "Excessive arguments supplied! Command cannot be executed."
            + " Please enter \"// current\" to view the current state information.";

    protected PenultimateState(ArgumentMultimap soFar) {
        super(soFar);
    }

    protected void enforceNoExcessiveArguments(ArgumentMultimap args) throws StateTransitionException {
        if (!args.isEmpty()) {
            throw new StateTransitionException(MESSAGE_EXCESSIVE_ARGUMENTS);
        }
    }

}
