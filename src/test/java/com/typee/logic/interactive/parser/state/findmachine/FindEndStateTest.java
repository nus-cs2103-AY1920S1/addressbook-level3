package com.typee.logic.interactive.parser.state.findmachine;

import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_ATTENDEES;
import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_DESCRIPTION;
import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_END_TIME;
import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_ENGAGEMENT_TYPE;
import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_LOCATION;
import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_START_TIME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.Prefix;
import com.typee.logic.interactive.parser.state.State;
import com.typee.logic.interactive.parser.state.exceptions.StateTransitionException;
import com.typee.testutil.ArgumentMultimapBuilder;

public class FindEndStateTest {
    private FindEndState findEndState;

    private List<Prefix> prefixes = List.of(PREFIX_ENGAGEMENT_TYPE, PREFIX_START_TIME, PREFIX_END_TIME, PREFIX_LOCATION,
            PREFIX_DESCRIPTION, PREFIX_ATTENDEES);

    @BeforeEach
    public void setUp() {
        findEndState = new FindEndState(new ArgumentMultimap());
    }

    @Test
    public void transition_validArgumentMultiMapInvalidInput_throwsStateTransitionException() {
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

        State firstState = new FindEndState(ArgumentMultimapBuilder.build(
                prefixes.subList(0, 2), firstArgs.subList(0, 2)));
        State secondState = new FindEndState(ArgumentMultimapBuilder.build(
                prefixes.subList(0, 2), secondArgs.subList(0, 2)));
        State thirdState = new FindEndState(ArgumentMultimapBuilder.build(
                prefixes.subList(0, 2), thirdArgs.subList(0, 2)));
        State fourthState = new FindEndState(ArgumentMultimapBuilder.build(
                prefixes.subList(0, 2), fourthArgs.subList(0, 2)));
        State fifthState = new FindEndState(ArgumentMultimapBuilder.build(
                prefixes.subList(0, 2), fifthArgs.subList(0, 2)));
        State sixthState = new FindEndState(ArgumentMultimapBuilder.build(
                prefixes.subList(0, 2), sixthArgs.subList(0, 2)));
        State seventhState = new FindEndState(ArgumentMultimapBuilder.build(
                prefixes.subList(0, 2), seventhArgs.subList(0, 2)));
        State eighthState = new FindEndState(ArgumentMultimapBuilder.build(
                prefixes.subList(0, 2), eighthArgs.subList(0, 2)));

        Assertions.assertThrows(StateTransitionException.class, () -> firstState.transition(
                ArgumentMultimapBuilder.build(prefixes.subList(2, 3), firstArgs.subList(2, 3))));
        Assertions.assertThrows(StateTransitionException.class, () -> secondState.transition(
                ArgumentMultimapBuilder.build(prefixes.subList(2, 3), secondArgs.subList(2, 3))));
        Assertions.assertThrows(StateTransitionException.class, () -> thirdState.transition(
                ArgumentMultimapBuilder.build(prefixes.subList(2, 3), thirdArgs.subList(2, 3))));
        Assertions.assertThrows(StateTransitionException.class, () -> fourthState.transition(
                ArgumentMultimapBuilder.build(prefixes.subList(2, 3), fourthArgs.subList(2, 3))));
        Assertions.assertThrows(StateTransitionException.class, () -> fifthState.transition(
                ArgumentMultimapBuilder.build(prefixes.subList(2, 3), fifthArgs.subList(2, 3))));
        Assertions.assertThrows(StateTransitionException.class, () -> sixthState.transition(
                ArgumentMultimapBuilder.build(prefixes.subList(2, 3), sixthArgs.subList(2, 3))));
        Assertions.assertThrows(StateTransitionException.class, () -> seventhState.transition(
                ArgumentMultimapBuilder.build(prefixes.subList(2, 3), seventhArgs.subList(2, 3))));
        Assertions.assertThrows(StateTransitionException.class, () -> eighthState.transition(
                ArgumentMultimapBuilder.build(prefixes.subList(2, 3), eighthArgs.subList(2, 3))));

    }

    @Test
    public void getStateConstraints_valid_returnsConstraints() {
        assertEquals(findEndState.getStateConstraints(), "Displaying filtered engagement list.");

    }

    @Test
    public void isEndState_valid_returnsTrue() {
        assertTrue(findEndState.isEndState());
    }

    @Test
    public void getPrefix_valid_returnsPrefix() {
        assertEquals(findEndState.getPrefix(), new Prefix(""));
    }
}
