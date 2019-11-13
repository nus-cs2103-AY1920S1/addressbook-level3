package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UploadPictureCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author SebastianLie
/**
 * Parses input arguments and return UploadPictureCommand
 */
public class UploadPictureCommandParser implements Parser<UploadPictureCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UploadPictureCommand
     * and returns an UploadPictureCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UploadPictureCommand parse(String args) throws ParseException {
        requireNonNull(args);
        Index index;

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_FILE);
        if (argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    UploadPictureCommand.MESSAGE_USAGE));
        }

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    UploadPictureCommand.MESSAGE_USAGE), pe);
        }
        String displayPictureFileName = ParserUtil.parseDisplayPicture(argMultimap.getValue(PREFIX_FILE).get());

        return new UploadPictureCommand(index, displayPictureFileName);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
