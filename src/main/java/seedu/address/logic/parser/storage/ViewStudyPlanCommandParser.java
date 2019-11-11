package seedu.address.logic.parser.storage;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.storage.ViewStudyPlanCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ViewStudyPlanCommand object.
 */
public class ViewStudyPlanCommandParser implements Parser<ViewStudyPlanCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the
     * ViewStudyPlanCommand and returns a ViewStudyPlanCommand object for
     * execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewStudyPlanCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (!trimmedArgs.matches("-?\\d+")) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewStudyPlanCommand.MESSAGE_USAGE));
        }
        try {
            int studyPlanIndex = Integer.parseInt(trimmedArgs);
            return new ViewStudyPlanCommand(studyPlanIndex);
        } catch (NumberFormatException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewStudyPlanCommand.MESSAGE_USAGE));
        }
    }

}
