package com.typee.logic.interactive.parser.state.tabmachine;

import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_TAB;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.typee.logic.commands.TabCommand;
import com.typee.logic.commands.exceptions.CommandException;
import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.Prefix;
import com.typee.logic.interactive.parser.state.EndState;
import com.typee.logic.interactive.parser.state.exceptions.StateTransitionException;
import com.typee.testutil.ArgumentMultimapBuilder;
import com.typee.ui.Tab;

class TabEndStateTest {

    @Test
    void buildCommand_validTabCalendarCommand_returnsCommand() {
        List<Prefix> prefixes = List.of(PREFIX_TAB);
        List<String> args = List.of("calendar");

        EndState state = new TabEndState(ArgumentMultimapBuilder.build(prefixes, args));
        TabCommand tabCommand = new TabCommand(new Tab("Calendar"));

        try {
            assertEquals(state.buildCommand(), tabCommand);
        } catch (CommandException e) {
            fail();
        }
    }

    @Test
    void buildCommand_validTabEngagementCommand_returnsCommand() {
        List<Prefix> prefixes = List.of(PREFIX_TAB);
        List<String> args = List.of("engagement");

        EndState state = new TabEndState(ArgumentMultimapBuilder.build(prefixes, args));
        TabCommand tabCommand = new TabCommand(new Tab("Engagement"));

        try {
            assertEquals(state.buildCommand(), tabCommand);
        } catch (CommandException e) {
            fail();
        }
    }

    @Test
    void transition_throwsStateTransitionException() {
        EndState endState = new TabEndState(new ArgumentMultimap());
        assertThrows(StateTransitionException.class, () -> endState.transition(new ArgumentMultimap()));
    }

    @Test
    void getStateConstraints_valid_returnsConstraints() {
        EndState endState = new TabEndState(new ArgumentMultimap());
        assertEquals(endState.getStateConstraints(), "Tab successfully shifted!");
    }

    @Test
    void isEndState_valid_returnsTrue() {
        EndState endState = new TabEndState(new ArgumentMultimap());
        assertTrue(endState.isEndState());
    }

    @Test
    void getPrefix_valid_returnsPrefix() {
        EndState endState = new TabEndState(new ArgumentMultimap());
        assertEquals(endState.getPrefix(), new Prefix(""));
    }
}
