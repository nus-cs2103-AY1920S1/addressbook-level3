package seedu.address.logic.parser;

import seedu.address.logic.commands.GenReportsCommand;

//@@author bernicechio

/**
 * Parses input signature and creates a new GenReportsCommand object.
 */
public class GenReportsCommandParser implements Parser<GenReportsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the GenReportsCommand
     * and returns a GenReportsCommand object for execution.
     */
    public GenReportsCommand parse(String args) {
        return new GenReportsCommand(args);
    }

}
