package seedu.address.diaryfeature.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.diaryfeature.logic.commands.UnPrivateCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.exceptions.ParseException;

public class UnPrivateCommandParser {
    public static final String UNPRIVATE_USAGE = "In particular, input your unprivate command like this: \n" +
            "unprivate target Eg: unprivate 1. \n Note that the input has to be more than or equal to 1";
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parse(String args) throws ParseException {
        Index index;
        index = ParserUtil.parseIndex(args);
        return new UnPrivateCommand(index);

    }
}
