package seedu.address.logic.parser;

import seedu.address.logic.commands.DoneCommand;

/**
 * Parses input arguments and creates a new DoneCommand object
 */
public class DoneCommandParser implements Parser<DoneCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DoneCommand
     * and returns a DoneCommand object for execution.
     *
     * @return DoneCommand object.
     */
    public DoneCommand parse(String args) {
        return new DoneCommand();
    }
}
