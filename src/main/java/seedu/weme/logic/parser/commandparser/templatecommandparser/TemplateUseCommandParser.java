package seedu.weme.logic.parser.commandparser.templatecommandparser;

import static java.util.Objects.requireNonNull;
import static seedu.weme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.weme.commons.core.index.Index;
import seedu.weme.logic.commands.templatecommand.TemplateUseCommand;
import seedu.weme.logic.parser.Parser;
import seedu.weme.logic.parser.exceptions.ParseException;
import seedu.weme.logic.parser.util.ParserUtil;

/**
 * Parses input arguments and creates a new TemplateUseCommand object
 */
public class TemplateUseCommandParser implements Parser<TemplateUseCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the TemplateUseCommand
     * and returns an TemplateUseCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public TemplateUseCommand parse(String args) throws ParseException {
        requireNonNull(args);
        try {
            Index index = ParserUtil.parseIndex(args);
            return new TemplateUseCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                TemplateUseCommand.MESSAGE_USAGE), pe);
        }
    }

}

