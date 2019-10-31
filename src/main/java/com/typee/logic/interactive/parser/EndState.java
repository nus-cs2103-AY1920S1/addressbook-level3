package com.typee.logic.interactive.parser;

import com.typee.logic.commands.Command;

public interface EndState extends State {
    Command buildCommand();
}
