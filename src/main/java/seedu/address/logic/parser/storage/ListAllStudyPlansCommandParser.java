package seedu.address.logic.parser.storage;

import seedu.address.logic.commands.storage.ListAllStudyPlansCommand;
import seedu.address.logic.parser.Parser;

/**
 * Parses input arguments and creates a new ListAllStudyPlansCommand object.
 */
public class ListAllStudyPlansCommandParser implements Parser<ListAllStudyPlansCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the
     * ListAllStudyPlansCommand and returns a ListAllStudyPlansCommand object for
     * execution.
     */
    public ListAllStudyPlansCommand parse(String args) {
        return new ListAllStudyPlansCommand();
    }
}
