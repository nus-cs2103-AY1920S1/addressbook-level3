package com.dukeacademy.logic.commands;

import com.dukeacademy.logic.commands.exceptions.CommandException;
import com.dukeacademy.logic.parser.exceptions.ParseException;

public interface CommandLogic {
    CommandResult executeCommand(String commandText) throws ParseException, CommandException;
}
