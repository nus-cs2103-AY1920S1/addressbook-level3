package com.typee.logic.interactive.parser.state.findmachine;

import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_ATTENDEES;
import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_END_TIME;
import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_ENGAGEMENT_TYPE;
import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_LOCATION;
import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_PRIORITY;
import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_START_TIME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.Prefix;
import com.typee.logic.interactive.parser.state.State;
import com.typee.logic.interactive.parser.state.exceptions.StateTransitionException;
import com.typee.testutil.ArgumentMultimapBuilder;

class FindPriorityStateTest {
    private FindPriorityState findPriorityState;

    private List<Prefix> prefixes = List.of(PREFIX_ENGAGEMENT_TYPE, PREFIX_START_TIME, PREFIX_END_TIME,
            PREFIX_LOCATION, PREFIX_ATTENDEES, PREFIX_PRIORITY);

    @BeforeEach
    public void setUp() {
        findPriorityState = new FindPriorityState(new ArgumentMultimap());
    }

    @Test
    void transition_validArgumentMultiMapInvalidInput_throwsStateTransitionException() {

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

        State firstState = new FindPriorityState(ArgumentMultimapBuilder.build(
                prefixes.subList(0, 5), firstArgs.subList(0, 5)));
        State secondState = new FindPriorityState(ArgumentMultimapBuilder.build(
                prefixes.subList(0, 5), secondArgs.subList(0, 5)));
        State thirdState = new FindPriorityState(ArgumentMultimapBuilder.build(
                prefixes.subList(0, 5), firstArgs.subList(0, 5)));
        State fourthState = new FindPriorityState(ArgumentMultimapBuilder.build(
                prefixes.subList(0, 5), firstArgs.subList(0, 5)));

        Assertions.assertThrows(StateTransitionException.class, () -> firstState.transition(
                ArgumentMultimapBuilder.build(prefixes.subList(5, 6), firstArgs.subList(5, 6))));
        Assertions.assertThrows(StateTransitionException.class, () -> secondState.transition(
                ArgumentMultimapBuilder.build(prefixes.subList(5, 6), secondArgs.subList(5, 6))));
        Assertions.assertThrows(StateTransitionException.class, () -> thirdState.transition(
                ArgumentMultimapBuilder.build(prefixes.subList(5, 6), thirdArgs.subList(5, 6))));
        Assertions.assertThrows(StateTransitionException.class, () -> fourthState.transition(
                ArgumentMultimapBuilder.build(prefixes.subList(5, 6), fourthArgs.subList(5, 6))));

    }

    @Test
    public void getStateConstraints_valid_returnsConstraints() {
        assertEquals(findPriorityState.getStateConstraints(),
                "Please enter the priority to search for, prefixed by \"p/\".");
    }

    @Test
    public void isEndState_valid_returnsFalse() {
        assertFalse(findPriorityState.isEndState());
    }

    @Test
    public void getPrefix_valid_returnsPrefix() {
        assertEquals(findPriorityState.getPrefix(), PREFIX_PRIORITY);
    }

    @Test
    public void canBeSkipped_emptyArguments_returnsTrue() {
        assertEquals(findPriorityState.canBeSkipped(new ArgumentMultimap()), true);
    }
}
