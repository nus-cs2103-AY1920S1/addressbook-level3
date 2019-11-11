package seedu.mark.logic.parser;

import static java.util.Objects.requireNonNull;

import static seedu.mark.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_NEW_FOLDER;

import seedu.mark.commons.exceptions.IllegalValueException;

import seedu.mark.logic.commands.EditFolderCommand;
import seedu.mark.logic.parser.exceptions.ParseException;

import seedu.mark.model.bookmark.Folder;

/**
 * Parses input arguments and creates a new {@code EditFolderCommand} object
 */
public class EditFolderCommandParser implements Parser<EditFolderCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code EditFolderCommand}
     * and returns a {@code EditFolderCommand} object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public EditFolderCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NEW_FOLDER);

        Folder folder;
        try {
            folder = ParserUtil.parseFolder(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditFolderCommand.MESSAGE_USAGE), ive);
        }

        String newFolder = argMultimap.getValue(PREFIX_NEW_FOLDER).orElseThrow(() -> new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditFolderCommand.MESSAGE_USAGE)));

        return new EditFolderCommand(folder, new Folder(newFolder));
    }
}
