package seedu.address.logic.parser;

import seedu.address.logic.commands.UndoCommand;

/**
 * Parses input arguments and creates a new {@code UndoCommand} object
 */
public class UndoCommandParser implements Parser<UndoCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code UndoCommand}
     * and returns a {@code UndoCommand} object for execution.
     *
     * @return {@code UndoCommand} object.
     */
    public UndoCommand parse(String args) {
        return new UndoCommand(args);
    }
}
