package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.logic.commands.AddFileCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.file.EncryptedFile;
import seedu.address.model.file.FileName;
import seedu.address.model.file.FilePath;
import seedu.address.model.tag.Tag;


/**
 * Parses input arguments and creates a new AddFileCommand object
 */
public class AddFileCommandParser implements Parser<AddFileCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddFileCommand parse(String args) throws ParseException {
        try {
            ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG);

            String fullPath = argMultimap.getPreamble();
            FileName fileName = ParserUtil.parseFileName(fullPath);
            FilePath filePath = ParserUtil.parseFilePath(fullPath);
            Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

            EncryptedFile file = new EncryptedFile(fileName, filePath, tagList, true);

            return new AddFileCommand(file);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddFileCommand.MESSAGE_USAGE), pe);
        }
    }
}
