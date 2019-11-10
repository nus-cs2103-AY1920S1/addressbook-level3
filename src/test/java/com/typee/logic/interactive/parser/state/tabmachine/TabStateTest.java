package com.typee.logic.interactive.parser.state.tabmachine;

import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_DATE;
import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_TAB;
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

class TabStateTest {

    @Test
    void transition_validArgumentMultimapOneValidInput_returnsPostTransitionState() {
        try {
            // Equivalence partition : Valid tab inputs.

            List<Prefix> prefixes = List.of(PREFIX_TAB);

            // Calendar
            List<String> firstArgs = List.of("calendar");

            // Engagement
            List<String> secondArgs = List.of("engageMeNt");

            State firstState = new TabState(new ArgumentMultimap());
            State secondState = new TabState(new ArgumentMultimap());

            State firstPostTransitionState = firstState.transition(
                    ArgumentMultimapBuilder.build(prefixes, firstArgs));
            State secondPostTransitionState = secondState.transition(
                    ArgumentMultimapBuilder.build(prefixes, secondArgs));

            assertEquals(firstPostTransitionState, new TabEndState(
                    ArgumentMultimapBuilder.build(prefixes, firstArgs)));
            assertEquals(secondPostTransitionState, new TabEndState(
                    ArgumentMultimapBuilder.build(prefixes, secondArgs)));
        } catch (StateTransitionException e) {
            fail();
        }
    }

    @Test
    void transition_validArgumentMultimapInvalidInput_throwsStateTransitionException() {
        // Equivalence Partitions : Invalid tabs, blank strings.

        List<Prefix> prefixes = List.of(PREFIX_TAB);

        // EP : Invalid tab
        List<String> firstArgs = List.of("myTab");

        // EP : Invalid tab
        List<String> secondArgs = List.of("DamithIsGod");

        // EP : Blank string
        List<String> thirdArgs = List.of("  ");

        // EP : Blank string
        List<String> fourthArgs = List.of("");

        State firstState = new TabState(new ArgumentMultimap());
        State secondState = new TabState(new ArgumentMultimap());
        State thirdState = new TabState(new ArgumentMultimap());
        State fourthState = new TabState(new ArgumentMultimap());

        assertThrows(StateTransitionException.class, () -> firstState.transition(
                ArgumentMultimapBuilder.build(prefixes, firstArgs)));
        assertThrows(StateTransitionException.class, () -> secondState.transition(
                ArgumentMultimapBuilder.build(prefixes, secondArgs)));
        assertThrows(StateTransitionException.class, () -> thirdState.transition(
                ArgumentMultimapBuilder.build(prefixes, thirdArgs)));
        assertThrows(StateTransitionException.class, () -> fourthState.transition(
                ArgumentMultimapBuilder.build(prefixes, fourthArgs)));

    }

    @Test
    void transition_invalidArgumentMultimap_throwsStateTransitionException() {
        // Equivalence Partitions : Missing prefix, excessive arguments.

        // EP - Missing prefix
        State firstState = new TabState(new ArgumentMultimap());
        assertThrows(StateTransitionException.class, () -> firstState.transition(new ArgumentMultimap()));

        // EP - Excessive arguments.
        List<Prefix> prefixes = List.of(PREFIX_TAB, PREFIX_DATE);
        List<String> args = List.of("Engagement", "11/01/2000");

        State secondState = new TabState(new ArgumentMultimap());
        assertThrows(StateTransitionException.class, () -> secondState.transition(
                ArgumentMultimapBuilder.build(prefixes, args)));
    }

    @Test
    void getStateConstraints_valid_returnsConstraints() {
        State state = new TabState(new ArgumentMultimap());
        assertEquals(state.getStateConstraints(), "Tab command initiated. Please enter the tab you would like"
                + " to shift to, prefixed by \"b/\"."
                + " The available tabs are \"game\", \"calendar\", \"engagement\" and \"report\".");
    }

    @Test
    void isEndState_valid_returnsFalse() {
        State state = new TabState(new ArgumentMultimap());
        assertFalse(state.isEndState());
    }

    @Test
    void getPrefix_valid_returnsPrefix() {
        State state = new TabState(new ArgumentMultimap());
        assertEquals(state.getPrefix(), PREFIX_TAB);
    }
}
