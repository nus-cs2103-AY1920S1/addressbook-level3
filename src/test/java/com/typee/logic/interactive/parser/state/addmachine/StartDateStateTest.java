package com.typee.logic.interactive.parser.state.addmachine;

import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_END_TIME;
import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_ENGAGEMENT_TYPE;
import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_LOCATION;
import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_START_TIME;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.Prefix;
import com.typee.logic.interactive.parser.state.State;
import com.typee.logic.interactive.parser.state.exceptions.StateTransitionException;
import com.typee.testutil.ArgumentMultimapBuilder;

class StartDateStateTest {

    @Test
    void transition_validArgumentMultimapOneInput_returnsPostTransitionState() {
        try {

            // EP : Valid inputs - valid dates.

            List<Prefix> prefixes = List.of(PREFIX_ENGAGEMENT_TYPE);
            List<String> arguments = List.of("interview");

            ArgumentMultimap firstMap = ArgumentMultimapBuilder.build(prefixes, arguments);

            State firstState = new StartDateState(firstMap);
            State firstPostTransitionState = firstState.transition(
                    ArgumentMultimapBuilder.build(List.of(PREFIX_START_TIME), List.of("16/11/2019/1500")));

            List<Prefix> newPrefixes = List.of(PREFIX_ENGAGEMENT_TYPE, PREFIX_START_TIME);
            List<String> newArgs = List.of("interview", "16/11/2019/1500");

            assertEquals(firstPostTransitionState, new EndDateState(
                    ArgumentMultimapBuilder.build(newPrefixes, newArgs)));
        } catch (StateTransitionException e) {
            fail();
        }
    }

    @Test
    void transition_validArgumentMultimapMultipleInputs_returnsPostTransitionState() {
        try {

            // EP : Regular dates, leap years (test leap day)

            List<Prefix> prefixes = List.of(PREFIX_ENGAGEMENT_TYPE, PREFIX_START_TIME,
                    PREFIX_END_TIME, PREFIX_LOCATION);

            // Regular day
            List<String> firstArgs = List.of("meeting", "15/11/2019/1400", "15/11/2019/1500", "COM-1");

            // Leap day
            List<String> secondArgs = List.of("meeting", "29/02/2016/1400", "15/11/2019/1500", "COM-1");

            State firstInitialState = new StartDateState(ArgumentMultimapBuilder.build(
                    prefixes.subList(0, 1), firstArgs.subList(0, 1)));
            State secondInitialState = new StartDateState(ArgumentMultimapBuilder.build(
                    prefixes.subList(0, 1), secondArgs.subList(0, 1)));

            State firstPostTransitionState = firstInitialState.transition(ArgumentMultimapBuilder.build(
                    prefixes.subList(1, 4), firstArgs.subList(1, 4)));
            State secondPostTransitionState = secondInitialState.transition(ArgumentMultimapBuilder.build(
                    prefixes.subList(1, 4), secondArgs.subList(1, 4)));

            assertEquals(firstPostTransitionState, new EndDateState(ArgumentMultimapBuilder.build
                    (prefixes.subList(0, 2), firstArgs.subList(0, 2))));
            assertEquals(secondPostTransitionState, new EndDateState(ArgumentMultimapBuilder.build
                    (prefixes.subList(0, 2), secondArgs.subList(0, 2))));

        } catch (StateTransitionException e) {
            fail();
        }

    }

    @Test
    void transition_validArgumentMultiMapInvalidInput_throwsStateTransitionException() {

        // EP : Invalid day for the month, invalid month, invalid year, invalid time, string that is not a date.
        // Apply at most one invalid input heuristic for the tests.
        // null doesn't need to be tested for since ArgumentMultimap returns an empty optional in that case.

        List<Prefix> prefixes = List.of(PREFIX_ENGAGEMENT_TYPE, PREFIX_START_TIME);
        // Invalid day of the month.
        List<String> firstArgs = List.of("appointment", "31/02/2015/1600");

        // Invalid month.
        List<String> secondArgs = List.of("appointment", "13/13/2018/1600");

        // Invalid year.
        List<String> thirdArgs = List.of("appointment", "13/12/0000/1600");

        // Invalid time.
        List<String> fourthArgs = List.of("appointment", "13/11/2018/2500");

        // String that is not a date.
        List<String> fifthArgs = List.of("appointment", "hello");

        State firstState = new StartDateState(ArgumentMultimapBuilder.build(
                prefixes.subList(0, 1), firstArgs.subList(0, 1)));
        State secondState = new StartDateState(ArgumentMultimapBuilder.build(
                prefixes.subList(0, 1), secondArgs.subList(0, 1)));
        State thirdState = new StartDateState(ArgumentMultimapBuilder.build(
                prefixes.subList(0, 1), thirdArgs.subList(0, 1)));
        State fourthState = new StartDateState(ArgumentMultimapBuilder.build(
                prefixes.subList(0, 1), fourthArgs.subList(0, 1)));
        State fifthState = new StartDateState(ArgumentMultimapBuilder.build(
                prefixes.subList(0, 1), fifthArgs.subList(0, 1)));

        assertThrows(StateTransitionException.class, () -> firstState.transition(ArgumentMultimapBuilder.
                build(prefixes.subList(1, 2), firstArgs.subList(1, 2))));
        assertThrows(StateTransitionException.class, () -> secondState.transition(ArgumentMultimapBuilder.
                build(prefixes.subList(1, 2), secondArgs.subList(1, 2))));
        assertThrows(StateTransitionException.class, () -> thirdState.transition(ArgumentMultimapBuilder.
                build(prefixes.subList(1, 2), thirdArgs.subList(1, 2))));
        assertThrows(StateTransitionException.class, () -> fourthState.transition(ArgumentMultimapBuilder.
                build(prefixes.subList(1, 2), fourthArgs.subList(1, 2))));
        assertThrows(StateTransitionException.class, () -> fifthState.transition(ArgumentMultimapBuilder.
                build(prefixes.subList(1, 2), fifthArgs.subList(1, 2))));

    }

    @Test
    void transition_invalidArgumentMultimap_throwsStateTransitionException() {

        // EP : ArgumentMultimap without a start date prefix.

        List<Prefix> prefixes = List.of(PREFIX_ENGAGEMENT_TYPE, PREFIX_END_TIME, PREFIX_LOCATION);
        List<String> args = List.of("interview", "15/11/2019/1500", "COM1-B1-03");

        State initialState = new StartDateState(ArgumentMultimapBuilder.build(
                prefixes.subList(0, 1), args.subList(0, 1)));
        assertThrows(StateTransitionException.class, () -> initialState.transition(ArgumentMultimapBuilder.build(
                prefixes.subList(1, 3), args.subList(1, 3))));
    }

}