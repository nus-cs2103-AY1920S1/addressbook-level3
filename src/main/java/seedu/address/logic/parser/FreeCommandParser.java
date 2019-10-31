package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;

import seedu.address.logic.commands.FreeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class FreeCommandParser implements Parser<FreeCommand> {


    private static ParseException getWrongFormatException() {
        return new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FreeCommand.MESSAGE_USAGE));
    }

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     *
     * @return the parsed command
     * @throws ParseException if the user input does not conform the expected format
     */
    public FreeCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TASK);

        // driver and task must both be present
        String task = argMultimap.getValue(PREFIX_TASK).orElseThrow(FreeCommandParser::getWrongFormatException);
        int taskId = ParserUtil.parseId(task);

        return new FreeCommand(taskId);
    }

}
