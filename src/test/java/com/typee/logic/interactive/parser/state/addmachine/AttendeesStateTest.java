package com.typee.logic.interactive.parser.state.addmachine;

import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_ATTENDEES;
import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_DESCRIPTION;
import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_END_TIME;
import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_ENGAGEMENT_TYPE;
import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_LOCATION;
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

class AttendeesStateTest {

    @Test
    void transition_validArgumentMultimapOneInput_returnsPostTransitionState() {
        try {

            // EP : Valid inputs - One attendee, more than one attendees.
            // Test with two valid inputs.

            List<Prefix> prefixes = List.of(PREFIX_ENGAGEMENT_TYPE, PREFIX_START_TIME,
                    PREFIX_END_TIME, PREFIX_LOCATION, PREFIX_DESCRIPTION, PREFIX_ATTENDEES);

            // EP : One attendee
            List<String> firstArgs = List.of("interview", "16/01/2019/0000", "16/01/2019/0100",
                    "COM1-B-03", "CS2103T Discussion", "John");

            // EP : More than one attendees
            List<String> secondArgs = List.of("interview", "19/02/2019/0300", "01/03/2020/0100",
                    "My house", "Team meeting", "Adam | Will Ma Man Smith | John Blair");

            State firstState = new AttendeesState(ArgumentMultimapBuilder.build(
                    prefixes.subList(0, 5), firstArgs.subList(0, 5)));
            State secondState = new AttendeesState(ArgumentMultimapBuilder.build(
                    prefixes.subList(0, 5), secondArgs.subList(0, 5)));

            ArgumentMultimap firstNewArgs = ArgumentMultimapBuilder.build(
                    prefixes.subList(5, 6), firstArgs.subList(5, 6));
            ArgumentMultimap secondNewArgs = ArgumentMultimapBuilder.build(
                    prefixes.subList(5, 6), secondArgs.subList(5, 6));



            assertEquals(firstState.transition(firstNewArgs),
                    new PriorityState(ArgumentMultimapBuilder.build(prefixes, firstArgs)));
            assertEquals(secondState.transition(secondNewArgs),
                    new PriorityState(ArgumentMultimapBuilder.build(prefixes, secondArgs)));

        } catch (StateTransitionException e) {
            fail();
        }
    }

    @Test
    void transition_validArgumentMultimapMultipleInputs_returnsPostTransitionState() {
        try {

            // EP : Valid inputs - One attendee, more than one attendee.

            List<Prefix> prefixes = List.of(PREFIX_ENGAGEMENT_TYPE, PREFIX_START_TIME,
                    PREFIX_END_TIME, PREFIX_LOCATION, PREFIX_DESCRIPTION, PREFIX_ATTENDEES, PREFIX_PRIORITY);

            // EP : One attendee.
            List<String> firstArgs = List.of("meeting", "15/11/2019/1400", "15/11/2019/1500", "COM-1",
                    "Desc", "Smith", "Low");

            // EP : More than one attendee.
            List<String> secondArgs = List.of("meeting", "29/02/2016/1400", "29/02/2016/1600", "What is this",
                    "Desc", "Jon| Snow |Thrones", "none");

            // EP : More than one attendee.
            List<String> thirdArgs = List.of("meeting", "29/02/2016/1400", "29/02/2016/1600", "What is this",
                    "Desc", "Jon Snow Den", "none");

            State firstInitialState = new AttendeesState(ArgumentMultimapBuilder.build(
                    prefixes.subList(0, 5), firstArgs.subList(0, 5)));
            State secondInitialState = new AttendeesState(ArgumentMultimapBuilder.build(
                    prefixes.subList(0, 5), secondArgs.subList(0, 5)));
            State thirdInitialState = new AttendeesState(ArgumentMultimapBuilder.build(
                    prefixes.subList(0, 5), thirdArgs.subList(0, 5)));

            State firstPostTransitionState = firstInitialState.transition(ArgumentMultimapBuilder.build(
                    prefixes.subList(5, 7), firstArgs.subList(5, 7)));
            State secondPostTransitionState = secondInitialState.transition(ArgumentMultimapBuilder.build(
                    prefixes.subList(5, 7), secondArgs.subList(5, 7)));
            State thirdPostTransitionState = thirdInitialState.transition(ArgumentMultimapBuilder.build(
                    prefixes.subList(5, 7), thirdArgs.subList(5, 7)));

            assertEquals(firstPostTransitionState, new PriorityState(ArgumentMultimapBuilder.build
                    (prefixes.subList(0, 6), firstArgs.subList(0, 6))));
            assertEquals(secondPostTransitionState, new PriorityState(ArgumentMultimapBuilder.build
                    (prefixes.subList(0, 6), secondArgs.subList(0, 6))));
            assertEquals(thirdPostTransitionState, new PriorityState(ArgumentMultimapBuilder.build
                    (prefixes.subList(0, 6), thirdArgs.subList(0, 6))));

        } catch (StateTransitionException e) {
            fail();
        }

    }

