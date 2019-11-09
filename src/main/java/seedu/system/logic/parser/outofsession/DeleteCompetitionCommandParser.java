package seedu.system.logic.parser.outofsession;

import static seedu.system.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.system.commons.core.index.Index;
import seedu.system.logic.commands.outofsession.DeleteCompetitionCommand;
import seedu.system.logic.parser.Parser;
import seedu.system.logic.parser.ParserUtil;
import seedu.system.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCompetitionCommand object.
 */
public class DeleteCompetitionCommandParser implements Parser<DeleteCompetitionCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCompetitionCommand
     * and returns a DeleteCompetitionCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCompetitionCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteCompetitionCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCompetitionCommand.MESSAGE_USAGE), pe);
        }
    }

}
