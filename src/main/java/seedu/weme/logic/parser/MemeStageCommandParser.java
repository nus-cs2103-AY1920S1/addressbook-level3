package seedu.weme.logic.parser;

import static seedu.weme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.weme.commons.core.index.Index;
import seedu.weme.logic.commands.MemeStageCommand;
import seedu.weme.logic.parser.exceptions.ParseException;


/**
 * MemeStageCommandParser.
 */
public class MemeStageCommandParser implements Parser<MemeStageCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the MemeDeleteCommand
     * and returns a MemeDeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MemeStageCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new MemeStageCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MemeStageCommand.MESSAGE_USAGE), pe);
        }
    }

}
