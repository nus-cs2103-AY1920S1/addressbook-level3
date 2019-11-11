package seedu.address.logic.parser.common;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.util.FileUtil.isFileExists;
import static seedu.address.commons.util.FileUtil.isValidPath;

import java.io.File;
import java.nio.file.Paths;
import java.util.regex.Pattern;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.common.Photo;


/**
 * Utility class for common parsing operations of {@link Photo}.
 */
public abstract class PhotoUtil {

    private static final Pattern ALLOWED_IMAGE_EXTENSIONS =
            Pattern.compile("(?:.*)(?<extension>\\.(png|jpg|jpeg))$");

    /**
     * Parses the file path to be used for the photo from the {@code argMultimap}.
     * If not specified, it uses the path of the {@code imageFile}.
     * Assumes the alternative {@code imageFile} exists and is of a valid extension if non-null.
     *
     * @param path The input {@link java.nio.file.Path} given by user.
     * @param imageFile The {@link File} image file.
     * @return The file path to use for the photo.
     * @throws ParseException If the data file path prefix is not specified and the imageFile is {@code null},
     * or the file specified at the given data file path does not exist or is invalid.
     */
    public static Photo parseFilePath(String path, File imageFile) throws ParseException {
        //short-circuit if alternative is present
        if (imageFile != null) {
            return new Photo(imageFile.toPath().toAbsolutePath());
        }

        // Checks if path is valid and exists
        boolean doesFileExist = isValidPath(path) && isFileExists(Paths.get(path));
        if (!doesFileExist) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, Photo.MESSAGE_PATH_CONSTRAINTS));
        }


        boolean isFileExtensionValid = ALLOWED_IMAGE_EXTENSIONS
                .matcher(Paths.get(path).getFileName().toString()).matches();
        if (!isFileExtensionValid) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, Photo.MESSAGE_PATH_CONSTRAINTS));
        }

        return new Photo(path);
    }
}
