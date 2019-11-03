package com.typee.logic.interactive.parser.state;

import com.typee.logic.commands.Command;
import com.typee.logic.interactive.parser.state.State;

public abstract class EndState extends State {
    public abstract Command buildCommand();
}
