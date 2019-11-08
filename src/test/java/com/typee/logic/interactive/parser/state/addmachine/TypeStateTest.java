package com.typee.logic.interactive.parser.state.addmachine;

import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_END_TIME;
import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_ENGAGEMENT_TYPE;
import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_ORDER;
import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_START_TIME;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.Prefix;
import com.typee.logic.interactive.parser.state.State;
import com.typee.logic.interactive.parser.state.exceptions.StateTransitionException;
import com.typee.testutil.ArgumentMultimapBuilder;

class TypeStateTest {

    @Test
    void transition_validArgumentMultimapOneArgument_returnsPostTransitionState() {
        try {

            // EP : Argument multimap contains valid input and nothing else.

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
        try {

            // EP : ArgumentMultimap contains valid input along with other subsequent inputs.

            List<Prefix> prefixes = List.of(PREFIX_ENGAGEMENT_TYPE, PREFIX_START_TIME, PREFIX_END_TIME);
            List<String> arguments = List.of("interview", "11/11/2019/1500", "11/11/2019/1600");

            ArgumentMultimapBuilder argumentMultimapBuilder = new ArgumentMultimapBuilder();
            argumentMultimapBuilder.addPrefixes(prefixes.toArray(Prefix[]::new));
            argumentMultimapBuilder.addArguments(arguments.toArray(String[]::new));

            ArgumentMultimap argumentMultimap = argumentMultimapBuilder.build();

            State initialState = new TypeState(new ArgumentMultimap());
            State postTransitionState = initialState.transition(argumentMultimap);

            ArgumentMultimap newArgs = argumentMultimapBuilder.build();
            newArgs.clearValues(PREFIX_START_TIME);
            newArgs.clearValues(PREFIX_END_TIME);

            assertEquals(postTransitionState, new StartDateState(newArgs));
        } catch (StateTransitionException e) {
            fail();
        }
    }

    @Test
    void transition_validArgumentMultimapInvalidInput_throwsStateTransitionException() {

        // EP : Regular invalid input.

        ArgumentMultimap argumentMultimap = new ArgumentMultimap();
        argumentMultimap.put(PREFIX_ENGAGEMENT_TYPE, "This is invalid.");

        State initialState = new TypeState(new ArgumentMultimap());
        assertThrows(StateTransitionException.class, () -> initialState.transition(argumentMultimap));

        // EP : null

        ArgumentMultimap newArgumentMultimap = new ArgumentMultimap();
        argumentMultimap.put(PREFIX_ENGAGEMENT_TYPE, null);

        State newInitialState = new TypeState(new ArgumentMultimap());
        assertThrows(StateTransitionException.class, () -> initialState.transition(newArgumentMultimap));
    }

    @Test
    void transition_invalidArgumentMultimap_throwsStateTransitionException() {

        // EP : ArgumentMultimap doesn't contain the required prefix.

        ArgumentMultimap argumentMultimap = new ArgumentMultimap();
        argumentMultimap.put(PREFIX_START_TIME, "15/11/2015/1500");

        State initialState = new TypeState(new ArgumentMultimap());
        assertThrows(StateTransitionException.class, () -> initialState.transition(argumentMultimap));
    }
}