package com.typee.logic.interactive.parser;

import java.util.Map;

import com.typee.logic.parser.exceptions.ParseException;

public interface State {
    void processInput(String commandText);

    State transition(Map<String, String> arguments) throws ParseException;

    String getStateConstraints();

    boolean isEndState();
}
