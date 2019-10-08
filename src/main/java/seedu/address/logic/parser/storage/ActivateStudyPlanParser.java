package seedu.address.logic.parser.storage;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import seedu.address.logic.commands.ActivateStudyPlanCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new CreateStudyPlanCommand object
 */
public class ActivateStudyPlanParser implements Parser<ActivateStudyPlanCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the
     * ActivateStudyPlanCommand and returns an ActivateStudyPlanCommand object for
     * execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ActivateStudyPlanCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (!trimmedArgs.matches("-?\\d+")) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ActivateStudyPlanCommand.MESSAGE_USAGE));
        }
        int studyPlanIndex = Integer.parseInt(trimmedArgs);
        return new ActivateStudyPlanCommand(studyPlanIndex);
    }

}
