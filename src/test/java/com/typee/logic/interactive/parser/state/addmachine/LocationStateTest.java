package com.typee.logic.interactive.parser.state.addmachine;

import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_ATTENDEES;
import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_DESCRIPTION;
import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_END_TIME;
import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_ENGAGEMENT_TYPE;
import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_LOCATION;
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

class LocationStateTest {

    private static final String EXPECTED_CONSTRAINTS = "Where will the engagement be held? Please enter the location"
            + " prefixed by " + PREFIX_LOCATION.getPrefix() + ". Example - [l/I3-AUD]";

    @Test
    void transition_validArgumentMultimapOneInput_returnsPostTransitionState() {
        try {

            // EP : Valid inputs - valid locations.
            // Test with two valid inputs.

            List<Prefix> prefixes = List.of(PREFIX_ENGAGEMENT_TYPE, PREFIX_START_TIME,
                    PREFIX_END_TIME, PREFIX_LOCATION);
            List<String> firstArgs = List.of("interview", "16/01/2019/0000", "16/01/2019/0100", "COM1-B-03");
            List<String> secondArgs = List.of("interview", "19/02/2019/0300", "01/03/2020/0100", "My house");

            State firstState = new LocationState(ArgumentMultimapBuilder.build(
                    prefixes.subList(0, 3), firstArgs.subList(0, 3)));
            State secondState = new LocationState(ArgumentMultimapBuilder.build(
                    prefixes.subList(0, 3), secondArgs.subList(0, 3)));

            ArgumentMultimap firstNewArgs = ArgumentMultimapBuilder.build(
                    prefixes.subList(3, 4), firstArgs.subList(3, 4));
            ArgumentMultimap secondNewArgs = ArgumentMultimapBuilder.build(
                    prefixes.subList(3, 4), secondArgs.subList(3, 4));



            assertEquals(firstState.transition(firstNewArgs),
                    new DescriptionState(ArgumentMultimapBuilder.build(prefixes, firstArgs)));
            assertEquals(secondState.transition(secondNewArgs),
                    new DescriptionState(ArgumentMultimapBuilder.build(prefixes, secondArgs)));

        } catch (StateTransitionException e) {
            fail();
        }
    }

    @Test
    void transition_validArgumentMultimapMultipleInputs_returnsPostTransitionState() {
        try {

            // EP : Non blank locations

            List<Prefix> prefixes = List.of(PREFIX_ENGAGEMENT_TYPE, PREFIX_START_TIME,
                    PREFIX_END_TIME, PREFIX_LOCATION, PREFIX_DESCRIPTION, PREFIX_ATTENDEES);

            List<String> firstArgs = List.of("meeting", "15/11/2019/1400", "15/11/2019/1500", "COM-1",
                    "Desc", "Smith");
            List<String> secondArgs = List.of("meeting", "29/02/2016/1400", "29/02/2016/1600", "What is this",
                    "Desc", "Jon");

            State firstInitialState = new LocationState(ArgumentMultimapBuilder.build(
                    prefixes.subList(0, 3), firstArgs.subList(0, 3)));
            State secondInitialState = new LocationState(ArgumentMultimapBuilder.build(
                    prefixes.subList(0, 3), secondArgs.subList(0, 3)));

            State firstPostTransitionState = firstInitialState.transition(ArgumentMultimapBuilder.build(
                    prefixes.subList(3, 6), firstArgs.subList(3, 6)));
            State secondPostTransitionState = secondInitialState.transition(ArgumentMultimapBuilder.build(
                    prefixes.subList(3, 6), secondArgs.subList(3, 6)));

            assertEquals(firstPostTransitionState, new DescriptionState(ArgumentMultimapBuilder.build
                    (prefixes.subList(0, 4), firstArgs.subList(0, 4))));
            assertEquals(secondPostTransitionState, new DescriptionState(ArgumentMultimapBuilder.build
                    (prefixes.subList(0, 4), secondArgs.subList(0, 4))));

        } catch (StateTransitionException e) {
            fail();
        }

    }

    @Test
    void transition_validArgumentMultiMapInvalidInput_throwsStateTransitionException() {

        // Equivalence Partitions : Blank locations, null.
        // Apply at most one invalid input heuristic for the tests.

        List<Prefix> prefixes = List.of(PREFIX_ENGAGEMENT_TYPE, PREFIX_START_TIME, PREFIX_END_TIME, PREFIX_LOCATION);

        // EP : Blank location.
        List<String> firstArgs = List.of("appointment", "28/02/2015/1500", "28/02/2015/1600", "  ");

        // EP : Blank location.
        List<String> secondArgs = List.of("meeting", "28/02/2015/1500", "28/02/2015/1600", "");

        // null
        // null can't be tested as an input since ArgumentMultimap doesn't support null arguments.

        State firstState = new LocationState(ArgumentMultimapBuilder.build(
                prefixes.subList(0, 3), firstArgs.subList(0, 3)));
        State secondState = new LocationState(ArgumentMultimapBuilder.build(
                prefixes.subList(0, 3), secondArgs.subList(0, 3)));

        assertThrows(StateTransitionException.class, () -> firstState.transition(
                ArgumentMultimapBuilder.build(prefixes.subList(3, 4), firstArgs.subList(3, 4))));
        assertThrows(StateTransitionException.class, () -> secondState.transition(
                ArgumentMultimapBuilder.build(prefixes.subList(3, 4), secondArgs.subList(3, 4))));

    }

    @Test
    void transition_invalidArgumentMultimap_throwsStateTransitionException() {

        // Equivalence Partitions : ArgumentMultimap without a location prefix, duplicate prefixes.

        // EP : No location prefix.
        List<Prefix> firstPrefixes = List.of(PREFIX_ENGAGEMENT_TYPE, PREFIX_START_TIME,
                PREFIX_END_TIME, PREFIX_DESCRIPTION);

        // EP : Duplicate prefixes.
        List<Prefix> secondPrefixes = List.of(PREFIX_ENGAGEMENT_TYPE, PREFIX_START_TIME,
                PREFIX_END_TIME, PREFIX_LOCATION, PREFIX_START_TIME);

        List<String> firstArgs = List.of("interview", "15/11/2019/1500", "15/11/2019/1600", "Description");
        List<String> secondArgs = List.of("interview", "15/11/2019/1500", "15/11/2019/1600", "Location",
                "16/11/2019/1500");

        State firstState = new LocationState(ArgumentMultimapBuilder.build(
                firstPrefixes.subList(0, 3), firstArgs.subList(0, 3)));
        State secondState = new LocationState(ArgumentMultimapBuilder.build(
                secondPrefixes.subList(0, 3), secondArgs.subList(0, 3)));

        assertThrows(StateTransitionException.class, () -> firstState.transition(ArgumentMultimapBuilder.build(
                firstPrefixes.subList(3, 4), firstArgs.subList(3, 4))));
        assertThrows(StateTransitionException.class, () -> secondState.transition(ArgumentMultimapBuilder.build(
                secondPrefixes.subList(3, 5), secondArgs.subList(3, 5))));
    }

    @Test
    void getStateConstraints_valid_returnsConstraints() {
        State state = new LocationState(new ArgumentMultimap());
        assertEquals(EXPECTED_CONSTRAINTS, state.getStateConstraints());
    }

    @Test
    void isEndState_valid_returnsFalse() {
        State state = new LocationState(new ArgumentMultimap());
        assertFalse(state.isEndState());
    }

    @Test
    void getPrefix_valid_returnsPrefix() {
        State state = new LocationState(new ArgumentMultimap());
        assertEquals(state.getPrefix(), PREFIX_LOCATION);
    }
}
