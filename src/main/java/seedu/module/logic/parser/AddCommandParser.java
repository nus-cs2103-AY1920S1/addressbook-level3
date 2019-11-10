package seedu.module.logic.parser;

import static seedu.module.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.module.logic.commands.AddCommand;
import seedu.module.logic.parser.exceptions.ParseException;
import seedu.module.model.module.predicate.SameModuleCodePredicate;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        if (trimmedArgs.split("\\s+").length > 1) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, "Module code should be a single word only."));
        }

        return new AddCommand(new SameModuleCodePredicate(trimmedArgs));
    }

}
