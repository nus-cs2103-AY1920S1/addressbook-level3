package seedu.address.logic.parser;

import seedu.address.logic.commands.RedoCommand;

/**
 * Parses input arguments and creates a new {@code RedoCommand} object
 */
public class RedoCommandParser implements Parser<RedoCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code RedoCommand}
     * and returns a {@code RedoCommand} object for execution.
     *
     * @return {@code RedoCommand} object.
     */
    public RedoCommand parse(String args) {
        return new RedoCommand(args);
    }
}
