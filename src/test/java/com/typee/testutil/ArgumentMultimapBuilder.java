package com.typee.testutil;

import java.util.ArrayList;
import java.util.List;

import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.Prefix;

public class ArgumentMultimapBuilder {

    private final List<Prefix> prefixList;
    private final List<String> arguments;

    public ArgumentMultimapBuilder() {
        this.prefixList = new ArrayList<>();
        this.arguments = new ArrayList<>();
    }

    public void addPrefixes(Prefix... prefixes) {
        for (Prefix p : prefixes) {
            prefixList.add(p);
        }
    }

    public void addArguments(String... strings) {
        for (String s : strings) {
            arguments.add(s);
        }
    }

    public ArgumentMultimap build() {
        ArgumentMultimap argumentMultimap = new ArgumentMultimap();
        int parameters = prefixList.size();

        for (int i = 0; i < parameters; i++) {
            argumentMultimap.put(prefixList.get(i), arguments.get(i));
        }

        return argumentMultimap;
    }

}
