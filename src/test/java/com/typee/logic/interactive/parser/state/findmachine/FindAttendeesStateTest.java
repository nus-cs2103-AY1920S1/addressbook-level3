package com.typee.logic.interactive.parser.state.findmachine;

import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_ATTENDEES;
import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_DESCRIPTION;
import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_END_TIME;
import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_ENGAGEMENT_TYPE;
import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_LOCATION;
import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_START_TIME;
import static com.typee.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.Prefix;
import com.typee.logic.interactive.parser.state.State;
import com.typee.logic.interactive.parser.state.exceptions.StateTransitionException;
import com.typee.testutil.ArgumentMultimapBuilder;

public class FindAttendeesStateTest {

    private FindAttendeesState findAttendeesState;
    private List<Prefix> prefixes = List.of(PREFIX_ENGAGEMENT_TYPE, PREFIX_START_TIME, PREFIX_END_TIME, PREFIX_LOCATION,
            PREFIX_DESCRIPTION, PREFIX_ATTENDEES);

    // EP : One attendee.
    private List<String> firstValidArgs = List.of("meeting", "15/11/2019/1400", "15/11/2019/1500", "COM-1",
            "Desc", "Smith", "High");

    // EP : More than one attendee.
    private List<String> secondValidArgs = List.of("meeting", "29/02/2016/1400", "29/02/2016/1600", "COM-1",
            "Desc", "Jon| Snow |Thrones", "none");

    // EP : Blank input
    private List<String> firstInvalidArgs = List.of("appointment", "28/02/2015/1500", "28/02/2015/1600", "Com-2",
            "Desc", "  ");

    // EP : Blank input
    private List<String> secondInvalidArgs = List.of("meeting", "28/02/2015/1500", "28/02/2015/1600", "FASS", "Desc",
            "");

    // EP : Invalid name
    private List<String> thirdInvalidArgs = List.of("meeting", "28/02/2015/1500", "28/02/2015/1600", "FASS",
            "Desc", "Haddaway, 1993");

    // EP : Invalid name
    private List<String> fourthInvalidArgs = List.of("meeting", "28/02/2015/1500", "28/02/2015/1600", "FASS",
            "Desc", "What is Love |");

    // EP : Invalid name
    private List<String> fifthInvalidArgs = List.of("meeting", "28/02/2015/1500", "28/02/2015/1600", "FASS",
            "Desc", "Baby don't | Hurt Me | No More");

    // EP : Invalid format
    private List<String> sixthInvalidArgs = List.of("meeting", "28/02/2015/1500", "28/02/2015/1600", "FASS",
            "Desc", "This, Is, The, Rhythm");

    // EP : Invalid format
    private List<String> seventhInvalidArgs = List.of("meeting", "28/02/2015/1500", "28/02/2015/1600", "FASS",
            "Desc", "Of || The Night");

    // EP : Invalid format
    private List<String> eighthInvalidArgs = List.of("meeting", "28/02/2015/1500", "28/02/2015/1600", "FASS",
            "Desc", "Baby don't | Hurt Me | No More |");

    @BeforeEach
    public void setUp() {
        findAttendeesState = new FindAttendeesState(new ArgumentMultimap());
    }

    @Test
    public void transition_validArgumentMultiMapInvalidInput_throwsStateTransitionException() {

        State firstState = new FindAttendeesState(ArgumentMultimapBuilder.build(
                prefixes.subList(0, 5), firstInvalidArgs.subList(0, 5)));
        State secondState = new FindAttendeesState(ArgumentMultimapBuilder.build(
                prefixes.subList(0, 5), secondInvalidArgs.subList(0, 5)));
        State thirdState = new FindAttendeesState(ArgumentMultimapBuilder.build(
                prefixes.subList(0, 5), thirdInvalidArgs.subList(0, 5)));
        State fourthState = new FindAttendeesState(ArgumentMultimapBuilder.build(
                prefixes.subList(0, 5), fourthInvalidArgs.subList(0, 5)));
        State fifthState = new FindAttendeesState(ArgumentMultimapBuilder.build(
                prefixes.subList(0, 5), fifthInvalidArgs.subList(0, 5)));
        State sixthState = new FindAttendeesState(ArgumentMultimapBuilder.build(
                prefixes.subList(0, 5), sixthInvalidArgs.subList(0, 5)));
        State seventhState = new FindAttendeesState(ArgumentMultimapBuilder.build(
                prefixes.subList(0, 5), seventhInvalidArgs.subList(0, 5)));
        State eighthState = new FindAttendeesState(ArgumentMultimapBuilder.build(
                prefixes.subList(0, 5), eighthInvalidArgs.subList(0, 5)));

        assertThrows(StateTransitionException.class, () -> firstState.transition(
                ArgumentMultimapBuilder.build(prefixes.subList(5, 6), firstInvalidArgs.subList(5, 6))));
        assertThrows(StateTransitionException.class, () -> secondState.transition(
                ArgumentMultimapBuilder.build(prefixes.subList(5, 6), secondInvalidArgs.subList(5, 6))));
        assertThrows(StateTransitionException.class, () -> thirdState.transition(
                ArgumentMultimapBuilder.build(prefixes.subList(5, 6), thirdInvalidArgs.subList(5, 6))));
        assertThrows(StateTransitionException.class, () -> fourthState.transition(
                ArgumentMultimapBuilder.build(prefixes.subList(5, 6), fourthInvalidArgs.subList(5, 6))));
        assertThrows(StateTransitionException.class, () -> fifthState.transition(
                ArgumentMultimapBuilder.build(prefixes.subList(5, 6), fifthInvalidArgs.subList(5, 6))));
        assertThrows(StateTransitionException.class, () -> sixthState.transition(
                ArgumentMultimapBuilder.build(prefixes.subList(5, 6), sixthInvalidArgs.subList(5, 6))));
        assertThrows(StateTransitionException.class, () -> seventhState.transition(
                ArgumentMultimapBuilder.build(prefixes.subList(5, 6), seventhInvalidArgs.subList(5, 6))));
        assertThrows(StateTransitionException.class, () -> eighthState.transition(
                ArgumentMultimapBuilder.build(prefixes.subList(5, 6), eighthInvalidArgs.subList(5, 6))));
    }

    @Test
    public void getStateConstraints_valid_returnsConstraints() {
        assertEquals(findAttendeesState.getStateConstraints(), "Please enter the attendees to search for "
                + "prefixed by \"a/\". The presence of ANY attendee will be considered a match. Vertical lines should "
                + "separate attendees.");
    }

    @Test
    public void isEndState_valid_returnsFalse() {
        assertFalse(findAttendeesState.isEndState());
    }

    @Test
    public void getPrefix_valid_returnsPrefix() {
        assertEquals(findAttendeesState.getPrefix(), PREFIX_ATTENDEES);
    }

    @Test
    public void canBeSkipped_emptyArguments_returnsTrue() {
        assertEquals(findAttendeesState.canBeSkipped(new ArgumentMultimap()), true);
    }
}
