package com.typee.testutil;

import java.util.List;

import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.Prefix;

/**
 * Represents a class that helps build {@code ArgumentMultimaps}.
 */
public class ArgumentMultimapBuilder {

    /**
     * Builds and returns an {@code ArgumentMultimap} containing the entered prefixes and arguments.
     *
     * @param prefixes Prefixes.
     * @param arguments Arguments.
     * @return {@code ArgumentMultimap} with the entered input.
     */
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
