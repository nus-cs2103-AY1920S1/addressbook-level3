package seedu.address.logic.parser.diary.gallery;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATA_FILE_PATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_TIME_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE_CHOOSER;

import java.io.File;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import seedu.address.commons.util.FileUtil;
import seedu.address.logic.commands.diary.gallery.AddPhotoCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserDateUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.diary.fileutil.ImageChooser;
import seedu.address.model.diary.photo.Photo;

/**
 * {@link Parser} for parsing the given user input into a {@link AddPhotoCommand}.
 * The input must contain a valid data file path, and optionally a date taken and description.
 * Alternatively, the user is allowed to specify a prefix to open a file chooser dialog, allowing using
 * the OS gui the choose the image.
 */
public class AddPhotoParser implements Parser<AddPhotoCommand> {
    @Override
    public AddPhotoCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(userInput, PREFIX_DATA_FILE_PATH,
                PREFIX_DATE_TIME_START, PREFIX_DESCRIPTION, PREFIX_FILE_CHOOSER);

        File imageFile = null;
        if (argMultimap.getValue(PREFIX_FILE_CHOOSER).isPresent()) {
            ImageChooser imageChooser = new ImageChooser();
            imageFile = imageChooser.showDialog();
        } else if (!argMultimap.getValue(PREFIX_DATA_FILE_PATH).isPresent()) {
            throwParseExceptionWithUsage();
        }

        String filePath = imageFile == null
            ? argMultimap.getValue(PREFIX_DATA_FILE_PATH).get()
            : imageFile.toPath().toAbsolutePath().toString();

        imageFile = new File(filePath);

        LocalDateTime localDateTime = argMultimap.getValue(PREFIX_DATE_TIME_START).isPresent()
                ? ParserDateUtil.getDateTimeFromString(argMultimap.getValue(PREFIX_DATE_TIME_START).get())
                : LocalDateTime.ofInstant(
                        Instant.ofEpochSecond(imageFile.lastModified(), 0),
                        ZoneOffset.systemDefault());

        String description = argMultimap.getValue(PREFIX_DESCRIPTION).orElse(
                imageFile.getName()
                        .substring(0, Math.min(Photo.MAXIMUM_DESCRIPTION_LENGTH, imageFile.getName().length())));

        try {
            Photo photo = new Photo(filePath, description, localDateTime);
            return new AddPhotoCommand(photo);
        } catch (IllegalArgumentException ex) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ex.getMessage()));
        }
    }

    private void throwParseExceptionWithUsage() throws ParseException {
        throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPhotoCommand.MESSAGE_USAGE));
    }
}
