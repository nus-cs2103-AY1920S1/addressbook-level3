package seedu.address.logic.parser.storage;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.storage.DeleteCommitCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommitCommand object.
 */
public class DeleteCommitCommandParser implements Parser<DeleteCommitCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the
     * DeleteCommitCommand and returns a DeleteCommitCommand object for
     * execution.
     *
     * @throws ParseException if the user input does not conform the expected format.
     */
    public DeleteCommitCommand parse(String args) throws ParseException {
        String[] commitToken = args.trim().split("\\.");
        if (commitToken.length != 2 || !commitToken[0].matches("^\\s*-?[0-9]{1,10}\\s*$")
                || !commitToken[1].matches("^\\s*-?[0-9]{1,10}\\s*$")) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommitCommand.MESSAGE_USAGE));
        }
        try {
            int studyPlanIndex = Integer.parseInt(commitToken[0]);
            int commitNumber = Integer.parseInt(commitToken[1]);
            return new DeleteCommitCommand(studyPlanIndex, commitNumber);
        } catch (NumberFormatException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommitCommand.MESSAGE_USAGE));
        }
    }

}
