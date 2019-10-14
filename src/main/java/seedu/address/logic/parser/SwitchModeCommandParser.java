package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.FunctionMode;
import seedu.address.logic.commands.SwitchModeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SwitchModeCommand object
 */
public class SwitchModeCommandParser implements Parser<SwitchModeCommand> {

    private static final String CHEATSHEET_ABBREVIATION = "CS";
    private static final String FLASHCARD_ABBREVIATION = "FC";
    private static final String NOTES_ABBREVIATION = "NOTES";

    /**
     * Parses the given {@code String} of arguments in the context of the SwitchModeCommand
     * and returns a SwitchModeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SwitchModeCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SwitchModeCommand.MESSAGE_USAGE));
        }
        switch (trimmedArgs.toUpperCase()) {
        case CHEATSHEET_ABBREVIATION:
            return new SwitchModeCommand(FunctionMode.CHEATSHEET);

        case FLASHCARD_ABBREVIATION:
            return new SwitchModeCommand(FunctionMode.FLASHCARD);

        case NOTES_ABBREVIATION:
            return new SwitchModeCommand(FunctionMode.NOTES);

        default:
           throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SwitchModeCommand.MESSAGE_USAGE));
        }
    }
}
