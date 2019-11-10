package com.typee.logic.interactive.parser.state.addmachine;

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

class StartDateStateTest {

    @Test
    void transition_validArgumentMultimapOneInput_returnsPostTransitionState() {
        try {

            // Equivalence Partitions : valid dates.

            List<Prefix> prefixes = List.of(PREFIX_ENGAGEMENT_TYPE, PREFIX_START_TIME);
            List<String> firstArgs = List.of("interview", "14/07/2019/1631");
            List<String> secondArgs = List.of("meeting", "22/12/2019/2358");

            State firstState = new StartDateState(ArgumentMultimapBuilder.build(
                    prefixes.subList(0, 1), firstArgs.subList(0, 1)));
            State secondState = new StartDateState(ArgumentMultimapBuilder.build(
                    prefixes.subList(0, 1), secondArgs.subList(0, 1)));

            State firstPostTransitionState = firstState.transition(ArgumentMultimapBuilder.build(
                    prefixes.subList(1, 2), firstArgs.subList(1, 2)));
            State secondPostTransitionState = secondState.transition(ArgumentMultimapBuilder.build(
                    prefixes.subList(1, 2), secondArgs.subList(1, 2)));

            assertEquals(firstPostTransitionState, new EndDateState(
                    ArgumentMultimapBuilder.build(prefixes, firstArgs)));
            assertEquals(secondPostTransitionState, new EndDateState(
                    ArgumentMultimapBuilder.build(prefixes, secondArgs)));

        } catch (StateTransitionException e) {
            fail();
        }
    }

    @Test
    void transition_validArgumentMultimapMultipleInputs_returnsPostTransitionState() {
        try {

            // Equivalence Partitions : Regular dates, leap years (leap day)

            List<Prefix> prefixes = List.of(PREFIX_ENGAGEMENT_TYPE, PREFIX_START_TIME,
                    PREFIX_END_TIME, PREFIX_LOCATION);

            // EP : Regular day
            List<String> firstArgs = List.of("meeting", "30/11/2019/1400", "30/11/2019/1500", "COM-1");

            // EP : Leap day
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

        // Equivalence Partitions : Invalid day for the month, invalid month, invalid year, invalid time,
        // string that is not a date, blank string, null.
        // Apply at most one invalid input heuristic for the tests.

        List<Prefix> prefixes = List.of(PREFIX_ENGAGEMENT_TYPE, PREFIX_START_TIME);
        // EP : Invalid day of the month.
        List<String> firstArgs = List.of("appointment", "31/02/2015/1600");

        // EP : Invalid month.
        List<String> secondArgs = List.of("appointment", "13/13/2018/1600");

        // EP : Invalid year.
        List<String> thirdArgs = List.of("appointment", "13/12/0000/1600");

        // EP : Invalid time.
        List<String> fourthArgs = List.of("appointment", "13/11/2018/2500");

        // EP : String that is not a date.
        List<String> fifthArgs = List.of("appointment", "hello");

        // EP : Blank string
        List<String> sixthArgs = List.of("meeting", "   ");

        // EP : null
        // null can't be tested as an input since ArgumentMultimap doesn't support null arguments.

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
        State sixthState = new StartDateState(ArgumentMultimapBuilder.build(
                prefixes.subList(0, 1), sixthArgs.subList(0, 1)));

        assertThrows(StateTransitionException.class, () -> firstState.transition(
                ArgumentMultimapBuilder.build(prefixes.subList(1, 2), firstArgs.subList(1, 2))));
        assertThrows(StateTransitionException.class, () -> secondState.transition(
                ArgumentMultimapBuilder.build(prefixes.subList(1, 2), secondArgs.subList(1, 2))));
        assertThrows(StateTransitionException.class, () -> thirdState.transition(
                ArgumentMultimapBuilder.build(prefixes.subList(1, 2), thirdArgs.subList(1, 2))));
        assertThrows(StateTransitionException.class, () -> fourthState.transition(
                ArgumentMultimapBuilder.build(prefixes.subList(1, 2), fourthArgs.subList(1, 2))));
        assertThrows(StateTransitionException.class, () -> fifthState.transition(
                ArgumentMultimapBuilder.build(prefixes.subList(1, 2), fifthArgs.subList(1, 2))));
        assertThrows(StateTransitionException.class, () -> sixthState.transition(
                ArgumentMultimapBuilder.build(prefixes.subList(1, 2), sixthArgs.subList(1, 2))));

    }

    @Test
    void transition_invalidArgumentMultimap_throwsStateTransitionException() {

        // Equivalence Partitions : ArgumentMultimap without a start date prefix,
        // ArgumentMultimap with duplicate prefixes.

        // EP : No start-date prefix
        List<Prefix> firstPrefixes = List.of(PREFIX_ENGAGEMENT_TYPE, PREFIX_END_TIME, PREFIX_LOCATION);

        // EP : Duplicate prefixes.
        List<Prefix> secondPrefixes = List.of(PREFIX_ENGAGEMENT_TYPE, PREFIX_START_TIME,
                PREFIX_END_TIME, PREFIX_LOCATION, PREFIX_ENGAGEMENT_TYPE);

        List<String> firstArgs = List.of("interview", "15/11/2019/1500", "COM1-B1-03");
        List<String> secondArgs = List.of("interview", "15/11/2019/1500", "15/11/2019/1600",
                "COM1-B1-03", "meeting");


        State firstState = new StartDateState(ArgumentMultimapBuilder.build(
                firstPrefixes.subList(0, 1), firstArgs.subList(0, 1)));
        State secondState = new StartDateState(ArgumentMultimapBuilder.build(
                secondPrefixes.subList(0, 1), secondArgs.subList(0, 1)));

        assertThrows(StateTransitionException.class, () -> firstState.transition(ArgumentMultimapBuilder.build(
                firstPrefixes.subList(1, 3), firstArgs.subList(1, 3))));
        assertThrows(StateTransitionException.class, () -> secondState.transition(ArgumentMultimapBuilder.build(
                secondPrefixes.subList(1, 5), secondArgs.subList(1, 5))));
    }

    @Test
    void getStateConstraints_valid_returnsConstraints() {
        State state = new StartDateState(new ArgumentMultimap());
        assertEquals(state.getStateConstraints(), "Please enter a start date and time prefixed by \"s/\"."
                + " The start time must conform to the dd/mm/yyyy/hhmm format.");
    }

    @Test
    void isEndState_valid_returnsFalse() {
        State state = new StartDateState(new ArgumentMultimap());
        assertFalse(state.isEndState());
    }

    @Test
    void getPrefix_valid_returnsPrefix() {
        State state = new StartDateState(new ArgumentMultimap());
        assertEquals(PREFIX_START_TIME, state.getPrefix());
    }
}
