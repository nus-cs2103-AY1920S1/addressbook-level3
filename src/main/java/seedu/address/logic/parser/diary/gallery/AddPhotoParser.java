package seedu.address.logic.parser.diary.gallery;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.ArgumentMultimap.arePrefixesPresent;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATA_FILE_PATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_TIME_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import seedu.address.logic.commands.diary.gallery.AddPhotoCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserDateUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.diary.photo.Photo;

public class AddPhotoParser implements Parser<AddPhotoCommand> {
    @Override
    public AddPhotoCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(userInput, PREFIX_DATA_FILE_PATH,
                PREFIX_DATE_TIME_START, PREFIX_DESCRIPTION);

        if (!arePrefixesPresent(argMultimap, PREFIX_DATA_FILE_PATH, PREFIX_DATE_TIME_START, PREFIX_DESCRIPTION)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPhotoCommand.MESSAGE_USAGE));
        }

        Path filePath = Paths.get(argMultimap.getValue(PREFIX_DATA_FILE_PATH).get());
        if (Files.notExists(filePath)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPhotoCommand.MESSAGE_USAGE));
        }
        LocalDateTime localDateTime =
                ParserDateUtil.getDateTimeFromString(argMultimap.getValue(PREFIX_DATE_TIME_START).get());
        String description = argMultimap.getValue(PREFIX_DESCRIPTION).get();

        Photo photo;

        try {
            photo = new Photo(filePath, description, localDateTime);
        } catch (IllegalArgumentException ex) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPhotoCommand.MESSAGE_INVALID_PHOTO), ex);
        }


        return new AddPhotoCommand(photo);
    }
}
