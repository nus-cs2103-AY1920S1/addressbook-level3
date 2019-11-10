package seedu.address.logic.parser.diary.gallery;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.diary.gallery.DeletePhotoCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * {@link Parser} that parses user input to return a {@link DeletePhotoCommand}.
 * The input must contain a valid integer index of the photo displayed to delete.
 */
public class DeletePhotoParser implements Parser<DeletePhotoCommand> {
    @Override
    public DeletePhotoCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);
        try {
            Index index = ParserUtil.parseIndex(userInput);
            return new DeletePhotoCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeletePhotoCommand.MESSAGE_USAGE), pe);
        }
    }
}
