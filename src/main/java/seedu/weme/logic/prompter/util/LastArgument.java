package seedu.weme.logic.prompter.util;

import seedu.weme.logic.parser.Prefix;

/**
 * A wrapper class for the last argument in user input.
 */
public class LastArgument {
    private Prefix prefix;
    private String argument;

    public LastArgument(Prefix prefix, String argument) {
        this.prefix = prefix;
        this.argument = argument;
    }

    public Prefix getPrefix() {
        return prefix;
    }

    public String getArgument() {
        return argument;
    }
}
