package com.typee.logic.interactive.parser.state;

import java.util.Map;

import com.typee.logic.parser.exceptions.ParseException;

public interface State {
    void processInput(String commandText);

    State transition(Map<String, String> arguments);

    String getStateConstraints();

    boolean isEndState();
}
