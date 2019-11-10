package com.typee.logic.interactive.parser.state.currentmachine;

import com.typee.logic.commands.Command;
import com.typee.logic.commands.CurrentCommand;
import com.typee.logic.commands.exceptions.CommandException;
import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.Prefix;
import com.typee.logic.interactive.parser.state.EndState;
import com.typee.logic.interactive.parser.state.State;
import com.typee.logic.interactive.parser.state.exceptions.StateTransitionException;

/**
 * Represents the end state of the state machines that builds the {@code CurrentCommand}.
 */
public class CurrentState extends EndState {

    private static final String MESSAGE_CONSTRAINTS = "The parser is currently inactive."
            + " Please enter a command to get started.";

    private String currentMessage;

    public CurrentState(String currentMessage) {
        super(new ArgumentMultimap());
        this.currentMessage = currentMessage;
    }

    @Override
    public Command buildCommand() throws CommandException {
        return new CurrentCommand(currentMessage);
    }

    @Override
    public State transition(ArgumentMultimap newArgs) throws StateTransitionException {
        throw new StateTransitionException(MESSAGE_END_STATE);
    }

    @Override
    public String getStateConstraints() {
        return MESSAGE_CONSTRAINTS;
    }

    @Override
    public boolean isEndState() {
        return true;
    }

    @Override
    public Prefix getPrefix() {
        return DUMMY_PREFIX;
    }
}
