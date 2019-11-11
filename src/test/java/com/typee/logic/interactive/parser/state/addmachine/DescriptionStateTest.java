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

class DescriptionStateTest {

    public static final String EXPECTED_CONSTRAINTS = "What is the engagement about? Please enter a brief description "
            + "prefixed by " + PREFIX_DESCRIPTION.getPrefix() + ". Example - [d/CS2103T Discussion.]";

    @Test
    void transition_validArgumentMultimapOneInput_returnsPostTransitionState() {
        try {

            // EP : Valid inputs - valid descriptions.
            // Test with two valid inputs.

            List<Prefix> prefixes = List.of(PREFIX_ENGAGEMENT_TYPE, PREFIX_START_TIME,
                    PREFIX_END_TIME, PREFIX_LOCATION, PREFIX_DESCRIPTION);
            List<String> firstArgs = List.of("interview", "16/01/2019/0000", "16/01/2019/0100",
                    "COM1-B-03", "CS2103T Discussion");
            List<String> secondArgs = List.of("interview", "19/02/2019/0300", "01/03/2020/0100",
                    "My house", "Team meeting");

            State firstState = new DescriptionState(ArgumentMultimapBuilder.build(
                    prefixes.subList(0, 4), firstArgs.subList(0, 4)));
            State secondState = new DescriptionState(ArgumentMultimapBuilder.build(
                    prefixes.subList(0, 4), secondArgs.subList(0, 4)));

            ArgumentMultimap firstNewArgs = ArgumentMultimapBuilder.build(
                    prefixes.subList(4, 5), firstArgs.subList(4, 5));
            ArgumentMultimap secondNewArgs = ArgumentMultimapBuilder.build(
                    prefixes.subList(4, 5), secondArgs.subList(4, 5));



            assertEquals(firstState.transition(firstNewArgs),
                    new AttendeesState(ArgumentMultimapBuilder.build(prefixes, firstArgs)));
            assertEquals(secondState.transition(secondNewArgs),
                    new AttendeesState(ArgumentMultimapBuilder.build(prefixes, secondArgs)));

        } catch (StateTransitionException e) {
            fail();
        }
    }

    @Test
    void transition_validArgumentMultimapMultipleInputs_returnsPostTransitionState() {
        try {

            // EP : Non blank descriptions

            List<Prefix> prefixes = List.of(PREFIX_ENGAGEMENT_TYPE, PREFIX_START_TIME,
                    PREFIX_END_TIME, PREFIX_LOCATION, PREFIX_DESCRIPTION, PREFIX_ATTENDEES, PREFIX_PRIORITY);

            List<String> firstArgs = List.of("meeting", "15/11/2019/1400", "15/11/2019/1500", "COM-1",
                    "Desc", "Smith", "Low");
            List<String> secondArgs = List.of("meeting", "29/02/2016/1400", "29/02/2016/1600", "What is this",
                    "Desc", "Jon", "none");

            State firstInitialState = new DescriptionState(ArgumentMultimapBuilder.build(
                    prefixes.subList(0, 4), firstArgs.subList(0, 4)));
            State secondInitialState = new DescriptionState(ArgumentMultimapBuilder.build(
                    prefixes.subList(0, 4), secondArgs.subList(0, 4)));

            State firstPostTransitionState = firstInitialState.transition(ArgumentMultimapBuilder.build(
                    prefixes.subList(4, 7), firstArgs.subList(4, 7)));
            State secondPostTransitionState = secondInitialState.transition(ArgumentMultimapBuilder.build(
                    prefixes.subList(4, 7), secondArgs.subList(4, 7)));

            assertEquals(firstPostTransitionState, new AttendeesState(ArgumentMultimapBuilder.build
                    (prefixes.subList(0, 5), firstArgs.subList(0, 5))));
            assertEquals(secondPostTransitionState, new AttendeesState(ArgumentMultimapBuilder.build
                    (prefixes.subList(0, 5), secondArgs.subList(0, 5))));

        } catch (StateTransitionException e) {
            fail();
        }

    }

