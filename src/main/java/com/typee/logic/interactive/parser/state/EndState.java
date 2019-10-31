package com.typee.logic.interactive.parser.state;

import com.typee.logic.commands.Command;
import com.typee.logic.interactive.parser.state.State;

public interface EndState extends State {
    Command buildCommand();
}
