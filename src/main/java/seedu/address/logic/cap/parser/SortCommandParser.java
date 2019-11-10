package seedu.address.logic.cap.parser;

import seedu.address.logic.cap.commands.SortCommand;

/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns a DeleteCommand object for execution.
     * */
    public SortCommand parse(String args) {
        return new SortCommand();
    }

}
