package com.typee.logic.interactive.parser.state.addmachine;

import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_ENGAGEMENT_TYPE;
import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_START_TIME;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.Prefix;
import com.typee.logic.interactive.parser.state.State;
import com.typee.testutil.ArgumentMultimapBuilder;

class StartDateStateTest {

    @Test
    void transition_validArgumentMultimapOneInput_returnsPostTransitionState() {
        /*
        ArgumentMultimapBuilder argumentMultimapBuilder = new ArgumentMultimapBuilder();

        List<Prefix> prefixes = List.of(PREFIX_ENGAGEMENT_TYPE);
        List<String> arguments = List.of("interview");
        argumentMultimapBuilder.addPrefixes(prefixes.toArray(Prefix[]::new));
        argumentMultimapBuilder.addArguments(arguments.toArray(String[]::new));

        ArgumentMultimap firstMap = argumentMultimapBuilder.build();
        ArgumentMultimap secondMap = argumentMultimapBuilder.build();

        argumentMultimapBuilder.addPrefixes(PREFIX_START_TIME);

        State firstState = new StartDateState(firstMap);
        State firstPostTransitionState = firstState.transition();
        assertEquals();


         */
    }
}