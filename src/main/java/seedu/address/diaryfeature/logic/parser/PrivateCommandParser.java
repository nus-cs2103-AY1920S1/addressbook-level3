package seedu.address.diaryfeature.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.diaryfeature.logic.commands.PrivateCommand;
import seedu.address.diaryfeature.logic.parser.exceptions.EmptyArgumentException;
import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new PrivateCommand object
 */
public class PrivateCommandParser {
    private static final String PRIVATE_USAGE = "In particular, input your private command like this: \n"
            + "private TARGET | Eg: private 1. \nNote that the input has to be a number and  more than or equal to 1";

    /**
     * Parses the given {@code String} of arguments in the context of the PrivateCommand
     * and returns a PrivateCommand object for execution.
     *
     * @throws EmptyArgumentException if the user input does not conform the expected format
     */
    public Command parse(String args) throws EmptyArgumentException {
        try {
            Index index;
            index = ParserUtil.parseIndex(args);
            return new PrivateCommand(index);
        } catch (ParseException error) {
            throw new EmptyArgumentException(PrivateCommand.COMMAND_WORD, PRIVATE_USAGE);
        }
    }
}
