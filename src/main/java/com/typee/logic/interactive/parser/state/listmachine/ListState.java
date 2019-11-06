package com.typee.logic.interactive.parser.state.listmachine;

import com.typee.logic.commands.Command;
import com.typee.logic.commands.ListCommand;
import com.typee.logic.commands.exceptions.CommandException;
import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.Prefix;
import com.typee.logic.interactive.parser.state.EndState;
import com.typee.logic.interactive.parser.state.State;
import com.typee.logic.interactive.parser.state.StateTransitionException;

public class ListState extends EndState {

    private static final String MESSAGE_CONSTRAINTS = "Listed all commands.";

    public ListState(ArgumentMultimap soFar) {
        super(soFar);
    }

    @Override
    public Command buildCommand() throws CommandException {
        return new ListCommand();
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
