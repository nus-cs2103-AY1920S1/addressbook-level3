package seedu.address.logic.parser;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.DeleteShortcutCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.commanditem.CommandItem;

/**
 * Parses input arguments and creates a new DeleteShortcutCommand object
 */
public class DeleteShortcutParser {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteShortcutCommand
     * and returns a DeleteShortcutCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteShortcutCommand parse(String args) throws ParseException {
        try {
            CommandItem shortcutToDelete = ParserUtil.parseShortcut(args);
            return new DeleteShortcutCommand(shortcutToDelete);
        } catch (ParseException e) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteShortcutCommand.MESSAGE_USAGE), e);
        }
    }
}
