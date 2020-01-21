package seedu.algobase.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.commons.core.Messages.MESSAGE_INVALID_COMMAND_NAME;
import static seedu.algobase.commons.util.AppUtil.getClassStringField;

import seedu.algobase.logic.commands.Command;
import seedu.algobase.logic.commands.HelpCommand;
import seedu.algobase.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new HelpCommand object
 */
public class HelpCommandParser implements Parser<HelpCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the HelpCommand
     * and returns a HelpCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public HelpCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);
        String trimmedArgs = userInput.trim();
        if (trimmedArgs.isEmpty()) {
            return new HelpCommand(null, true);
        } else {
            for (Class command : Command.COMMAND_LIST) {
                String commandWord = getClassStringField(command, "COMMAND_WORD");
                if (commandWord.equals(trimmedArgs)) {
                    return new HelpCommand(command, false);
                }
            }
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_NAME, trimmedArgs));
        }
    }
}
