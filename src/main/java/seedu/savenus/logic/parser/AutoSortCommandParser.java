package seedu.savenus.logic.parser;

import static seedu.savenus.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.savenus.logic.commands.AutoSortCommand;
import seedu.savenus.logic.parser.exceptions.ParseException;

/**
 * A Simple Parser to produce an AutoSortCommand.
 */
public class AutoSortCommandParser implements Parser<AutoSortCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code AutoSortCommand}
     * and returns a {@code AutoSortCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AutoSortCommand parse(String args) throws ParseException {
        String flag = args.trim();
        if (isValidSyntax(flag)) {
            if (isOnFlag(flag)) {
                return new AutoSortCommand(true);
            } else {
                return new AutoSortCommand(false);
            }
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AutoSortCommand.MESSAGE_ERROR));
        }
    }

    /**
     * Simply checks whether the flag is on or not.
     * @param flag the String which represents a flag.
     * @return true fi the flag is on. False if not.
     */
    public boolean isOnFlag(String flag) {
        return flag.equals("ON");
    }

    /**
     * Checks if the flag is valid or not.
     * @param flag the String which is supposed to represent a flag.
     * @return true if the flag is valid. False if otherwise.
     */
    public boolean isValidSyntax(String flag) {
        return flag.equals("ON") || flag.equals("OFF");
    }
}
