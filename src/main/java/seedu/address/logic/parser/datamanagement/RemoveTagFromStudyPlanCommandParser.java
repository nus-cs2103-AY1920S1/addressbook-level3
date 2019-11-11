//@@author andyylam

package seedu.address.logic.parser.datamanagement;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.datamanagement.RemoveTagFromStudyPlanCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new RemoveTagFromStudyPlanCommand object
 */
public class RemoveTagFromStudyPlanCommandParser implements Parser<RemoveTagFromStudyPlanCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the RemoveTagFromStudyPlanCommand
     * and returns an RemoveTagFromStudyPlanCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemoveTagFromStudyPlanCommand parse(String args) throws ParseException {
        String[] tokens = args.trim().split(" ");
        if (tokens.length < 1 || !tokens[0].matches("\\d+")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RemoveTagFromStudyPlanCommand.MESSAGE_USAGE));
        }
        return new RemoveTagFromStudyPlanCommand(Integer.parseInt(tokens[0]));
    }
}
