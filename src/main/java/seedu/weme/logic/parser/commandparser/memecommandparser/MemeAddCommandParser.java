package seedu.weme.logic.parser.commandparser.memecommandparser;

import static seedu.weme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_FILEPATH;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.weme.logic.commands.memecommand.MemeAddCommand;
import seedu.weme.logic.parser.Parser;
import seedu.weme.logic.parser.exceptions.ParseException;
import seedu.weme.logic.parser.util.ArgumentMultimap;
import seedu.weme.logic.parser.util.ArgumentTokenizer;
import seedu.weme.logic.parser.util.ParserUtil;
import seedu.weme.model.meme.Description;
import seedu.weme.model.meme.Meme;
import seedu.weme.model.path.ImagePath;
import seedu.weme.model.tag.Tag;

/**
 * Parses input arguments and creates a new MemeAddCommand object
 */
public class MemeAddCommandParser implements Parser<MemeAddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MemeAddCommand
     * and returns an MemeAddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public MemeAddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_FILEPATH, PREFIX_DESCRIPTION, PREFIX_TAG);

        if (!argMultimap.arePrefixesPresent(PREFIX_FILEPATH)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MemeAddCommand.MESSAGE_USAGE));
        }

        Description description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).orElse(""));
        ImagePath url = ParserUtil.parseFilePath(argMultimap.getValue(PREFIX_FILEPATH).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Meme meme = new Meme(url, description, tagList);

        return new MemeAddCommand(meme);
    }

}
