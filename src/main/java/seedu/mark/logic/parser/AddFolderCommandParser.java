package seedu.mark.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.mark.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_PARENT_FOLDER;

import seedu.mark.commons.exceptions.IllegalValueException;
import seedu.mark.logic.commands.AddFolderCommand;
import seedu.mark.logic.parser.exceptions.ParseException;
import seedu.mark.model.bookmark.Folder;

/**
 * Parses input arguments and creates a new {@code AddFolderCommand} object
 */
public class AddFolderCommandParser implements Parser<AddFolderCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code AddFolderCommand}
     * and returns a {@code AddFolderCommand} object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public AddFolderCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PARENT_FOLDER);

        Folder folder;
        try {
            folder = ParserUtil.parseFolder(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddFolderCommand.MESSAGE_USAGE), ive);
        }

        String parentFolder =
                argMultimap.getValue(PREFIX_PARENT_FOLDER).orElse(Folder.DEFAULT_FOLDER_NAME);

        return new AddFolderCommand(folder, new Folder(parentFolder));
    }
}
