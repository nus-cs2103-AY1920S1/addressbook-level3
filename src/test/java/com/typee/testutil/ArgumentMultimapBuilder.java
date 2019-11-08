package com.typee.testutil;

import java.util.ArrayList;
import java.util.List;

import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.Prefix;

public class ArgumentMultimapBuilder {

    public static ArgumentMultimap build(List<Prefix> prefixes, List<String> arguments) {
        assert prefixes.size() == arguments.size();
        int numberOfArguments = arguments.size();

        ArgumentMultimap argumentMultimap = new ArgumentMultimap();

        for (int i = 0; i < numberOfArguments; i++) {
            argumentMultimap.put(prefixes.get(i), arguments.get(i));
        }

        return argumentMultimap;
    }

}
