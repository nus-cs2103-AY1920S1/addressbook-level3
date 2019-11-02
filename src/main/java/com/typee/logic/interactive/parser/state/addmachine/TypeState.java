package com.typee.logic.interactive.parser.state.addmachine;

import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_ENGAGEMENT_TYPE;

import java.util.Map;

import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.state.State;
import com.typee.logic.interactive.parser.state.StateTransitionException;
import com.typee.logic.parser.Prefix;
import com.typee.logic.parser.exceptions.ParseException;

public class TypeState implements State {

    private static final String MESSAGE_CONSTRAINTS = "The engagement should be an appointment,"
            + " meeting or interview.";


    /*
    @Override
    public void processInput(String commandText) throws IllegalArgumentException {
        if (isValid(commandText)) {
            argumentMultimap.put(PREFIX_ENGAGEMENT_TYPE, commandText);
        } else {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
    }
     */

    @Override
    public State transition(ArgumentMultimap argumentMultimap) throws StateTransitionException {
        return
    }

    @Override
    public String getStateConstraints() {
        return MESSAGE_CONSTRAINTS;
    }

    @Override
    public boolean isEndState() {
        return false;
    }

    private boolean isValid(String commandText) {
        return commandText.equalsIgnoreCase("Meeting")
                || commandText.equalsIgnoreCase("Interview")
                || commandText.equalsIgnoreCase("Appointment");
    }
}
