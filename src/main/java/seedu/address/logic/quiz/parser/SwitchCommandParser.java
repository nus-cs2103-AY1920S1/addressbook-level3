package seedu.address.logic.quiz.parser;

import seedu.address.logic.quiz.commands.SwitchCommand;
import seedu.address.logic.quiz.parser.exceptions.ParseException;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class SwitchCommandParser implements Parser<SwitchCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    public SwitchCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SwitchCommand.MESSAGE_USAGE));
        }

        return new SwitchCommand(trimmedArgs);
    }

}
