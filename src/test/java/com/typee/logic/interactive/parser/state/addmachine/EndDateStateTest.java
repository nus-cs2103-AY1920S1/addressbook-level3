package com.typee.logic.interactive.parser.state.addmachine;

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

class EndDateStateTest {

    @Test
    void transition_validArgumentMultimapOneInput_returnsPostTransitionState() {
        try {

            // EP : Valid inputs - valid dates.
            // Test with two valid inputs.

            List<Prefix> prefixes = List.of(PREFIX_ENGAGEMENT_TYPE, PREFIX_START_TIME, PREFIX_END_TIME);
            List<String> firstArgs = List.of("interview", "16/01/2019/0000", "16/01/2019/0100");
            List<String> secondArgs = List.of("interview", "19/02/2019/0300", "01/03/2020/0100");

            State firstState = new EndDateState(ArgumentMultimapBuilder.build(
                    prefixes.subList(0, 2), firstArgs.subList(0, 2)));
            State secondState = new EndDateState(ArgumentMultimapBuilder.build(
                    prefixes.subList(0, 2), secondArgs.subList(0, 2)));

            ArgumentMultimap firstNewArgs = ArgumentMultimapBuilder.build(
                    prefixes.subList(2, 3), firstArgs.subList(2, 3));
            ArgumentMultimap secondNewArgs = ArgumentMultimapBuilder.build(
                    prefixes.subList(2, 3), secondArgs.subList(2, 3));



            assertEquals(firstState.transition(firstNewArgs),
                    new LocationState(ArgumentMultimapBuilder.build(prefixes, firstArgs)));
            assertEquals(secondState.transition(secondNewArgs),
                    new LocationState(ArgumentMultimapBuilder.build(prefixes, secondArgs)));

        } catch (StateTransitionException e) {
            fail();
        }
    }

    @Test
    void transition_validArgumentMultimapMultipleInputs_returnsPostTransitionState() {
        try {

            // Equivalence Partitions : Regular dates, leap years (leap day)

            List<Prefix> prefixes = List.of(PREFIX_ENGAGEMENT_TYPE, PREFIX_START_TIME,
                    PREFIX_END_TIME, PREFIX_LOCATION, PREFIX_DESCRIPTION);

            // EP : Regular day
            List<String> firstArgs = List.of("meeting", "15/11/2019/1400", "15/11/2019/1500", "COM-1", "Desc");

            // EP : Leap day
            List<String> secondArgs = List.of("meeting", "29/02/2016/1400", "29/02/2016/1600", "COM-1", "Desc");

            State firstInitialState = new EndDateState(ArgumentMultimapBuilder.build(
                    prefixes.subList(0, 2), firstArgs.subList(0, 2)));
            State secondInitialState = new EndDateState(ArgumentMultimapBuilder.build(
                    prefixes.subList(0, 2), secondArgs.subList(0, 2)));

            State firstPostTransitionState = firstInitialState.transition(ArgumentMultimapBuilder.build(
                    prefixes.subList(2, 5), firstArgs.subList(2, 5)));
            State secondPostTransitionState = secondInitialState.transition(ArgumentMultimapBuilder.build(
                    prefixes.subList(2, 5), secondArgs.subList(2, 5)));

            assertEquals(firstPostTransitionState, new LocationState(ArgumentMultimapBuilder.build
                    (prefixes.subList(0, 3), firstArgs.subList(0, 3))));
            assertEquals(secondPostTransitionState, new LocationState(ArgumentMultimapBuilder.build
                    (prefixes.subList(0, 3), secondArgs.subList(0, 3))));

        } catch (StateTransitionException e) {
            fail();
        }

    }

