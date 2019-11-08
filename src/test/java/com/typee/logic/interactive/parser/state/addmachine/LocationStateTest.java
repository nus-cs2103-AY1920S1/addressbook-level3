package com.typee.logic.interactive.parser.state.addmachine;

import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_ATTENDEES;
import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_DESCRIPTION;
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

class LocationStateTest {

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

        // EP : Invalid day for the month, invalid month, invalid year, invalid time, string that is not a date,
        // null, end date after start date.
        // Apply at most one invalid input heuristic for the tests.

        List<Prefix> prefixes = List.of(PREFIX_ENGAGEMENT_TYPE, PREFIX_START_TIME, PREFIX_END_TIME);
        // Invalid day of the month.
        List<String> firstArgs = List.of("appointment", "28/02/2015/1500", "31/02/2015/1600");

        // Invalid month.
        List<String> secondArgs = List.of("appointment", "13/12/2018/1600", "13/13/2018/1600");

        // Invalid year.
        List<String> thirdArgs = List.of("appointment", "13/12/2004/1454", "13/12/20004/1600");

        // Invalid time.
        List<String> fourthArgs = List.of("appointment", "13/11/2018/2300", "13/11/2018/2400");

        // String that is not a date.
        List<String> fifthArgs = List.of("appointment", "13/11/2018/2359", "hello");

        // null
        // null can't be tested as an input since ArgumentMultimap doesn't support null arguments.

        State firstState = new EndDateState(ArgumentMultimapBuilder.build(
                prefixes.subList(0, 2), firstArgs.subList(0, 2)));
        State secondState = new EndDateState(ArgumentMultimapBuilder.build(
                prefixes.subList(0, 2), secondArgs.subList(0, 2)));
        State thirdState = new EndDateState(ArgumentMultimapBuilder.build(
                prefixes.subList(0, 2), thirdArgs.subList(0, 2)));
        State fourthState = new EndDateState(ArgumentMultimapBuilder.build(
                prefixes.subList(0, 2), fourthArgs.subList(0, 2)));
        State fifthState = new EndDateState(ArgumentMultimapBuilder.build(
                prefixes.subList(0, 2), fifthArgs.subList(0, 2)));

        assertThrows(StateTransitionException.class, () -> firstState.transition(
                ArgumentMultimapBuilder.build(prefixes.subList(2, 3), firstArgs.subList(2, 3))));
        assertThrows(StateTransitionException.class, () -> secondState.transition(
                ArgumentMultimapBuilder.build(prefixes.subList(2, 3), secondArgs.subList(2, 3))));
        assertThrows(StateTransitionException.class, () -> thirdState.transition(
                ArgumentMultimapBuilder.build(prefixes.subList(2, 3), thirdArgs.subList(2, 3))));
        assertThrows(StateTransitionException.class, () -> fourthState.transition(
                ArgumentMultimapBuilder.build(prefixes.subList(2, 3), fourthArgs.subList(2, 3))));
        assertThrows(StateTransitionException.class, () -> fifthState.transition(
                ArgumentMultimapBuilder.build(prefixes.subList(2, 3), fifthArgs.subList(2, 3))));

    }

    @Test
    void transition_invalidArgumentMultimap_throwsStateTransitionException() {

        // EP : ArgumentMultimap without a start date prefix.

        List<Prefix> prefixes = List.of(PREFIX_ENGAGEMENT_TYPE, PREFIX_START_TIME, PREFIX_LOCATION);
        List<String> args = List.of("interview", "15/11/2019/1500", "COM1-B1-03");

        State initialState = new EndDateState(ArgumentMultimapBuilder.build(
                prefixes.subList(0, 2), args.subList(0, 2)));
        assertThrows(StateTransitionException.class, () -> initialState.transition(ArgumentMultimapBuilder.build(
                prefixes.subList(2, 3), args.subList(2, 3))));
    }

    @Test
    void getStateConstraints_valid_returnsConstraints() {
        State state = new LocationState(new ArgumentMultimap());
        assertEquals(state.getStateConstraints(), "Please enter the location of the meeting,"
                + " prefixed by \"l/\".");
    }

    @Test
    void isEndState_valid_returnsFalse() {
        State state = new LocationState(new ArgumentMultimap());
        assertEquals(state.isEndState(), false);
    }

    @Test
    void getPrefix_valid_returnsPrefix() {
        State state = new LocationState(new ArgumentMultimap());
        assertEquals(state.getPrefix(), PREFIX_LOCATION);
    }
}