    @Test
    void transition_validArgumentMultiMapInvalidInput_throwsStateTransitionException() {

        // Equivalence Partitions : Blank input, invalid name, invalid format, null.
        // Apply at most one invalid input heuristic for the tests.

        List<Prefix> prefixes = List.of(PREFIX_ENGAGEMENT_TYPE, PREFIX_START_TIME, PREFIX_END_TIME,
                PREFIX_LOCATION, PREFIX_DESCRIPTION, PREFIX_ATTENDEES);

        // EP : Blank input
        List<String> firstArgs = List.of("appointment", "28/02/2015/1500", "28/02/2015/1600", "Com-2", "Desc", "  ");

        // EP : Blank input
        List<String> secondArgs = List.of("meeting", "28/02/2015/1500", "28/02/2015/1600", "FASS", "Desc", "");

        // EP : Invalid name
        List<String> thirdArgs = List.of("meeting", "28/02/2015/1500", "28/02/2015/1600", "FASS",
                "Desc", "Haddaway, 1993");

        // EP : Invalid name
        List<String> fourthArgs = List.of("meeting", "28/02/2015/1500", "28/02/2015/1600", "FASS",
                "Desc", "What is Love |");

        // EP : Invalid name
        List<String> fifthArgs = List.of("meeting", "28/02/2015/1500", "28/02/2015/1600", "FASS",
                "Desc", "Baby don't | Hurt Me | No More");

        // EP : Invalid format
        List<String> sixthArgs = List.of("meeting", "28/02/2015/1500", "28/02/2015/1600", "FASS",
                "Desc", "This, Is, The, Rhythm");

        // EP : Invalid format
        List<String> seventhArgs = List.of("meeting", "28/02/2015/1500", "28/02/2015/1600", "FASS",
                "Desc", "Of || The Night");

        // EP : Invalid format
        List<String> eighthArgs = List.of("meeting", "28/02/2015/1500", "28/02/2015/1600", "FASS",
                "Desc", "Baby don't | Hurt Me | No More |");

        // null
        // null can't be tested as an input since ArgumentMultimap doesn't support null arguments.

        State firstState = new AttendeesState(ArgumentMultimapBuilder.build(
                prefixes.subList(0, 5), firstArgs.subList(0, 5)));
        State secondState = new AttendeesState(ArgumentMultimapBuilder.build(
                prefixes.subList(0, 5), secondArgs.subList(0, 5)));
        State thirdState = new AttendeesState(ArgumentMultimapBuilder.build(
                prefixes.subList(0, 5), thirdArgs.subList(0, 5)));
        State fourthState = new AttendeesState(ArgumentMultimapBuilder.build(
                prefixes.subList(0, 5), fourthArgs.subList(0, 5)));
        State fifthState = new AttendeesState(ArgumentMultimapBuilder.build(
                prefixes.subList(0, 5), fifthArgs.subList(0, 5)));
        State sixthState = new AttendeesState(ArgumentMultimapBuilder.build(
                prefixes.subList(0, 5), sixthArgs.subList(0, 5)));
        State seventhState = new AttendeesState(ArgumentMultimapBuilder.build(
                prefixes.subList(0, 5), seventhArgs.subList(0, 5)));
        State eighthState = new AttendeesState(ArgumentMultimapBuilder.build(
                prefixes.subList(0, 5), eighthArgs.subList(0, 5)));

        assertThrows(StateTransitionException.class, () -> firstState.transition(
                ArgumentMultimapBuilder.build(prefixes.subList(5, 6), firstArgs.subList(5, 6))));
        assertThrows(StateTransitionException.class, () -> secondState.transition(
                ArgumentMultimapBuilder.build(prefixes.subList(5, 6), secondArgs.subList(5, 6))));
        assertThrows(StateTransitionException.class, () -> thirdState.transition(
                ArgumentMultimapBuilder.build(prefixes.subList(5, 6), thirdArgs.subList(5, 6))));
        assertThrows(StateTransitionException.class, () -> fourthState.transition(
                ArgumentMultimapBuilder.build(prefixes.subList(5, 6), fourthArgs.subList(5, 6))));
        assertThrows(StateTransitionException.class, () -> fifthState.transition(
                ArgumentMultimapBuilder.build(prefixes.subList(5, 6), fifthArgs.subList(5, 6))));
        assertThrows(StateTransitionException.class, () -> sixthState.transition(
                ArgumentMultimapBuilder.build(prefixes.subList(5, 6), sixthArgs.subList(5, 6))));
        assertThrows(StateTransitionException.class, () -> seventhState.transition(
                ArgumentMultimapBuilder.build(prefixes.subList(5, 6), seventhArgs.subList(5, 6))));
        assertThrows(StateTransitionException.class, () -> eighthState.transition(
                ArgumentMultimapBuilder.build(prefixes.subList(5, 6), eighthArgs.subList(5, 6))));

    }

