package seedu.address.logic.parser.storage;

import seedu.address.logic.commands.storage.ViewCommitHistoryCommand;
import seedu.address.logic.parser.Parser;

/**
 * Parses input arguments and creates a new ViewCommitHistoryCommand object.
 */
public class ViewCommitHistoryParser implements Parser<ViewCommitHistoryCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the
     * ViewCommitHistoryCommand and returns an ViewCommitHistoryCommand object for
     * execution.
     */
    public ViewCommitHistoryCommand parse(String args) {
        return new ViewCommitHistoryCommand();
    }

}
