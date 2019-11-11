package com.typee.logic.interactive.parser.state.findmachine;

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

public class FindLocationStateTest {
    private FindLocationState findLocationState;

    private List<Prefix> prefixes = List.of(PREFIX_ENGAGEMENT_TYPE, PREFIX_START_TIME, PREFIX_END_TIME,
            PREFIX_LOCATION);

    @Test
    public void transition_validArgumentMultiMapInvalidInput_throwsStateTransitionException() {

        // EP : Blank location.
        List<String> firstArgs = List.of("appointment", "28/02/2015/1500", "28/02/2015/1600", "  ");

        // EP : Blank location.
        List<String> secondArgs = List.of("meeting", "28/02/2015/1500", "28/02/2015/1600", "");

        State firstState = new FindLocationState(ArgumentMultimapBuilder.build(
                prefixes.subList(0, 3), firstArgs.subList(0, 3)));
        State secondState = new FindLocationState(ArgumentMultimapBuilder.build(
                prefixes.subList(0, 3), secondArgs.subList(0, 3)));

        Assertions.assertThrows(StateTransitionException.class, () -> firstState.transition(
                ArgumentMultimapBuilder.build(prefixes.subList(3, 4), firstArgs.subList(3, 4))));
        Assertions.assertThrows(StateTransitionException.class, () -> secondState.transition(
                ArgumentMultimapBuilder.build(prefixes.subList(3, 4), secondArgs.subList(3, 4))));
    }

    @BeforeEach
    public void setUp() {
        findLocationState = new FindLocationState(new ArgumentMultimap());
    }

    @Test
    public void getStateConstraints_valid_returnsConstraints() {
        assertEquals(findLocationState.getStateConstraints(), "Please enter a location to search for, prefixed "
                + "by \"l/\".");

    }

    @Test
    public void isEndState_valid_returnsFalse() {
        assertFalse(findLocationState.isEndState());
    }

    @Test
    public void getPrefix_valid_returnsPrefix() {
        assertEquals(findLocationState.getPrefix(), PREFIX_LOCATION);
    }

    @Test
    public void canBeSkipped_emptyArguments_returnsTrue() {
        assertEquals(findLocationState.canBeSkipped(new ArgumentMultimap()), true);
    }
}
