package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddFromContactsCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import java.util.List;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class AddFromContactsCommandParser implements Parser<AddFromContactsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddFromContactsCommand parse(String args) throws ParseException {
        try {
            List<Index> indexList = ParserUtil.parseMultipleIndex(args);
            return new AddFromContactsCommand(indexList);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddFromContactsCommand.MESSAGE_USAGE), pe);
        }
    }
}
