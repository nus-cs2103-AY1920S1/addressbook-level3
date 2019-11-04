package com.typee.logic.interactive.parser.state;

import com.typee.logic.commands.Command;
import com.typee.logic.commands.exceptions.CommandException;
import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.state.State;
import com.typee.logic.parser.exceptions.ParseException;

public abstract class EndState extends State {

    protected final String MESSAGE_END_STATE = "Cannot transition from an end state!";

    protected EndState(ArgumentMultimap soFar) {
        super(soFar);
    }

    public abstract Command buildCommand() throws CommandException;
}
