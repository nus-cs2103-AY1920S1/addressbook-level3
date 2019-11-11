package seedu.address.logic.parser.storage;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.storage.DeleteStudyPlanCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteStudyPlanCommandParser object.
 */
public class DeleteStudyPlanCommandParser implements Parser<DeleteStudyPlanCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the
     * DeleteStudyPlanCommand and returns a DeleteStudyPlanCommand object for
     * execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteStudyPlanCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (!trimmedArgs.matches("^\\s*-?[0-9]{1,10}\\s*$")) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteStudyPlanCommand.MESSAGE_USAGE));
        }
        Index studyPlanIndex = Index.fromZeroBased(Integer.parseInt(trimmedArgs));
        return new DeleteStudyPlanCommand(studyPlanIndex);
    }
}
