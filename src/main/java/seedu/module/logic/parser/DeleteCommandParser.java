package seedu.module.logic.parser;

import static seedu.module.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.module.logic.commands.DeleteCommand;
import seedu.module.logic.parser.exceptions.ParseException;
import seedu.module.model.module.predicate.SameModuleCodePredicate;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        if (trimmedArgs.split("\\s+").length > 1) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, "Module code should be a single word only."));
        }

        return new DeleteCommand(new SameModuleCodePredicate(trimmedArgs));
    }

}
