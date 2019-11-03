package seedu.address.diaryfeature.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.diaryfeature.logic.commands.PrivateCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.exceptions.ParseException;

public class PrivateCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parse(String args) throws ParseException {
        Index index;
        index = ParserUtil.parseIndex(args);
        return new PrivateCommand(index);

    }
}
