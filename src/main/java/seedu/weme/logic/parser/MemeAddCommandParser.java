package seedu.weme.logic.parser;

import static seedu.weme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.weme.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.weme.logic.parser.CliSyntax.PREFIX_FILEPATH;
import static seedu.weme.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.weme.logic.commands.MemeAddCommand;
import seedu.weme.logic.parser.exceptions.ParseException;
import seedu.weme.model.meme.Description;
import seedu.weme.model.meme.ImagePath;
import seedu.weme.model.meme.Meme;
import seedu.weme.model.tag.Tag;

/**
 * Parses input arguments and creates a new MemeAddCommand object
 */
public class MemeAddCommandParser implements Parser<MemeAddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MemeAddCommand
     * and returns an MemeAddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MemeAddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_FILEPATH, PREFIX_DESCRIPTION, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_FILEPATH)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MemeAddCommand.MESSAGE_USAGE));
        }

        Description description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).orElse(""));
        ImagePath url = ParserUtil.parseFilePath(argMultimap.getValue(PREFIX_FILEPATH).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Meme meme = new Meme(url, description, tagList);

        return new MemeAddCommand(meme);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
