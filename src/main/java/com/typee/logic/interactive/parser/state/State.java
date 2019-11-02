package com.typee.logic.interactive.parser.state;

import java.util.Map;

import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.parser.Prefix;
import com.typee.logic.parser.exceptions.ParseException;

public interface State {
    State transition(ArgumentMultimap argumentMultimap);

    String getStateConstraints();

    boolean isEndState();
}