    @Test
    void transition_invalidArgumentMultimap_throwsStateTransitionException() {

        // Equivalence Partition : ArgumentMultimap without an attendees prefix, duplicate prefixes

        // EP : No attendees prefix
        List<Prefix> firstPrefixes = List.of(PREFIX_ENGAGEMENT_TYPE, PREFIX_START_TIME, PREFIX_END_TIME,
                PREFIX_LOCATION, PREFIX_DESCRIPTION, PREFIX_PRIORITY);

        // EP : Duplicate prefixes
        List<Prefix> secondPrefixes = List.of(PREFIX_ENGAGEMENT_TYPE, PREFIX_START_TIME, PREFIX_END_TIME,
                PREFIX_LOCATION, PREFIX_DESCRIPTION, PREFIX_ATTENDEES, PREFIX_DESCRIPTION);

        List<String> firstArgs = List.of("interview", "15/11/2019/1500", "15/11/2019/1600", "COM-2", "desc", "low");
        List<String> secondArgs = List.of("interview", "15/11/2019/1500", "15/11/2019/1600", "COM-2", "desc",
                "John | Kelly", "new desc");

        State firstState = new AttendeesState(ArgumentMultimapBuilder.build(
                firstPrefixes.subList(0, 5), firstArgs.subList(0, 5)));
        State secondState = new AttendeesState(ArgumentMultimapBuilder.build(
                secondPrefixes.subList(0, 5), secondArgs.subList(0, 5)));

        assertThrows(StateTransitionException.class, () -> firstState.transition(ArgumentMultimapBuilder.build(
                firstPrefixes.subList(5, 6), firstArgs.subList(5, 6))));
        assertThrows(StateTransitionException.class, () -> secondState.transition(ArgumentMultimapBuilder.build(
                secondPrefixes.subList(5, 7), secondArgs.subList(5, 7))));
    }

    @Test
    void getStateConstraints_valid_returnsConstraints() {
        State state = new AttendeesState(new ArgumentMultimap());
        assertEquals(state.getStateConstraints(), "Please enter the list of attendees separated by"
                + " vertical lines and prefixed by \"a/\". Only English names are supported.");
    }

    @Test
    void isEndState_valid_returnsFalse() {
        State state = new AttendeesState(new ArgumentMultimap());
        assertFalse(state.isEndState());
    }

    @Test
    void getPrefix_valid_returnsPrefix() {
        State state = new AttendeesState(new ArgumentMultimap());
        assertEquals(state.getPrefix(), PREFIX_ATTENDEES);
    }
}
