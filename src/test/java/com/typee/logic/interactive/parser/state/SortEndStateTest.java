package com.typee.logic.interactive.parser.state;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.typee.logic.commands.SortCommand;
import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.CliSyntax;
import com.typee.logic.interactive.parser.Prefix;
import com.typee.logic.interactive.parser.state.exceptions.StateTransitionException;
import com.typee.logic.interactive.parser.state.sortmachine.PropertyState;
import com.typee.model.util.EngagementComparator;

public class SortEndStateTest {

    private EndState endStateStartAscending;
    private EndState endStateStartDescending;
    private EndState endStateEndAscending;
    private EndState endStateEndDescending;
    private EndState endStatePriorityAscending;
    private EndState endStatePriorityDescending;
    private EndState endStateDescriptionAscending;
    private EndState endStateDescriptionDescending;

    @BeforeEach
    public void setup() {
        endStateStartAscending = produceEndstateStartAscending();
        endStateStartDescending = produceEndstateStartDescending();
        endStateEndAscending = produceEndstateEndAscending();
        endStateEndDescending = produceEndstateEndDescending();
        endStatePriorityAscending = produceEndstatePriorityAscending();
        endStatePriorityDescending = produceEndstatePriorityDescending();
        endStateDescriptionAscending = produceEndstateDescriptionAscending();
        endStateDescriptionDescending = produceEndstateDescriptionDescending();
    }

    @Test
    public void buildCommand() {
        assertDoesNotThrow(()
            -> assertEquals(endStateStartAscending.buildCommand(),
            new SortCommand(EngagementComparator.START_TIME)));
        assertDoesNotThrow(()
            -> assertEquals(endStateStartDescending.buildCommand(),
            new SortCommand(EngagementComparator.START_TIME_REVERSE)));
        assertDoesNotThrow(()
            -> assertEquals(endStateEndAscending.buildCommand(),
            new SortCommand(EngagementComparator.END_TIME)));
        assertDoesNotThrow(()
            -> assertEquals(endStateEndDescending.buildCommand(),
            new SortCommand(EngagementComparator.END_TIME_REVERSE)));
        assertDoesNotThrow(()
            -> assertEquals(endStateDescriptionAscending.buildCommand(),
            new SortCommand(EngagementComparator.ALPHABETICAL)));
        assertDoesNotThrow(()
            -> assertEquals(endStateDescriptionDescending.buildCommand(),
            new SortCommand(EngagementComparator.ALPHABETICAL_REVERSE)));
        assertDoesNotThrow(()
            -> assertEquals(endStatePriorityAscending.buildCommand(),
            new SortCommand(EngagementComparator.PRIORITY)));
        assertDoesNotThrow(()
            -> assertEquals(endStatePriorityDescending.buildCommand(),
            new SortCommand(EngagementComparator.PRIORITY_REVERSE)));
    }

    @Test
    public void transition() {
        assertThrows(StateTransitionException.class, ()
            -> endStatePriorityDescending.transition(new ArgumentMultimap()));
    }

    @Test
    public void getStateConstraints() {
        assertEquals(endStatePriorityDescending.getStateConstraints(), "Sorting engagements.");
    }

    @Test
    public void isEndState() {
        assertTrue(endStatePriorityDescending.isEndState());
    }

    @Test
    public void getPrefix() {
        assertEquals(endStatePriorityDescending.getPrefix(), new Prefix(""));
    }

    /**
     * Generates the SortEndState by the order StartAscending.
     *
     * @return generated SortEndState
     */
    private EndState produceEndstateStartAscending() {
        EndState postTransitionState;
        ArgumentMultimap sofar = new ArgumentMultimap();
        State state = new PropertyState(new ArgumentMultimap());
        sofar.put(CliSyntax.PREFIX_PROPERTY, "start");
        try {
            state = state.transition(sofar);
        } catch (StateTransitionException e) {
            // StateTransitionException should not be thrown here.
            throw new AssertionError();
        }
        sofar.put(CliSyntax.PREFIX_ORDER, "ascending");
        try {
            state = state.transition(sofar);
        } catch (StateTransitionException e) {
            // StateTransitionException should not be thrown here.
            throw new AssertionError();
        }
        postTransitionState = (EndState) state;
        return postTransitionState;
    }

    /**
     * Generates the SortEndState by the order StartDescending.
     *
     * @return generated SortEndState
     */
    private EndState produceEndstateStartDescending() {
        EndState postTransitionState;
        ArgumentMultimap sofar = new ArgumentMultimap();
        State state = new PropertyState(new ArgumentMultimap());
        sofar.put(CliSyntax.PREFIX_PROPERTY, "start");
        try {
            state = state.transition(sofar);
        } catch (StateTransitionException e) {
            // StateTransitionException should not be thrown here.
            throw new AssertionError();
        }
        sofar.put(CliSyntax.PREFIX_ORDER, "descending");
        try {
            state = state.transition(sofar);
        } catch (StateTransitionException e) {
            // StateTransitionException should not be thrown here.
            throw new AssertionError();
        }
        postTransitionState = (EndState) state;
        return postTransitionState;
    }

