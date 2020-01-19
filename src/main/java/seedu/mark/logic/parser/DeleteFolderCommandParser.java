package seedu.mark.logic.parser;

import static java.util.Objects.requireNonNull;

import static seedu.mark.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.mark.commons.exceptions.IllegalValueException;
import seedu.mark.logic.commands.DeleteFolderCommand;
import seedu.mark.logic.parser.exceptions.ParseException;
import seedu.mark.model.bookmark.Folder;

/**
 * Parses input arguments and creates a new {@code DeleteFolderCommand} object
 */
public class DeleteFolderCommandParser implements Parser<DeleteFolderCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code DeleteFolderCommand}
     * and returns a {@code DeleteFolderCommand} object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public DeleteFolderCommand parse(String args) throws ParseException {
        requireNonNull(args);
        Folder folder;
        try {
            folder = ParserUtil.parseFolder(args.trim());
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteFolderCommand.MESSAGE_USAGE), ive);
        }

        return new DeleteFolderCommand(folder);
    }
}
