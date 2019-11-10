package com.typee.logic.interactive.parser.state.addmachine;

import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_ATTENDEES;
import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_DESCRIPTION;
import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_END_TIME;
import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_ENGAGEMENT_TYPE;
import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_LOCATION;
import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_ORDER;
import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_PRIORITY;
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

class PriorityStateTest {

    @Test
    void transition_validArgumentMultimapOneInput_returnsPostTransitionState() {
        try {

            // EP : Valid inputs - valid priorities.
            // Test with two valid inputs.

            List<Prefix> prefixes = List.of(PREFIX_ENGAGEMENT_TYPE, PREFIX_START_TIME,
                    PREFIX_END_TIME, PREFIX_LOCATION, PREFIX_ATTENDEES, PREFIX_PRIORITY);
            List<String> firstArgs = List.of("interview", "16/01/2019/0000", "16/01/2019/0100", "COM1-B-03",
                    "Jon | Snow", "high");
            List<String> secondArgs = List.of("interview", "19/02/2019/0300", "01/03/2020/0100", "My house",
                    "Will Smith", "NonE");

            State firstState = new PriorityState(ArgumentMultimapBuilder.build(
                    prefixes.subList(0, 5), firstArgs.subList(0, 5)));
            State secondState = new PriorityState(ArgumentMultimapBuilder.build(
                    prefixes.subList(0, 5), secondArgs.subList(0, 5)));

            ArgumentMultimap firstNewArgs = ArgumentMultimapBuilder.build(
                    prefixes.subList(5, 6), firstArgs.subList(5, 6));
            ArgumentMultimap secondNewArgs = ArgumentMultimapBuilder.build(
                    prefixes.subList(5, 6), secondArgs.subList(5, 6));



            assertEquals(firstState.transition(firstNewArgs),
                    new AddCommandEndState(ArgumentMultimapBuilder.build(prefixes, firstArgs)));
            assertEquals(secondState.transition(secondNewArgs),
                    new AddCommandEndState(ArgumentMultimapBuilder.build(prefixes, secondArgs)));

        } catch (StateTransitionException e) {
            fail();
        }
    }

    @Test
    void transition_validArgumentMultiMapInvalidInput_throwsStateTransitionException() {

        // Equivalence Partitions : Invalid priorities, blank inputs, null
        // Apply at most one invalid input heuristic for the tests.

        List<Prefix> prefixes = List.of(PREFIX_ENGAGEMENT_TYPE, PREFIX_START_TIME, PREFIX_END_TIME,
                PREFIX_LOCATION, PREFIX_ATTENDEES, PREFIX_PRIORITY);

        // EP : Invalid priority
        List<String> firstArgs = List.of("appointment", "28/02/2015/1500", "28/02/2015/1600", "COM-1",
                "My Name", "loww");

        // EP : Invalid priority
        List<String> secondArgs = List.of("appointment", "28/02/2015/1500", "28/02/2015/1600", "COM-1",
                "My Name", "hi   || ");

        // EP : Blank input
        List<String> thirdArgs = List.of("appointment", "28/02/2015/1500", "28/02/2015/1600", "COM-1",
                "My Name", "");

        // EP : Blank input
        List<String> fourthArgs = List.of("appointment", "28/02/2015/1500", "28/02/2015/1600", "COM-1",
                "My Name", "  ");

        // null
        // null can't be tested as an input since ArgumentMultimap doesn't support null arguments.

        State firstState = new PriorityState(ArgumentMultimapBuilder.build(
                prefixes.subList(0, 5), firstArgs.subList(0, 5)));
        State secondState = new PriorityState(ArgumentMultimapBuilder.build(
                prefixes.subList(0, 5), secondArgs.subList(0, 5)));
        State thirdState = new PriorityState(ArgumentMultimapBuilder.build(
                prefixes.subList(0, 5), firstArgs.subList(0, 5)));
        State fourthState = new PriorityState(ArgumentMultimapBuilder.build(
                prefixes.subList(0, 5), firstArgs.subList(0, 5)));


        assertThrows(StateTransitionException.class, () -> firstState.transition(
                ArgumentMultimapBuilder.build(prefixes.subList(5, 6), firstArgs.subList(5, 6))));
        assertThrows(StateTransitionException.class, () -> secondState.transition(
                ArgumentMultimapBuilder.build(prefixes.subList(5, 6), secondArgs.subList(5, 6))));
        assertThrows(StateTransitionException.class, () -> thirdState.transition(
                ArgumentMultimapBuilder.build(prefixes.subList(5, 6), thirdArgs.subList(5, 6))));
        assertThrows(StateTransitionException.class, () -> fourthState.transition(
                ArgumentMultimapBuilder.build(prefixes.subList(5, 6), fourthArgs.subList(5, 6))));

    }

    @Test
    void transition_invalidArgumentMultimap_throwsStateTransitionException() {

        // Equivalence Partitions : ArgumentMultimap without a priority level prefix,
        // ArgumentMultimap with excessive args.

        // EP : No priority level prefix.
        List<Prefix> firstPrefixes = List.of(PREFIX_ENGAGEMENT_TYPE, PREFIX_START_TIME, PREFIX_END_TIME,
                PREFIX_DESCRIPTION, PREFIX_LOCATION, PREFIX_ATTENDEES);

        // EP : Excessive arguments.
        List<Prefix> secondPrefixes = List.of(PREFIX_ENGAGEMENT_TYPE, PREFIX_START_TIME, PREFIX_END_TIME,
                PREFIX_DESCRIPTION, PREFIX_LOCATION, PREFIX_ATTENDEES, PREFIX_PRIORITY, PREFIX_ORDER);

        List<String> firstArgs = List.of("interview", "15/11/2019/1500", "15/11/2019/1600", "Description",
                "Location", "Damn It");
        List<String> secondArgs = List.of("interview", "15/11/2019/1500", "15/11/2019/1600", "Description",
                "Location", "Damn It", "low", "start");

        State firstState = new PriorityState(ArgumentMultimapBuilder.build(
                firstPrefixes, firstArgs));
        State secondState = new PriorityState(ArgumentMultimapBuilder.build(
                secondPrefixes.subList(0, 6), secondArgs.subList(0, 6)));

        assertThrows(StateTransitionException.class, () -> firstState.transition(new ArgumentMultimap()));
        assertThrows(StateTransitionException.class, () -> secondState.transition(
                ArgumentMultimapBuilder.build(secondPrefixes.subList(6, 8), secondArgs.subList(6, 8))));
    }

    @Test
    void getStateConstraints_valid_returnsConstraints() {
        State state = new PriorityState(new ArgumentMultimap());
        assertEquals(state.getStateConstraints(), "The priority of an engagement can be"
                + " LOW, MEDIUM, HIGH or NONE. Please enter the priority prefixed by \"p/\".");
    }

    @Test
    void isEndState_valid_returnsFalse() {
        State state = new PriorityState(new ArgumentMultimap());
        assertFalse(state.isEndState());
    }

    @Test
    void getPrefix_valid_returnsPrefix() {
        State state = new PriorityState(new ArgumentMultimap());
        assertEquals(state.getPrefix(), PREFIX_PRIORITY);
    }
}
