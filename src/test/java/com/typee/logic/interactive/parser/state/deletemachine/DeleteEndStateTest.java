package com.typee.logic.interactive.parser.state.deletemachine;

import static com.typee.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.typee.commons.core.index.Index;
import com.typee.logic.commands.DeleteCommand;
import com.typee.logic.commands.exceptions.CommandException;
import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.CliSyntax;
import com.typee.logic.interactive.parser.Prefix;
import com.typee.logic.interactive.parser.state.exceptions.StateTransitionException;

class DeleteEndStateTest {

    private DeleteEndState deleteEndState;

    @BeforeEach
    public void setUp() {
        ArgumentMultimap argumentMultimap = new ArgumentMultimap();
        argumentMultimap.put(CliSyntax.PREFIX_LIST_INDEX, "1");
        deleteEndState = new DeleteEndState(argumentMultimap);
    }

    @Test
    public void buildCommand() {
        try {
            assertEquals(deleteEndState.buildCommand(), new DeleteCommand(Index.fromOneBased(1)));
        } catch (CommandException e) {
            // CommandException should not be thrown here.
        }
    }

    @Test
    public void buildCommand_invalidIndex_throwsCommandException() {
        ArgumentMultimap argumentMultimapWithInvalidIndex = new ArgumentMultimap();
        argumentMultimapWithInvalidIndex.put(CliSyntax.PREFIX_LIST_INDEX, "-1");
        DeleteEndState invalidDeleteEndState = new DeleteEndState(argumentMultimapWithInvalidIndex);
        assertThrows(CommandException.class, () -> invalidDeleteEndState.buildCommand());
    }

    @Test
    public void transition() {
        ArgumentMultimap argumentMultimap = new ArgumentMultimap();
        assertThrows(StateTransitionException.class, ()
            -> deleteEndState.transition(argumentMultimap));
    }

    @Test
    public void getStateConstraints() {
        assertEquals(deleteEndState.getStateConstraints(), "Delete command end state");
    }

    @Test
    public void isEndState() {
        assertTrue(deleteEndState.isEndState());
    }

    @Test
    public void getPrefix() {
        assertEquals(deleteEndState.getPrefix(), new Prefix(""));
    }

}
