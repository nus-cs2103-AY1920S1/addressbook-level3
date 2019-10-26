package seedu.weme.logic.parser.commandparser.templatecommandparser;

import static seedu.weme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.weme.commons.core.index.Index;
import seedu.weme.logic.commands.templatecommand.TemplateDeleteCommand;
import seedu.weme.logic.parser.Parser;
import seedu.weme.logic.parser.exceptions.ParseException;
import seedu.weme.logic.parser.util.ParserUtil;

/**
 * Parses input arguments and creates a new TemplateDeleteCommand object
 */
public class TemplateDeleteCommandParser implements Parser<TemplateDeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the TemplateDeleteCommand
     * and returns a TemplateDeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public TemplateDeleteCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new TemplateDeleteCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TemplateDeleteCommand.MESSAGE_USAGE), pe);
        }
    }

}
