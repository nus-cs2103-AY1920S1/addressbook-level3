package seedu.elisa.logic.parser;

import seedu.elisa.commons.core.index.Index;
import seedu.elisa.logic.commands.OpenCommand;
import seedu.elisa.logic.parser.exceptions.ParseException;

import static seedu.elisa.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class OpenCommandParser implements Parser<OpenCommand> {

    public OpenCommand parse(String description, String flags) throws ParseException {
        // flags should be empty in this case, focus on description only
        try {
            Index index = ParserUtil.parseIndex(description);
            return new OpenCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, OpenCommand.MESSAGE_USAGE), pe);
        }
    }
}