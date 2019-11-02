package com.typee.logic.interactive.parser.state.addmachine;

import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_ENGAGEMENT_TYPE;

import java.util.Map;
import java.util.Optional;

import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.state.State;
import com.typee.logic.interactive.parser.state.StateTransitionException;
import com.typee.logic.parser.Prefix;
import com.typee.logic.parser.exceptions.ParseException;

public class TypeState implements State {

    private static final String MESSAGE_CONSTRAINTS = "The engagement should be an appointment,"
            + " meeting or interview.";
    private static final String MESSAGE_MISSING_KEYWORD = "Please enter a valid engagement type following"
            + " \"t/\".";


    @Override
    public State transition(ArgumentMultimap argumentMultimap) throws StateTransitionException {
        Optional<String> typeValue = argumentMultimap.getValue(PREFIX_ENGAGEMENT_TYPE);
        if (typeValue.isEmpty()) {
            throw new StateTransitionException(MESSAGE_MISSING_KEYWORD);
        }

        if (!isValid(typeValue.get())) {
            throw new StateTransitionException(MESSAGE_CONSTRAINTS);
        }


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