    @Test
    void transition_validArgumentMultiMapInvalidInput_throwsStateTransitionException() {

        // Equivalence Partitions : Invalid day for the month, invalid month, invalid year, invalid time,
        // string that is not a date, end date before start date, end date equals start date, blank string, null.
        // Apply at most one invalid input heuristic for the tests.

        List<Prefix> prefixes = List.of(PREFIX_ENGAGEMENT_TYPE, PREFIX_START_TIME, PREFIX_END_TIME);
        // EP : Invalid day of the month.
        List<String> firstArgs = List.of("appointment", "28/02/2015/1500", "31/02/2015/1600");

        // EP : Invalid month.
        List<String> secondArgs = List.of("appointment", "13/12/2018/1600", "13/13/2018/1600");

        // EP : Invalid year.
        List<String> thirdArgs = List.of("appointment", "13/12/2004/1454", "13/12/20004/1600");

        // EP : Invalid time.
        List<String> fourthArgs = List.of("appointment", "13/11/2018/2300", "13/11/2018/2400");

        // EP : String that is not a date.
        List<String> fifthArgs = List.of("appointment", "13/11/2018/2359", "hello");

        // EP : End date-time before start date-time.
        List<String> sixthArgs = List.of("appointment", "13/11/2018/2359", "13/11/2018/2350");

        // EP : End date-time equals start date-time
        List<String> seventhArgs = List.of("appointment", "13/11/2018/2359", "13/11/2018/2359");

        // EP : Blank string.
        List<String> eighthArgs = List.of("appointment", "13/11/2018/2359", "  ");

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
        State sixthState = new EndDateState(ArgumentMultimapBuilder.build(
                prefixes.subList(0, 2), sixthArgs.subList(0, 2)));
        State seventhState = new EndDateState(ArgumentMultimapBuilder.build(
                prefixes.subList(0, 2), seventhArgs.subList(0, 2)));
        State eighthState = new EndDateState(ArgumentMultimapBuilder.build(
                prefixes.subList(0, 2), eighthArgs.subList(0, 2)));

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
        assertThrows(StateTransitionException.class, () -> sixthState.transition(
                ArgumentMultimapBuilder.build(prefixes.subList(2, 3), sixthArgs.subList(2, 3))));
        assertThrows(StateTransitionException.class, () -> seventhState.transition(
                ArgumentMultimapBuilder.build(prefixes.subList(2, 3), seventhArgs.subList(2, 3))));
        assertThrows(StateTransitionException.class, () -> eighthState.transition(
                ArgumentMultimapBuilder.build(prefixes.subList(2, 3), eighthArgs.subList(2, 3))));

    }

    @Test
    void transition_invalidArgumentMultimap_throwsStateTransitionException() {

        // Equivalence Partitions : ArgumentMultimap without an end date prefix,
        // ArgumentMultimap with duplicate prefixes.

        // EP :  No end date prefix.
        List<Prefix> firstPrefixes = List.of(PREFIX_ENGAGEMENT_TYPE, PREFIX_START_TIME, PREFIX_LOCATION);

        // EP : Duplicate prefix.
        List<Prefix> secondPrefixes = List.of(PREFIX_ENGAGEMENT_TYPE, PREFIX_START_TIME, PREFIX_END_TIME,
                PREFIX_ENGAGEMENT_TYPE);
        List<String> firstArgs = List.of("interview", "15/11/2019/1500", "COM1-B1-03");
        List<String> secondArgs = List.of("interview", "15/11/2019/1500", "15/11/2019/1600", "meeting");

        State firstState = new EndDateState(ArgumentMultimapBuilder.build(
                firstPrefixes.subList(0, 2), firstArgs.subList(0, 2)));
        State secondState = new EndDateState(ArgumentMultimapBuilder.build(
                secondPrefixes.subList(0, 2), secondArgs.subList(0, 2)));

        assertThrows(StateTransitionException.class, () -> firstState.transition(ArgumentMultimapBuilder.build(
                firstPrefixes.subList(2, 3), firstArgs.subList(2, 3))));
        assertThrows(StateTransitionException.class, () -> secondState.transition(ArgumentMultimapBuilder.build(
                secondPrefixes.subList(2, 4), secondArgs.subList(2, 4))));
    }

    @Test
    void getStateConstraints_valid_returnsConstraints() {
        State state = new EndDateState(new ArgumentMultimap());
        assertEquals(state.getStateConstraints(), "Please enter an end date and time prefixed by \"e/\"."
                + " The start time must conform to the dd/mm/yyyy/hhmm format.");
    }

    @Test
    void isEndState_valid_returnsFalse() {
        State state = new EndDateState(new ArgumentMultimap());
        assertFalse(state.isEndState());
    }

    @Test
    void getPrefix_valid_returnsPrefix() {
        State state = new EndDateState(new ArgumentMultimap());
        assertEquals(state.getPrefix(), PREFIX_END_TIME);
    }
}
