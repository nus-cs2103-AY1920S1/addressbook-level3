package com.typee.logic.interactive.parser.state;

import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.state.exceptions.PenultimateStateTransitionException;

/**
 * Represents the penultimate state of a finite-state machine.
 * Handles the final argument entered by the user for a particular command.
 */
public abstract class PenultimateState extends State {

    private static final String MESSAGE_EXCESSIVE_ARGUMENTS = "Excessive arguments supplied!"
            + " Command cannot be executed."
            + " Please enter \"// current\" to view the current state information.";

    protected PenultimateState(ArgumentMultimap soFar) {
        super(soFar);
    }

    /**
     * Enforces the absence of unprocessed arguments.
     *
     * @param args Unprocessed arguments.
     * @throws PenultimateStateTransitionException If excessive unprocessed arguments are present.
     */
    protected void enforceNoExcessiveArguments(ArgumentMultimap args) throws PenultimateStateTransitionException {
        if (!args.isEmpty()) {
            throw new PenultimateStateTransitionException(MESSAGE_EXCESSIVE_ARGUMENTS);
        }
    }

}
