package com.typee.logic.interactive.parser.state.findmachine;

import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_ATTENDEES;
import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_DESCRIPTION;
import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_END_TIME;
import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_ENGAGEMENT_TYPE;
import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_LOCATION;
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

public class FindDescriptionStateTest {
    private FindDescriptionState findDescriptionState;

    private List<Prefix> prefixes = List.of(PREFIX_ENGAGEMENT_TYPE, PREFIX_START_TIME, PREFIX_END_TIME, PREFIX_LOCATION,
            PREFIX_DESCRIPTION, PREFIX_ATTENDEES);

    // EP : Blank description.
    private List<String> firstArgs = List.of("appointment", "28/02/2015/1500", "28/02/2015/1600", "Com-2", "");

    // EP : Blank description.
    private List<String> secondArgs = List.of("meeting", "28/02/2015/1500", "28/02/2015/1600", "FASS", "  ");


    @BeforeEach
    public void setUp() {
        findDescriptionState = new FindDescriptionState(new ArgumentMultimap());
    }

    @Test
    public void transition_validArgumentMultiMapInvalidInput_throwsStateTransitionException() {

        State firstState = new FindDescriptionState(ArgumentMultimapBuilder.build(
                prefixes.subList(0, 4), firstArgs.subList(0, 4)));
        State secondState = new FindDescriptionState(ArgumentMultimapBuilder.build(
                prefixes.subList(0, 4), secondArgs.subList(0, 4)));

        Assertions.assertThrows(StateTransitionException.class, () -> firstState.transition(
                ArgumentMultimapBuilder.build(prefixes.subList(4, 5), firstArgs.subList(4, 5))));
        Assertions.assertThrows(StateTransitionException.class, () -> secondState.transition(
                ArgumentMultimapBuilder.build(prefixes.subList(4, 5), secondArgs.subList(4, 5))));

    }

    @Test
    public void getStateConstraints_valid_returnsConstraints() {
        assertEquals(findDescriptionState.getStateConstraints(), "Please enter a description to"
                + " search for prefixed by \"d/\".");
    }

    @Test
    public void isEndState_valid_returnsFalse() {
        assertFalse(findDescriptionState.isEndState());
    }

    @Test
    public void getPrefix_valid_returnsPrefix() {
        assertEquals(findDescriptionState.getPrefix(), PREFIX_DESCRIPTION);
    }

    @Test
    public void canBeSkipped_emptyArguments_returnsTrue() {
        assertEquals(findDescriptionState.canBeSkipped(new ArgumentMultimap()), true);
    }
}
