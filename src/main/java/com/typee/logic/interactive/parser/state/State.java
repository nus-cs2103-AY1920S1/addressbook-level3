package com.typee.logic.interactive.parser.state;

import java.util.Map;

import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.parser.Prefix;
import com.typee.logic.parser.exceptions.ParseException;

public interface State {
    State transition(ArgumentMultimap soFar, ArgumentMultimap argumentMultimap) throws StateTransitionException;

    String getStateConstraints();

    boolean isEndState();
}