    /**
     * Generates the SortEndState by the order EndAscending.
     *
     * @return generated SortEndState
     */
    private EndState produceEndstateEndAscending() {
        EndState postTransitionState;
        ArgumentMultimap sofar = new ArgumentMultimap();
        State state = new PropertyState(new ArgumentMultimap());
        sofar.put(CliSyntax.PREFIX_PROPERTY, "end");
        try {
            state = state.transition(sofar);
        } catch (StateTransitionException e) {
            // StateTransitionException should not be thrown here.
            throw new AssertionError();
        }
        sofar.put(CliSyntax.PREFIX_ORDER, "ascending");
        try {
            state = state.transition(sofar);
        } catch (StateTransitionException e) {
            // StateTransitionException should not be thrown here.
            throw new AssertionError();
        }
        postTransitionState = (EndState) state;
        return postTransitionState;
    }

    /**
     * Generates the SortEndState by the order EndDescending.
     *
     * @return generated SortEndState
     */
    private EndState produceEndstateEndDescending() {
        EndState postTransitionState;
        ArgumentMultimap sofar = new ArgumentMultimap();
        State state = new PropertyState(new ArgumentMultimap());
        sofar.put(CliSyntax.PREFIX_PROPERTY, "end");
        try {
            state = state.transition(sofar);
        } catch (StateTransitionException e) {
            // StateTransitionException should not be thrown here.
            throw new AssertionError();
        }
        sofar.put(CliSyntax.PREFIX_ORDER, "descending");
        try {
            state = state.transition(sofar);
        } catch (StateTransitionException e) {
            // StateTransitionException should not be thrown here.
            throw new AssertionError();
        }
        postTransitionState = (EndState) state;
        return postTransitionState;
    }

    /**
     * Generates the SortEndState by the order PriorityAscending.
     *
     * @return generated SortEndState
     */
    private EndState produceEndstatePriorityAscending() {
        EndState postTransitionState;
        ArgumentMultimap sofar = new ArgumentMultimap();
        State state = new PropertyState(new ArgumentMultimap());
        sofar.put(CliSyntax.PREFIX_PROPERTY, "priority");
        try {
            state = state.transition(sofar);
        } catch (StateTransitionException e) {
            // StateTransitionException should not be thrown here.
            throw new AssertionError();
        }
        sofar.put(CliSyntax.PREFIX_ORDER, "ascending");
        try {
            state = state.transition(sofar);
        } catch (StateTransitionException e) {
            // StateTransitionException should not be thrown here.
            throw new AssertionError();
        }
        postTransitionState = (EndState) state;
        return postTransitionState;
    }

    /**
     * Generates the SortEndState by the order PriorityDescending.
     *
     * @return generated SortEndState
     */
    private EndState produceEndstatePriorityDescending() {
        EndState postTransitionState;
        ArgumentMultimap sofar = new ArgumentMultimap();
        State state = new PropertyState(new ArgumentMultimap());
        sofar.put(CliSyntax.PREFIX_PROPERTY, "priority");
        try {
            state = state.transition(sofar);
        } catch (StateTransitionException e) {
            // StateTransitionException should not be thrown here.
            throw new AssertionError();
        }
        sofar.put(CliSyntax.PREFIX_ORDER, "descending");
        try {
            state = state.transition(sofar);
        } catch (StateTransitionException e) {
            // StateTransitionException should not be thrown here.
            throw new AssertionError();
        }
        postTransitionState = (EndState) state;
        return postTransitionState;
    }

    /**
     * Generates the SortEndState by the order DescriptionAscending.
     *
     * @return generated SortEndState
     */
    private EndState produceEndstateDescriptionAscending() {
        EndState postTransitionState;
        ArgumentMultimap sofar = new ArgumentMultimap();
        State state = new PropertyState(new ArgumentMultimap());
        sofar.put(CliSyntax.PREFIX_PROPERTY, "description");
        try {
            state = state.transition(sofar);
        } catch (StateTransitionException e) {
            // StateTransitionException should not be thrown here.
            throw new AssertionError();
        }
        sofar.put(CliSyntax.PREFIX_ORDER, "ascending");
        try {
            state = state.transition(sofar);
        } catch (StateTransitionException e) {
            // StateTransitionException should not be thrown here.
            throw new AssertionError();
        }
        postTransitionState = (EndState) state;
        return postTransitionState;
    }

    /**
     * Generates the SortEndState by the order DescriptionDescending.
     *
     * @return generated SortEndState
     */
    private EndState produceEndstateDescriptionDescending() {
        EndState postTransitionState;
        ArgumentMultimap sofar = new ArgumentMultimap();
        State state = new PropertyState(new ArgumentMultimap());
        sofar.put(CliSyntax.PREFIX_PROPERTY, "description");
        try {
            state = state.transition(sofar);
        } catch (StateTransitionException e) {
            // StateTransitionException should not be thrown here.
            throw new AssertionError();
        }
        sofar.put(CliSyntax.PREFIX_ORDER, "descending");
        try {
            state = state.transition(sofar);
        } catch (StateTransitionException e) {
            // StateTransitionException should not be thrown here.
            throw new AssertionError();
        }
        postTransitionState = (EndState) state;
        return postTransitionState;
    }
}
