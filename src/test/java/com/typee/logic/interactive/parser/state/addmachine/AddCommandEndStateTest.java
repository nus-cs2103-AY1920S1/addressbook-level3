package com.typee.logic.interactive.parser.state.addmachine;

import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_ATTENDEES;
import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_DESCRIPTION;
import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_END_TIME;
import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_ENGAGEMENT_TYPE;
import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_LOCATION;
import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_PRIORITY;
import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_START_TIME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.typee.logic.commands.AddCommand;
import com.typee.logic.commands.exceptions.CommandException;
import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.Prefix;
import com.typee.logic.interactive.parser.state.EndState;
import com.typee.logic.interactive.parser.state.State;
import com.typee.logic.interactive.parser.state.exceptions.StateTransitionException;
import com.typee.model.engagement.Engagement;
import com.typee.testutil.ArgumentMultimapBuilder;
import com.typee.testutil.EngagementBuilder;

class AddCommandEndStateTest {

    @Test
    void buildCommand_validCommand_returnsCommand() {
        List<Prefix> prefixes = List.of(PREFIX_ENGAGEMENT_TYPE, PREFIX_START_TIME, PREFIX_END_TIME, PREFIX_LOCATION,
                PREFIX_DESCRIPTION, PREFIX_ATTENDEES, PREFIX_PRIORITY);
        List<String> args = List.of("meeting", "01/01/2020/0100", "01/01/2020/0200", "University Town",
                "Tea party", "Alice Pauline", "low");

        EndState endState = new AddCommandEndState(ArgumentMultimapBuilder.build(prefixes, args));

        EngagementBuilder engagementBuilder = new EngagementBuilder();
        Engagement engagement = engagementBuilder.buildAsMeeting();

        try {
            assertEquals(endState.buildCommand(), new AddCommand(engagement));
        } catch (CommandException e) {
            fail();
        }
    }

    @Test
    void transition_throwsStateTransitionException() {
        State state = new AddCommandEndState(new ArgumentMultimap());
        assertThrows(StateTransitionException.class, () -> state.transition(new ArgumentMultimap()));
    }

    @Test
    void getStateConstraints_valid_returnsConstraints() {
        State state = new AddCommandEndState(new ArgumentMultimap());
        assertEquals(state.getStateConstraints(), "Engagement successfully added!");
    }

    @Test
    void isEndState_valid_returnsTrue() {
        State state = new AddCommandEndState(new ArgumentMultimap());
        assertTrue(state.isEndState());
    }

    @Test
    void getPrefix_valid_returnsPrefix() {
        State state = new AddCommandEndState(new ArgumentMultimap());
        assertEquals(state.getPrefix(), new Prefix(""));
    }
}
