package seedu.address.logic.parser.diary.gallery;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.util.FileUtil.isFileExists;
import static seedu.address.commons.util.FileUtil.isValidPath;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATA_FILE_PATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_TIME_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE_CHOOSER;
import static seedu.address.model.diary.photo.Photo.MAXIMUM_DESCRIPTION_LENGTH;

import java.io.File;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.regex.Pattern;

import seedu.address.commons.util.ImageChooser;
import seedu.address.logic.commands.diary.gallery.AddPhotoCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserDateUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.diary.photo.Photo;

/**
 * {@link Parser} for parsing the given user input into a {@link AddPhotoCommand}.
 * The input must contain a valid data file path, and optionally a date taken and description.
 * Alternatively, the user is allowed to specify a prefix to open a file chooser dialog, allowing using
 * the OS gui the choose the image.
 */
public class AddPhotoParser implements Parser<AddPhotoCommand> {

    /**
     * The allowed image extensions to look for when parsing the given file path
     * or chosen file from the OS file chooser.
     * */
    private static final Pattern ALLOWED_IMAGE_EXTENSIONS =
            Pattern.compile("(?:.*)(?<extension>\\.(png|jpg|jpeg))$");

    @Override
    public AddPhotoCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(userInput, PREFIX_DATA_FILE_PATH,
                PREFIX_DATE_TIME_START, PREFIX_DESCRIPTION, PREFIX_FILE_CHOOSER);

        File imageFile = null;
        if (argMultimap.getValue(PREFIX_FILE_CHOOSER).isPresent()) {
            ImageChooser imageChooser = new ImageChooser();
            imageFile = imageChooser.showDialog();
        }

        String filePath = getFilePath(argMultimap, imageFile);
        imageFile = new File(filePath);

        //A parseException should have been thrown at this point if the image file does not exist.
        assert imageFile.exists();
        //Optionally specifiable fields by user
        LocalDateTime localDateTime = parseDateTime(argMultimap, imageFile);
        String description = parseDescription(argMultimap, imageFile);

        try {
            Photo photo = new Photo(filePath, description, localDateTime);
            return new AddPhotoCommand(photo);
        } catch (IllegalArgumentException ex) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ex.getMessage()));
        }
    }

    /**
     * Parses the file path to be used for the photo from the {@code argMultimap}.
     * If not specified, it uses the path of the {@code imageFile}.
     * Assumes the alternative {@code imageFile} exists and is of a valid extension if non-null.
     *
     * @param argMultimap The {@link ArgumentMultimap} to use.
     * @param imageFile The {@link File} image file.
     * @return The file path to use for the photo.
     * @throws ParseException If the data file path prefix is not specified and the imageFile is {@code null},
     * or the file specified at the given data file path does not exist or is invalid.
     */
    private String getFilePath(ArgumentMultimap argMultimap, File imageFile) throws ParseException {
        //short-circuit if alternative is present
        if (imageFile != null) {
            return imageFile.toPath().toAbsolutePath().toString();
        }

        boolean hasDataFilePath = argMultimap.getValue(PREFIX_DATA_FILE_PATH).isPresent();
        if (!hasDataFilePath) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPhotoCommand.MESSAGE_USAGE));
        }

        String specifiedPath = argMultimap.getValue(PREFIX_DATA_FILE_PATH).get();
        boolean doesFileExist = isValidPath(specifiedPath) && isFileExists(Paths.get(specifiedPath));
        if (!doesFileExist) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPhotoCommand.MESSAGE_USAGE));
        }

        boolean isFileExtensionValid = ALLOWED_IMAGE_EXTENSIONS
                .matcher(Paths.get(specifiedPath).getFileName().toString()).matches();
        if (!isFileExtensionValid) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPhotoCommand.MESSAGE_USAGE));
        }

        return specifiedPath;
    }


    /**
     * Parses the date time to be used for the photo from the {@code argMultimap}.
     * If not specified, it uses the last modified date of the {@code imageFile}.
     * Assumes the {@code imageFile} exists.
     *
     * @param argMultimap The {@link ArgumentMultimap} to use.
     * @param imageFile   The {@link File} image file.
     * @return The {@link LocalDateTime} parsed from the multimap or retrieved from the last modified date time of the
     * imageFile.
     * @throws ParseException If the date time is present in the {@code argMultimap} but does not match the format
     *                        specified in {@link ParserDateUtil}.
     */
    private LocalDateTime parseDateTime(ArgumentMultimap argMultimap, File imageFile) throws ParseException {
        if (argMultimap.getValue(PREFIX_DATE_TIME_START).isPresent()) {
            return ParserDateUtil.getDateTimeFromString(argMultimap.getValue(PREFIX_DATE_TIME_START).get());
        } else {
            return LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(imageFile.lastModified()),
                    ZoneOffset.systemDefault());
        }
    }

    /**
     * Parses the description to be used for the photo from the {@code argMultimap}.
     * If not specified, it uses the name of the {@code imageFile}, truncated to the {@code MAXIMUM_DESCRIPTION_LENGTH}.
     * Assumes the {@code imageFile} exists.
     *
     * @param argMultimap The {@link ArgumentMultimap} to use.
     * @param imageFile The {@link File} image file.
     * @return The parsed description.
     */
    private String parseDescription(ArgumentMultimap argMultimap, File imageFile) {
        return argMultimap.getValue(PREFIX_DESCRIPTION)
                .orElse(imageFile.getName().substring(
                        0, Math.min(MAXIMUM_DESCRIPTION_LENGTH, imageFile.getName().length())));
    }
}
