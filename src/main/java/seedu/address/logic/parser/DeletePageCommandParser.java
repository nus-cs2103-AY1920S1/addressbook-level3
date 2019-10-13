package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeletePageCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.diary.Name;

/**
 * Parses input arguments and creates a new DeletePageCommand object
 */
public class DeletePageCommandParser implements Parser<DeletePageCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeletePageCommand
     * and returns a DeletePageCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeletePageCommand parse(String args) throws ParseException {
        // Separate arguments into index and diary name
        String[] argsArr = args.trim().split(" ", 2);
        try {
            Index index = ParserUtil.parseIndex(argsArr[0]);
            Name diaryName = ParserUtil.parseName(argsArr[1]);
            return new DeletePageCommand(index, diaryName);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeletePageCommand.MESSAGE_USAGE), pe);
        }
    }

}