    @Test
    void transition_validArgumentMultiMapInvalidInput_throwsStateTransitionException() {

        // Equivalence Partitions : Blank descriptions, null.
        // Apply at most one invalid input heuristic for the tests.

        List<Prefix> prefixes = List.of(PREFIX_ENGAGEMENT_TYPE, PREFIX_START_TIME, PREFIX_END_TIME,
                PREFIX_LOCATION, PREFIX_DESCRIPTION);

        // EP : Blank description.
        List<String> firstArgs = List.of("appointment", "28/02/2015/1500", "28/02/2015/1600", "Com-2", "");

        // EP : Blank description.
        List<String> secondArgs = List.of("meeting", "28/02/2015/1500", "28/02/2015/1600", "FASS", "  ");

        // null
        // null can't be tested as an input since ArgumentMultimap doesn't support null arguments.

        State firstState = new DescriptionState(ArgumentMultimapBuilder.build(
                prefixes.subList(0, 4), firstArgs.subList(0, 4)));
        State secondState = new DescriptionState(ArgumentMultimapBuilder.build(
                prefixes.subList(0, 4), secondArgs.subList(0, 4)));

        assertThrows(StateTransitionException.class, () -> firstState.transition(
                ArgumentMultimapBuilder.build(prefixes.subList(4, 5), firstArgs.subList(4, 5))));
        assertThrows(StateTransitionException.class, () -> secondState.transition(
                ArgumentMultimapBuilder.build(prefixes.subList(4, 5), secondArgs.subList(4, 5))));

    }

    @Test
    void transition_invalidArgumentMultimap_throwsStateTransitionException() {

        // Equivalence Partitions : ArgumentMultimap without a description prefix, duplicate prefixes.

        // EP : No description prefix
        List<Prefix> firstPrefixes = List.of(PREFIX_ENGAGEMENT_TYPE, PREFIX_START_TIME, PREFIX_END_TIME,
                PREFIX_LOCATION, PREFIX_PRIORITY);

        // EP : Duplicate prefixes.
        List<Prefix> secondPrefixes = List.of(PREFIX_ENGAGEMENT_TYPE, PREFIX_START_TIME, PREFIX_END_TIME,
                PREFIX_LOCATION, PREFIX_DESCRIPTION, PREFIX_LOCATION);

        List<String> firstArgs = List.of("interview", "15/11/2019/1500", "15/11/2019/1600", "COM-2", "low");
        List<String> secondArgs = List.of("interview", "15/11/2019/1500", "15/11/2019/1600", "COM-2", "Desc", "PGP");

        State firstState = new DescriptionState(ArgumentMultimapBuilder.build(
                firstPrefixes.subList(0, 4), firstArgs.subList(0, 4)));
        State secondState = new DescriptionState(ArgumentMultimapBuilder.build(
                secondPrefixes.subList(0, 4), secondArgs.subList(0, 4)));

        assertThrows(StateTransitionException.class, () -> firstState.transition(ArgumentMultimapBuilder.build(
                firstPrefixes.subList(4, 5), firstArgs.subList(4, 5))));
        assertThrows(StateTransitionException.class, () -> secondState.transition(ArgumentMultimapBuilder.build(
                secondPrefixes.subList(4, 6), secondArgs.subList(4, 6))));
    }

    @Test
    void getStateConstraints_valid_returnsConstraints() {
        State state = new DescriptionState(new ArgumentMultimap());
        assertEquals(EXPECTED_CONSTRAINTS, state.getStateConstraints());
    }

    @Test
    void isEndState_valid_returnsFalse() {
        State state = new DescriptionState(new ArgumentMultimap());
        assertFalse(state.isEndState());
    }

    @Test
    void getPrefix_valid_returnsPrefix() {
        State state = new DescriptionState(new ArgumentMultimap());
        assertEquals(PREFIX_DESCRIPTION, state.getPrefix());
    }
}
