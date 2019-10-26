package seedu.mark.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.mark.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.mark.logic.commands.AutotagDeleteCommand;
import seedu.mark.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@link AutotagDeleteCommand} object
 */
public class AutotagDeleteCommandParser implements Parser<AutotagDeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AutotagDeleteCommand
     * and returns an AutotagDeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AutotagDeleteCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();

        // exactly one tag name should be present
        if (trimmedArgs.isEmpty() || trimmedArgs.split("\\s+").length != 1) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AutotagDeleteCommand.MESSAGE_USAGE));
        }

        return new AutotagDeleteCommand(trimmedArgs);
    }

}
