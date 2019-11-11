package com.typee.logic.interactive.parser.state.addmachine;

import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_END_TIME;
import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_ENGAGEMENT_TYPE;
import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_START_TIME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.Prefix;
import com.typee.logic.interactive.parser.state.State;
import com.typee.logic.interactive.parser.state.exceptions.StateTransitionException;
import com.typee.testutil.ArgumentMultimapBuilder;

class TypeStateTest {

    private static final String EXPECTED_CONSTRAINTS = "Let's add an engagement! What is the type of the engagement to"
            + " be added? Please enter the type of the engagement prefixed by " + PREFIX_ENGAGEMENT_TYPE.getPrefix()
            + ". Example - [t/meeting]";

    @Test
    void transition_validArgumentMultimapOneArgument_returnsPostTransitionState() {
        try {

            // EP : Argument multimap contains valid input and nothing else.

            List<Prefix> prefixes = List.of(PREFIX_ENGAGEMENT_TYPE);
            List<String> firstArgs = List.of("interview");
            List<String> secondArgs = List.of("Appointment");
            List<String> thirdArgs = List.of("meEtIng");

            State firstState = new TypeState(new ArgumentMultimap());
            State secondState = new TypeState(new ArgumentMultimap());
            State thirdState = new TypeState(new ArgumentMultimap());

            assertEquals(firstState.transition(ArgumentMultimapBuilder.build(prefixes, firstArgs)),
                    new StartDateState(ArgumentMultimapBuilder.build(prefixes, firstArgs)));
            assertEquals(secondState.transition(ArgumentMultimapBuilder.build(prefixes, secondArgs)),
                    new StartDateState(ArgumentMultimapBuilder.build(prefixes, secondArgs)));
            assertEquals(thirdState.transition(ArgumentMultimapBuilder.build(prefixes, thirdArgs)),
                    new StartDateState(ArgumentMultimapBuilder.build(prefixes, thirdArgs)));

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

            ArgumentMultimap argumentMultimap = ArgumentMultimapBuilder.build(prefixes, arguments);

            State initialState = new TypeState(new ArgumentMultimap());
            State postTransitionState = initialState.transition(argumentMultimap);

            ArgumentMultimap newArgs = ArgumentMultimapBuilder.build(prefixes, arguments);
            newArgs.clearValues(PREFIX_START_TIME);
            newArgs.clearValues(PREFIX_END_TIME);

            assertEquals(postTransitionState, new StartDateState(newArgs));
        } catch (StateTransitionException e) {
            fail();
        }
    }

    @Test
    void transition_validArgumentMultimapInvalidInput_throwsStateTransitionException() {

        // Equivalence Partitions : Non-blank invalid input, blank input

        List<Prefix> prefixes = List.of(PREFIX_ENGAGEMENT_TYPE);

        // EP : Non-blank invalid input
        List<String> firstArgs = List.of("hahahaha");

        // EP : Blank invalid input
        List<String> secondArgs = List.of("   ");

        State firstState = new TypeState(new ArgumentMultimap());
        State secondState = new TypeState(new ArgumentMultimap());

        assertThrows(StateTransitionException.class, () -> firstState.transition(
                ArgumentMultimapBuilder.build(prefixes, firstArgs)));
        assertThrows(StateTransitionException.class, () -> secondState.transition(
                ArgumentMultimapBuilder.build(prefixes, secondArgs)));

        // EP : null

        // null can't be tested as an input value since ArgumentMultimap doesn't handle nulls.
    }

    @Test
    void transition_invalidArgumentMultimap_throwsStateTransitionException() {

        // EP : ArgumentMultimap doesn't contain the required prefix.

        ArgumentMultimap argumentMultimap = new ArgumentMultimap();
        argumentMultimap.put(PREFIX_START_TIME, "15/11/2015/1500");

        State initialState = new TypeState(new ArgumentMultimap());
        assertThrows(StateTransitionException.class, () -> initialState.transition(argumentMultimap));
    }

    @Test
    void getStateConstraints_valid_returnsConstraints() {
        State typeState = new TypeState(new ArgumentMultimap());
        assertEquals(EXPECTED_CONSTRAINTS, typeState.getStateConstraints());
    }

    @Test
    void isEndState_valid_returnsFalse() {
        State typeState = new TypeState(new ArgumentMultimap());
        assertFalse(typeState.isEndState());
    }

    @Test
    void getPrefix_valid_returnsPrefix() {
        State typeState = new TypeState(new ArgumentMultimap());
        assertEquals(PREFIX_ENGAGEMENT_TYPE, typeState.getPrefix());
    }
}
