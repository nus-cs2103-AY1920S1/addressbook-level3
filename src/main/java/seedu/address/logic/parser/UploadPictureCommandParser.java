package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UploadPictureCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class UploadPictureCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the UploadPictureCommand
     * and returns an UploadPictureCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UploadPictureCommand parse(String args) throws ParseException {
        requireNonNull(args);
        Index index;
        try {
            index = ParserUtil.parseIndex(args);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    UploadPictureCommand.MESSAGE_USAGE), pe);
        }
        return new UploadPictureCommand(index);
    }

}
