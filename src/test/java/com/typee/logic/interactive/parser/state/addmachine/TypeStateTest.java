package com.typee.logic.interactive.parser.state.addmachine;

import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_ENGAGEMENT_TYPE;
import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_START_TIME;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.state.State;
import com.typee.logic.interactive.parser.state.exceptions.StateTransitionException;

class TypeStateTest {

    @Test
    void transition_validArgumentMultimapOneArgument_returnsPostTransitionState() {
        try {
            ArgumentMultimap argumentMultimap = new ArgumentMultimap();
            argumentMultimap.put(PREFIX_ENGAGEMENT_TYPE, "meeting");
            ArgumentMultimap transitionArgs = new ArgumentMultimap();
            transitionArgs.put(PREFIX_ENGAGEMENT_TYPE, "meeting");

            State state = new TypeState(new ArgumentMultimap());
            State postTransitionState = state.transition(argumentMultimap);

            assertEquals(postTransitionState, new StartDateState(transitionArgs));
        } catch (StateTransitionException e) {
            fail();
        }
    }

    @Test
    void transition_validArgumentMultimapMultipleArguments_returnsPostTransitionState() {
        ArgumentMultimap argumentMultimap = new ArgumentMultimap();
        argumentMultimap.put(PREFIX_ENGAGEMENT_TYPE, "interview");
        argumentMultimap.put(PREFIX_START_TIME, "11/11/2019/1500");
    }
}