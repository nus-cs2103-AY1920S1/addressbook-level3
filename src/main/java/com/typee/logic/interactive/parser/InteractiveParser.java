package com.typee.logic.interactive.parser;

import com.typee.logic.commands.Command;
import com.typee.logic.commands.CommandResult;
import com.typee.logic.parser.exceptions.ParseException;

public interface InteractiveParser {
    void parseInput(String commandText) throws ParseException;

    CommandResult fetchResult();

    boolean hasParsedCommand();

    Command makeCommand();

}
