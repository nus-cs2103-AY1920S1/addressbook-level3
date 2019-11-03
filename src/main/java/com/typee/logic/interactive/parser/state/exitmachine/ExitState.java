package com.typee.logic.interactive.parser.state.exitmachine;

import com.typee.logic.commands.Command;
import com.typee.logic.commands.ExitCommand;
import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.state.EndState;
import com.typee.logic.interactive.parser.state.EndStateException;
import com.typee.logic.interactive.parser.state.State;
import com.typee.logic.interactive.parser.state.StateTransitionException;
import com.typee.logic.parser.Prefix;

public class ExitState extends EndState {

    private static final String MESSAGE_GOODBYE = "Goodbye!";
    private static final String MESSAGE_TRANSITION_FROM_END = "Cannot transition from an end state!";

    public ExitState(ArgumentMultimap soFar) {
        super(soFar);
    }

    @Override
    public Command buildCommand() {
        return new ExitCommand();
    }

    @Override
    public State transition(ArgumentMultimap newArgs) throws EndStateException {
        throw new EndStateException(MESSAGE_TRANSITION_FROM_END);
    }

    @Override
    public String getStateConstraints() {
        return MESSAGE_GOODBYE;
    }

    @Override
    public boolean isEndState() {
        return true;
    }

    @Override
    public Prefix getPrefix() {
        return new Prefix("");
    }
}
