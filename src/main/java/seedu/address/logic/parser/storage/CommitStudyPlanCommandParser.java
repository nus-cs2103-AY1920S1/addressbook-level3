package seedu.address.logic.parser.storage;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.storage.CommitStudyPlanCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new CommitStudyPlanCommand object
 */
public class CommitStudyPlanCommandParser implements Parser<CommitStudyPlanCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the
     * CommitStudyPlanCommand and returns an CommitStudyPlanCommand object for
     * execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public CommitStudyPlanCommand parse(String args) throws ParseException {
        String commitName = args.trim();
        if (commitName.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CommitStudyPlanCommand.MESSAGE_USAGE));
        }
        return new CommitStudyPlanCommand(commitName);
    }
}
