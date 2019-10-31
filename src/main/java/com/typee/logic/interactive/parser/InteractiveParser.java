package com.typee.logic.interactive.parser;

import com.typee.logic.commands.Command;
import com.typee.logic.commands.CommandResult;

public interface InteractiveParser {
    void parseCommand(String commandText);

    CommandResult fetchResult();

    boolean hasParsedCommand();

    Command makeCommand();

}
