package seedu.address.diaryfeature.logic.parser;


import seedu.address.commons.core.index.Index;
import seedu.address.diaryfeature.logic.commands.DeleteCommand;
import seedu.address.diaryfeature.logic.parser.exceptions.EmptyArgumentException;
import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser {
    private static final String DELETE_USAGE = "In particular, input your delete command like this: \n" +
            "delete TARGET | EG: delete 1. \nNote that the input has to be a number and more than or equal to 1";

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parse(String args) throws EmptyArgumentException {
        try {
            Index index;
            index = ParserUtil.parseIndex(args);
            return new DeleteCommand(index);
        } catch (ParseException error) {
            throw new EmptyArgumentException(DeleteCommand.COMMAND_WORD, DELETE_USAGE);
        }
    }
}
