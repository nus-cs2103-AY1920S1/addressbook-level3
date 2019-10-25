package seedu.address.logic.parser.storage;

import seedu.address.logic.commands.storage.DefaultStudyPlanCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DefaultStudyPlanCommand object.
 */
public class DefaultStudyPlanCommandParser implements Parser<DefaultStudyPlanCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the
     * DefaultStudyPlanCommand and returns an DefaultStudyPlanCommand object for
     * execution.
     *
     * @throws ParseException if the user input does not conform the expected format.
     */
    public DefaultStudyPlanCommand parse(String args) throws ParseException {
        return new DefaultStudyPlanCommand();
    }
}
