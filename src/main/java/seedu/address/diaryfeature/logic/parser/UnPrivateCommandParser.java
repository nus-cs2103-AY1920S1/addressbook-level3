package seedu.address.diaryfeature.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.diaryfeature.logic.commands.UnPrivateCommand;
import seedu.address.diaryfeature.logic.parser.exceptions.EmptyArgumentException;
import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a UnPrivateCommand object
 */
public class UnPrivateCommandParser {
    public static final String UNPRIVATE_USAGE = "In particular, input your unprivate command like this: \n" +
            "unprivate TARGET Eg: unprivate 1. \nNote that the input has to be a number and more than or equal to 1";

    /**
     * Parses the given {@code String} of arguments in the context of theUnPrivateCommand
     * and returns a UnPrivateCommand object for execution.    *
     *
     * @param args is the user input
     * @return an UnPrivateCommand to execute
     * @throws EmptyArgumentException if the user input does not conform the expected format
     */

    public Command parse(String args) throws EmptyArgumentException {
        try {
            Index index;
            index = ParserUtil.parseIndex(args);
            return new UnPrivateCommand(index);
        } catch (ParseException error) {
            throw new EmptyArgumentException(UnPrivateCommand.COMMAND_WORD, UNPRIVATE_USAGE);
        }
    }
}

