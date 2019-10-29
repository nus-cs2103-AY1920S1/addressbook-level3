package seedu.weme.logic.parser.commandparser.templatecommandparser;

import static seedu.weme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.weme.commons.core.index.Index;
import seedu.weme.logic.commands.templatecommand.TemplateUnarchiveCommand;
import seedu.weme.logic.parser.Parser;
import seedu.weme.logic.parser.exceptions.ParseException;
import seedu.weme.logic.parser.util.ParserUtil;

/**
 * Parses input arguments and creates a new TemplateUnarchiveCommand object
 */
public class TemplateUnarchiveCommandParser implements Parser<TemplateUnarchiveCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the TemplateUnarchiveCommand
     * and returns a TemplateUnarchiveCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public TemplateUnarchiveCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new TemplateUnarchiveCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, TemplateUnarchiveCommand.MESSAGE_USAGE), pe);
        }
    }

}
