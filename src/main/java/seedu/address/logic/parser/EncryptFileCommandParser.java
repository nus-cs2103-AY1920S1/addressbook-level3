package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Date;
import java.util.Set;

import seedu.address.logic.commands.EncryptFileCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.file.EncryptedAt;
import seedu.address.model.file.EncryptedFile;
import seedu.address.model.file.FileName;
import seedu.address.model.file.FilePath;
import seedu.address.model.tag.Tag;


/**
 * Parses input arguments and creates a new EncryptFileCommand object
 */
public class EncryptFileCommandParser implements FileCommandParser<EncryptFileCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EncryptFileCommand parse(String args, String password) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG);

        FileName fileName = ParserUtil.parseFileName(args);
        FilePath filePath = ParserUtil.parseFilePath(args);
        EncryptedAt encryptedAt = new EncryptedAt(new Date());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        EncryptedFile file = new EncryptedFile(fileName, filePath, encryptedAt, tagList);

        return new EncryptFileCommand(file, password);
    }
}
