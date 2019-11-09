package com.typee.logic.interactive.parser.state.tabmachine;

import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_TAB;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.typee.logic.commands.TabCommand;
import com.typee.logic.commands.exceptions.CommandException;
import com.typee.logic.interactive.parser.Prefix;
import com.typee.logic.interactive.parser.state.EndState;
import com.typee.testutil.ArgumentMultimapBuilder;
import com.typee.ui.Tab;

class TabEndStateTest {

    @Test
    void buildCommand_validCommand_returnsCommand() {
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
    void transition() {
    }

    @Test
    void getStateConstraints() {
    }

    @Test
    void isEndState() {
    }

    @Test
    void getPrefix() {
    }
}